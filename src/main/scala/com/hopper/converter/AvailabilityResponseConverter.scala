package com.hopper.converter

import java.time.{Duration, LocalDate}

import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.{AvailabilityLongResponseV2, Hotel, Room}
import com.hopper.model.availability.eps.{EPSShoppingResponse, PropertyAvailability, PropertyAvailabilityAmenities, PropertyAvailabilityBedGroups, PropertyAvailabilityCancelPenalties, PropertyAvailabilityLinks, PropertyAvailabilityPrice, PropertyAvailabilityPriceWithCurrency, PropertyAvailabilityRates, PropertyAvailabilityRoom, PropertyAvailabilityRoomRates, PropertyAvailabilityTotalPrice}
import com.twitter.finagle.http.Method

/**
  * Convert Agoda Availability XML Response to EPS Standard JSON Response
  */
class AvailabilityResponseConverter(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2)
{
    def convertToEPSResponse(): EPSShoppingResponse =
    {
        val propertyList: Array[PropertyAvailability] = response.hotels
          .map(hotel => new PropertyAvailability(
              hotel.id,
              hotel.rooms.map(r => _getRoomInfo(r, hotel)))
          )

        new EPSShoppingResponse(propertyList)
    }

    def _getRoomInfo(r: Room, hotel: Hotel): PropertyAvailabilityRoom =
    {
        new PropertyAvailabilityRoom(
            r.id,
            r.name,
            _getRateList(r, hotel)
        )
    }

    def _getRateList(r: Room, hotel: Hotel): Array[PropertyAvailabilityRates] =
    {
        Array(_populateRateInfo(r, hotel))
    }

    def _populateRateInfo(r: Room, hotel: Hotel): PropertyAvailabilityRates =
    {
        val rate: PropertyAvailabilityRates = new PropertyAvailabilityRates

        rate.id = r.rateCategoryID
        rate.availableRooms = r.remainingRooms

        rate.depositRequired = false
        rate.fencedDeal = false
        rate.merchantOfRecordString = "TBD"

        rate.links = _populateLinks(r, hotel)
        rate.bedGroups = _populateBedGroups(r, hotel)
        rate.roomPriceByOccupancy = _populateRates(r)

        Option(r.rateInfo.promotionType).foreach(promotionType => {
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
        roomPrice.totals = _populateTotals(r)

        _populateSurcharges(r).foreach(surcharge => {
            roomPrice.stayPrice = surcharge._1
            roomPrice.fees = surcharge._2
        })

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

        val priceList = List(
            new PropertyAvailabilityPrice("base_rate", r.rateInfo.rate.exclusive, r.currency),
            new PropertyAvailabilityPrice("sales_tax", r.rateInfo.rate.tax, r.currency),
            new PropertyAvailabilityPrice("tax_and_service_fee", r.rateInfo.rate.fees, r.currency))

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
                        fees = Array(
                            new PropertyAvailabilityPrice("mandatory_fee", surcharge.rate.exclusive, r.currency),
                            new PropertyAvailabilityPrice("mandatory_tax", surcharge.rate.tax, r.currency)
                        )
                    }
                    else
                    {
                        stayPrices = Array(
                            new PropertyAvailabilityPrice("Property_fee", surcharge.rate.exclusive, r.currency),
                            new PropertyAvailabilityPrice("sales_tax", surcharge.rate.tax, r.currency)
                        )
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
        val inclusiveRate:Double = r.rateInfo.totalPaymentAmount.inclusive
        new PropertyAvailabilityPriceWithCurrency(
            new PropertyAvailabilityPrice(inclusiveRate, r.currency),
            new PropertyAvailabilityPrice(inclusiveRate, r.currency)
        )
    }

    def _populateExclusivePriceWithCurrency(r: Room): PropertyAvailabilityPriceWithCurrency =
    {
        val exclusiveRate:Double = r.rateInfo.totalPaymentAmount.exclusive

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

    def _populateLinks(r: Room, hotel: Hotel): Map[String, PropertyAvailabilityLinks] =
    {
        val link = new PropertyAvailabilityLinks(
            Method.Get.toString,
            "/2.1/properties/availability/" + hotel.id + "/rooms/201300484/rates/206295235/payment-options?token=Ql1WAERHXV1QO"
        )

        Map("payment_options" -> link)
    }

    def _populateBedGroups(r: Room, hotel: Hotel): Array[PropertyAvailabilityBedGroups] =
    {
        val bedGroup = new PropertyAvailabilityBedGroups
        val link = new PropertyAvailabilityLinks(
            Method.Get.toString,
            "/2.1/properties/availability/" + hotel.id + "/rooms/201300484/rates/206295235/price-check?token=Ql1WAERHXV1QO"
        )

        bedGroup.links = Map("price_check" -> link)

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
object AvailabilityResponseConverter{}
