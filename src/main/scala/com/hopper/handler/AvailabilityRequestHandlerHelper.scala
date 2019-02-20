package com.hopper.handler

import com.hopper.model.availability.agoda.response.AvailabilityLongResponseV2
import com.hopper.model.availability.shopping.ShoppingResponse
import com.hopper.model.availability.{CancelPolicies, Link, Price, RoomPrice}
import com.hopper.model.availability.shopping.{Amenities, BedGroups, Property, Rate}
import java.util
import java.time.{Duration, LocalDate}

import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import scala.collection.JavaConversions._

object AvailabilityRequestHandlerHelper
{

    import com.hopper.model.availability.agoda.response.{Hotel, Room}

    def convertToEPSResponse(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2): ShoppingResponse =
    {
        println(response.toString)
        println("Hotels List Size : "  +  response.hotels.size())
        val lengthOfStay:Int = _calculateLengthOfStay(request)

        val propertyList: util.List[Property] = new util.ArrayList[Property]
        for (hotel <- response.hotels)
        {
            println(hotel.toString)
            val property = new Property
            property.setPropertyId(Integer.toString(hotel.id))
            val roomList = new util.ArrayList[com.hopper.model.availability.shopping.Room]
            for (r <- hotel.rooms)
            {
                val room: com.hopper.model.availability.shopping.Room = _populateRoomInfo(request, r, hotel, lengthOfStay)
                roomList.add(room)
            }
            property.setRooms(roomList)
            propertyList.add(property)
        }

        new ShoppingResponse(propertyList.toArray(new Array[Property](propertyList.size)))
    }

    def _calculateLengthOfStay(request: AvailabilityRequestV2): Int =
    {
        val checkin: LocalDate = LocalDate.parse(request.checkInDate)
        val checkout: LocalDate = LocalDate.parse(request.checkOutDate)
        Duration.between(checkin.atStartOfDay, checkout.atStartOfDay).toDays.toInt
    }

    def _populateRoomInfo(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): com.hopper.model.availability.shopping.Room =
    {
        val room: com.hopper.model.availability.shopping.Room = new com.hopper.model.availability.shopping.Room

        room.setId(Integer.toString(r.id))
        room.setName(r.name)
        room.setRates(_getRateList(request, r, hotel, lengthOfStay))

        room
    }

    def _getRateList(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): util.List[Rate] =
    {
        val rate: Rate = _populateRateInfo(request, r, hotel, lengthOfStay)
        val ratesList: util.List[Rate] = new util.ArrayList[Rate]
        ratesList.add(rate)

        ratesList
    }

    def _populateRateInfo(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): Rate =
    {
        val rate: Rate = new Rate
        rate.setId(r.rateCategoryID)
        rate.setAvailableRooms(r.remainingRooms) // TODO: What is happening here ?

        if (r.cancellation != null && r.cancellation.policyParameters != null)
        {
            rate.setRefundable(r.cancellation.policyParameters.head.days != 365)
        }

        rate.setDepositRequired(false)
        rate.setFencedDeal(false)
        rate.setMerchantOfRecord("TBD")

        rate.setAmenities(_populateAmenities(r))
        rate.setLinks(_populateLinks(r, hotel))
        rate.setBedGroups(_populateBedGroups(r, hotel))

        val cancelPolicies: Option[util.ArrayList[CancelPolicies]] = _populateCancelPolicies(r)
        if (cancelPolicies.isDefined)
        {
            rate.setCancelPolicies(cancelPolicies.get)
        }

        rate.setRoomPriceByOccupancy(_populateRates(request, r, lengthOfStay))

        if (r.rateInfo.promotionType != null)
        {
            rate.setPromoId(r.rateInfo.promotionType.id)
            rate.setPromoDesc(r.rateInfo.promotionType.text)
        }

        rate
    }

    def _populateRates(request: AvailabilityRequestV2, r: Room, lengthOfStay: Int): util.Map[String, RoomPrice] =
    {
        import java.util
        val basePrice = new Price
        basePrice.setType("base_rate")
        basePrice.setValue(r.rateInfo.totalPaymentAmount.exclusive)
        basePrice.setCurrency(r.currency)

        val saleTaxPrice: Price = new Price
        saleTaxPrice.setType("sales_tax")
        saleTaxPrice.setCurrency(r.currency)
        saleTaxPrice.setValue(r.rateInfo.totalPaymentAmount.tax)

        val taxAndServiceFee: Price = new Price
        taxAndServiceFee.setType("tax_and_service_fee")
        taxAndServiceFee.setCurrency(r.currency)
        taxAndServiceFee.setValue(r.rateInfo.totalPaymentAmount.fees)

        val priceList: util.List[Price] = new util.ArrayList[Price]
        priceList.add(basePrice)
        priceList.add(saleTaxPrice)
        priceList.add(taxAndServiceFee)

        // TODO: Doesnt seem right.
        val nightPriceList = new util.ArrayList[util.List[Price]]
        (1 to lengthOfStay).map(_ => nightPriceList.add(priceList))

        val roomPrice: RoomPrice = new RoomPrice
        roomPrice.setNightlyPrice(nightPriceList)

        val occupancyMap: util.Map[String, RoomPrice] = new util.HashMap[String, RoomPrice]
        for (ocy <- request.occupancy)
        {
            occupancyMap.put(ocy, roomPrice)
        }


        occupancyMap
    }

    def _populateCancelPolicies(r: Room): Option[util.ArrayList[CancelPolicies]] =
    {
        if (r.rateInfo.totalPaymentAmount.inclusive != 0)
        {
            val policiesList = new util.ArrayList[CancelPolicies]
            if (r.cancellation.policyDates != null)
            {
                for (policyDate <- r.cancellation.policyDates)
                {
                    val cancelPolicies = new CancelPolicies
                    cancelPolicies.setCurrency(r.currency)
                    cancelPolicies.setStart(Option(policyDate.after).getOrElse(""))
                    cancelPolicies.setEnd(Option(policyDate.before).getOrElse(""))
                    cancelPolicies.setAmount(policyDate.rate.inclusive)

                    policiesList.add(cancelPolicies)
                }
            }

            return Option(policiesList)
        }

        Option.empty
    }

    def _populateLinks(r: Room, hotel: Hotel): util.HashMap[String, Link] =
    {
        val linkMap = new util.HashMap[String, Link]
        val link = new Link
        val id = hotel.id
        link.setHref("/2.1/properties/availability/" + id + "/rooms/201300484/rates/206295235/payment-options?token=Ql1WAERHXV1QO")
        link.setMethod("GET")
        linkMap.put("payment_options", link)

        linkMap
    }

    def _populateBedGroups(r: Room, hotel: Hotel): util.ArrayList[BedGroups] =
    {
        val bedGroupList = new util.ArrayList[BedGroups]
        val bedGroups = new BedGroups

        val links = new util.HashMap[String, Link]
        val link = new Link

        link.setMethod("GET")
        link.setHref("/2.1/properties/availability/" + hotel.id + "/rooms/201300484/rates/206295235/price-check?token=Ql1WAERHXV1QO")
        links.put("price_check", link)

        bedGroups.setLinks(links)
        bedGroupList.add(bedGroups)

        bedGroupList
    }

    def _populateAmenities(r: Room): util.List[Amenities] =
    {
        val amenitiesList: util.List[Amenities] = new util.ArrayList[Amenities]

        if (r.benefits != null)
        {
            for (benefit <- r.benefits.iterator)
            {
                val amenities: Amenities = new Amenities
                amenities.setId(benefit.id)
                amenitiesList.add(amenities)
            }
        }

        amenitiesList
    }
}
