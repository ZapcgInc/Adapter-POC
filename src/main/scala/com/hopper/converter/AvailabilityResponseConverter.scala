package com.hopper.converter

import java.time.{Duration, LocalDate}

import com.hopper.commons.eps.model.availability.{EPSShoppingResponse, PropertyAvailability, PropertyAvailabilityAmenities, PropertyAvailabilityBedGroups, PropertyAvailabilityCancelPenalties, PropertyAvailabilityLinks, PropertyAvailabilityPrice, PropertyAvailabilityPriceWithCurrency, PropertyAvailabilityRates, PropertyAvailabilityRoom, PropertyAvailabilityRoomRates, PropertyAvailabilityTotalPrice}
import com.hopper.commons.eps.model.prebook.EPSPreBookingResponse
import com.hopper.converter.href.{BookHrefBuilder, PreBookHrefBuilder}
import com.hopper.model.agoda.availability.request.AvailabilityRequestV2
import com.hopper.model.agoda.availability.response.{AvailabilityLongResponseV2, Hotel, Room}
import com.twitter.finagle.http.Method
import com.hopper.commons.eps.model.prebook.Links

/**
  * Convert Agoda Availability XML Response to EPS Standard JSON Response
  */
class AvailabilityResponseConverter(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2)
{
    var totalPriceInclusive : Double = 0
    var totalPriceExclusive : Double = 0
    def convertToEPSResponse(): EPSShoppingResponse =
    {
        val propertyList: Array[PropertyAvailability] = response.hotels.map{hotel =>
            val rooms = hotel.rooms.groupBy(_.id).map{roomt =>
                val roomInfo = _getRoomInfo(roomt._2.head,hotel)
                val rates: Array[PropertyAvailabilityRates] = roomt._2.map{ room=> _populateRateInfo(room, hotel)}
                roomInfo.rates = rates
                roomInfo
            }
            new PropertyAvailability(hotel.id,rooms.toArray)
        }

        new EPSShoppingResponse(propertyList)
    }

    def convertToEPSResponse(hotelID: String, roomID: String): Option[EPSPreBookingResponse] =
    {

        val hotel: Option[Hotel] = response.hotels.find(hotel => hotel.id == hotelID)
        println("hotel"+hotel.toString)
        if (hotel.isEmpty)
        {
            None
        }

        val room: Option[Room] = hotel.get.rooms.find(room => room.id == roomID)
        if (room.isEmpty)
        {
            None
        }

        val rates: Map[String, PropertyAvailabilityRoomRates] = _populateRates(room.get)

        Some(new EPSPreBookingResponse("matched", rates, _populateBookingHref(hotelID, roomID)))
    }

    def _populateBookingHref(hotelID: String, roomID: String): Map[String, Links] =
    {
        val bookingHref:String = BookHrefBuilder.buildHref(request, response, hotelID, roomID)
        Map("book" -> new Links(Method.Get.toString, bookingHref))
    }

    def _getRoomInfo(r: Room, hotel: Hotel): PropertyAvailabilityRoom =
    {
        new PropertyAvailabilityRoom(
            r.id,
            r.name,
            Array()
        )
    }

    def _populateRateInfo(r: Room, hotel: Hotel): PropertyAvailabilityRates =
    {
        val rate: PropertyAvailabilityRates = new PropertyAvailabilityRates

        rate.id = r.rateCategoryID
        rate.availableRooms = r.remainingRooms

        rate.depositRequired = false
        rate.fencedDeal = false
        rate.merchantOfRecordString = "merchant"

        rate.bedGroups = _populateBedGroups(r, hotel)
        rate.roomPriceByOccupancy = _populateRates(r)

        Option(r.rateInfo.promotionType).foreach(promotionType =>
        {
            rate.promoId = promotionType.id
            rate.promoDesc = promotionType.text
        })

        _populateAmenities(r).foreach(amenities => rate.amenities = amenities)
        _populateCancelPolicies(r).foreach(policies => rate.cancelPolicies = policies)

        if (r.cancellation != null && r.cancellation.policyParameters != null)
        {
            rate.refundable = r.cancellation.policyParameters.head.days != 365
        }

        rate
    }

    def _populateRates(r: Room): Map[String, PropertyAvailabilityRoomRates] =
    {
        val roomPrice: PropertyAvailabilityRoomRates = new PropertyAvailabilityRoomRates

        roomPrice.nightlyPrice = _populateNightlyPrice(r)


        _populateSurcharges(r).foreach(surcharge =>
        {
            roomPrice.stayPrice = surcharge._1
            roomPrice.fees = surcharge._2
        })

        roomPrice.totals = _populateTotals(r)

        // return immutable Map.
        var occupancyMap: scala.collection.mutable.Map[String, PropertyAvailabilityRoomRates] = scala.collection.mutable.Map[String, PropertyAvailabilityRoomRates]()
        for (ocy <- request.occupancy)
        {
            occupancyMap += (ocy -> roomPrice)
        }

        request.occupancy.groupBy(identity).mapValues(_ => roomPrice).map(identity)
    }

    def _populateNightlyPrice(r: Room): List[List[PropertyAvailabilityPrice]] =
    {
        val lengthOfStay: Int = _calculateLengthOfStay(request)
        val base_rate = r.rateInfo.rate.exclusive
        println("base rate"+base_rate)
        val base_rate_temp = base_rate*1.1111
        val new_base_rate = Math.round(base_rate_temp*100.0) / 100.0

        val tax_and_service_fee = r.rateInfo.rate.fees+r.rateInfo.rate.tax
        println("new base rate"+new_base_rate)
        val priceList = List(
            new PropertyAvailabilityPrice("base_rate", new_base_rate, r.currency),
//            new PropertyAvailabilityPrice("sales_tax", r.rateInfo.rate.tax, r.currency),
            new PropertyAvailabilityPrice("tax_and_service_fee", r.rateInfo.rate.fees+r.rateInfo.rate.tax, r.currency))

        totalPriceInclusive = (new_base_rate+tax_and_service_fee)*lengthOfStay

        totalPriceExclusive =new_base_rate * lengthOfStay

        List.fill(lengthOfStay)(priceList)
    }

    def _calculateLengthOfStay(request: AvailabilityRequestV2): Int =
    {
        Duration.between(
            LocalDate.parse(request.checkInDate).atStartOfDay,
            LocalDate.parse(request.checkOutDate).atStartOfDay
        ).toDays.toInt
    }

    def _populateSurcharges(r: Room): Option[(Array[PropertyAvailabilityPrice] /*stayPrice*/ , Array[PropertyAvailabilityPrice]) /*fees*/ ] =
    {
        if (r.rateInfo.surcharges != null)
        {
            var stayPrices: Array[PropertyAvailabilityPrice] = null
            var fees: Array[PropertyAvailabilityPrice] = null

            for (surcharge <- r.rateInfo.surcharges)
            {
                if (surcharge.charge != null)
                {
                    if (surcharge.charge.equals("Excluded"))
                    {
                        val taxAndServiceFee = surcharge.rate.tax +surcharge.rate.fees
                        fees = Array(
                            new PropertyAvailabilityPrice("mandatory_fee", surcharge.rate.exclusive, r.currency),
                            new PropertyAvailabilityPrice("tax_and_service_fee", taxAndServiceFee, r.currency)
                        )
                    }
                    else
                    {
                        val taxAndServiceFee = surcharge.rate.tax+surcharge.rate.fees
                        val propertyFee = surcharge.rate.exclusive
                        stayPrices = Array(
                            new PropertyAvailabilityPrice("Property_fee", propertyFee, r.currency),
                            new PropertyAvailabilityPrice("tax_and_service_fee", taxAndServiceFee, r.currency)
                        )
                        totalPriceInclusive = totalPriceInclusive + propertyFee + taxAndServiceFee
                        println("totalsIfSurcharges"+totalPriceInclusive)
                    }

                }
            }

            return Some(stayPrices, fees)
        }

        None
    }

    def _populateTotals(r: Room): PropertyAvailabilityTotalPrice =
    {
        new PropertyAvailabilityTotalPrice(_populateInclusivePriceWithCurrency(r), _populateExclusivePriceWithCurrency(r))
    }

    def _populateInclusivePriceWithCurrency(r: Room): PropertyAvailabilityPriceWithCurrency =
    {
        //val inclusiveRate: Double = r.rateInfo.totalPaymentAmount.inclusive
        val inclusiveRate: Double = Math.round(totalPriceInclusive * 100.0) / 100.0
        println("totalPriceInclusive"+inclusiveRate)
        new PropertyAvailabilityPriceWithCurrency(
            new PropertyAvailabilityPrice(inclusiveRate, r.currency),
            new PropertyAvailabilityPrice(inclusiveRate, r.currency)
        )
    }

    def _populateExclusivePriceWithCurrency(r: Room): PropertyAvailabilityPriceWithCurrency =
    {
       // val exclusiveRate: Double = r.rateInfo.totalPaymentAmount.exclusive
       val exclusiveRate: Double = Math.round(totalPriceExclusive * 100.0) / 100.0
        println("totalPriceExclusive"+exclusiveRate)
        new PropertyAvailabilityPriceWithCurrency(
            new PropertyAvailabilityPrice(exclusiveRate, r.currency),
            new PropertyAvailabilityPrice(exclusiveRate, r.currency)
        )
    }

    def _populateCancelPolicies(r: Room): Option[Array[PropertyAvailabilityCancelPenalties]] =
    {
        if (r.rateInfo.totalPaymentAmount.inclusive != 0 && r.cancellation.policyDates != null)
        {
            return Option(
                r.cancellation.policyDates.map(policyDate => new PropertyAvailabilityCancelPenalties(r.currency, policyDate))
            )
        }

        Option.empty
    }

    def _populateBedGroups(r: Room, hotel: Hotel): Array[PropertyAvailabilityBedGroups] =
    {
        val bedGroup = new PropertyAvailabilityBedGroups
        val href: String = PreBookHrefBuilder.buildHref(r, hotel, request)

        bedGroup.links = Map("price_check" -> new PropertyAvailabilityLinks(Method.Get.toString, href))

        Array(bedGroup)
    }

    def _populateAmenities(r: Room): Option[Array[PropertyAvailabilityAmenities]] =
    {
        if (r.benefits != null)
        {
            return Some(r.benefits
              .map(b => new PropertyAvailabilityAmenities(b.id)))
        }

        None
    }
}

/*Companion Object*/
object AvailabilityResponseConverter
{}
