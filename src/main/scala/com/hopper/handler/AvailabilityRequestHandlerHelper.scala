package com.hopper.handler

import java.time.{Duration, LocalDate}

import com.hopper.model.availability.agoda.request.AvailabilityRequestV2
import com.hopper.model.availability.agoda.response.{AvailabilityLongResponseV2, Hotel, Room}
import com.hopper.model.availability.eps.{EPSShoppingResponse, PropertyAvailability, PropertyAvailabilityAmenities, PropertyAvailabilityBedGroups, PropertyAvailabilityCancelPenalties, PropertyAvailabilityLinks, PropertyAvailabilityPrice, PropertyAvailabilityRates, PropertyAvailabilityRoom, PropertyAvailabilityRoomRates}
import com.hopper.model.availability.eps.{PropertyAvailabilityPriceWithCurrency, PropertyAvailabilityTotalPrice}
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object AvailabilityRequestHandlerHelper
{

    import com.hopper.model.error.EPSErrorResponse

    def convertToEPSResponse(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2): EPSShoppingResponse =
    {
        val lengthOfStay: Int = _calculateLengthOfStay(request)

        val propertyList: ListBuffer[PropertyAvailability] = new ListBuffer[PropertyAvailability]

        for (hotel <- response.hotels)
        {
            val property = new PropertyAvailability
            property.property_id = hotel.id

            val roomList = new ListBuffer[PropertyAvailabilityRoom]
            for (r <- hotel.rooms)
            {
                val room: PropertyAvailabilityRoom = _populateRoomInfo(request, r, hotel, lengthOfStay)
                roomList += room
            }

            property.rooms = roomList.toArray
            propertyList += property
        }

        val responseInEPSStandard: EPSShoppingResponse = new EPSShoppingResponse()
        responseInEPSStandard.properties = propertyList.toArray

        return responseInEPSStandard
    }

    def convertToErrorResponse(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2) : EPSErrorResponse =
    {
        return null
    }

    def _calculateLengthOfStay(request: AvailabilityRequestV2): Int =
    {
        val checkin: LocalDate = LocalDate.parse(request.checkInDate)
        val checkout: LocalDate = LocalDate.parse(request.checkOutDate)
        Duration.between(checkin.atStartOfDay, checkout.atStartOfDay).toDays.toInt
    }

    def _populateRoomInfo(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): PropertyAvailabilityRoom =
    {
        val room: PropertyAvailabilityRoom = new PropertyAvailabilityRoom

        room.id = r.id
        room.room_name = r.name
        room.rates = _getRateList(request, r, hotel, lengthOfStay)

        room
    }

    def _getRateList(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): Array[PropertyAvailabilityRates] =
    {
        val rate: PropertyAvailabilityRates = _populateRateInfo(request, r, hotel, lengthOfStay)

        Array(rate)
    }

    def _populateRateInfo(request: AvailabilityRequestV2, r: Room, hotel: Hotel, lengthOfStay: Int): PropertyAvailabilityRates =
    {
        import com.hopper.model.availability.eps.PropertyAvailabilityCancelPenalties
        val rate: PropertyAvailabilityRates = new PropertyAvailabilityRates
        rate.id = r.rateCategoryID
        rate.availableRooms = r.remainingRooms

        if (r.cancellation != null && r.cancellation.policyParameters != null)
        {
            rate.refundable = r.cancellation.policyParameters.head.days != 365
        }

        rate.depositRequired = false
        rate.fencedDeal = false
        rate.merchantOfRecordString = "TBD"

        rate.amenities = _populateAmenities(r)
        rate.links = _populateLinks(r, hotel)
        rate.bedGroups = _populateBedGroups(r, hotel)

        val cancelPolicies: Option[Array[PropertyAvailabilityCancelPenalties]] = _populateCancelPolicies(r)
        if (cancelPolicies.isDefined)
        {
            rate.cancelPolicies = cancelPolicies.get
        }

        rate.roomPriceByOccupancy = _populateRates(request, r, lengthOfStay)

        if (r.rateInfo.promotionType != null)
        {
            rate.promoId = r.rateInfo.promotionType.id
            rate.promoDesc = r.rateInfo.promotionType.text
        }

        rate
    }

    def _populateRates(request: AvailabilityRequestV2, r: Room, lengthOfStay: Int): Map[String, PropertyAvailabilityRoomRates] =
    {

        val basePrice = new PropertyAvailabilityPrice
        basePrice.priceType = "base_rate"
        basePrice.value = r.rateInfo.rate.exclusive
        basePrice.currency = r.currency

        val saleTaxPrice: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        saleTaxPrice.priceType = "sales_tax"
        saleTaxPrice.currency = r.currency
        saleTaxPrice.value = r.rateInfo.rate.tax

        val taxAndServiceFee: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        taxAndServiceFee.priceType = "tax_and_service_fee"
        taxAndServiceFee.currency = r.currency
        taxAndServiceFee.value = r.rateInfo.rate.fees


        val totalPrice: PropertyAvailabilityTotalPrice = new PropertyAvailabilityTotalPrice
        totalPrice.inclusive = _populateInclusivePriceWithCurrency(r)
        totalPrice.exclusive = _populateExclusivePriceWithCurrency(r)

        val priceList = List(basePrice, saleTaxPrice, taxAndServiceFee)

        val nightPriceList = new ListBuffer[List[PropertyAvailabilityPrice]]
        (1 to lengthOfStay).map(_ => nightPriceList += priceList)

        val roomPrice: PropertyAvailabilityRoomRates = new PropertyAvailabilityRoomRates
        print("ListOfS:"+r.rateInfo.surcharges)
        if(r.rateInfo.surcharges!=null) {
            var mandatory_fee = new PropertyAvailabilityPrice
            var mandatory_tax = new PropertyAvailabilityPrice
            var stayPriceProperty = new PropertyAvailabilityPrice
            var stayPriceSale = new PropertyAvailabilityPrice
            for (surcharge <- r.rateInfo.surcharges) {
                print("Surcharge:"+surcharge)
                if(surcharge.charge!=null) {
                    if (surcharge.charge.equals("Excluded")) {
                        mandatory_fee.priceType = "mandatory_fee"
                        mandatory_fee.value = surcharge.rate.exclusive
                        mandatory_fee.currency = r.currency
                        mandatory_tax.priceType = "mandatory_tax"
                        mandatory_tax.value = surcharge.rate.tax
                        mandatory_tax.currency = r.currency
                    } else {
                        stayPriceProperty.priceType = "Property_fee"
                        stayPriceProperty.value = surcharge.rate.exclusive
                        stayPriceProperty.currency = r.currency
                        stayPriceSale.priceType = "sales_tax"
                        stayPriceSale.currency = r.currency
                        stayPriceSale.value = surcharge.rate.tax
                    }
                }
            }
            roomPrice.fees=Array(mandatory_fee,mandatory_tax)
            roomPrice.stayPrice=Array(stayPriceProperty,stayPriceSale)
        }

        roomPrice.nightlyPrice = nightPriceList.toList
        roomPrice.totals = totalPrice

        var occupancyMap: scala.collection.mutable.Map[String, PropertyAvailabilityRoomRates] = scala.collection.mutable.Map[String, PropertyAvailabilityRoomRates]()
        for (ocy <- request.occupancy)
        {
            occupancyMap += (ocy -> roomPrice)
        }

        // return immutable Map.
        occupancyMap.toMap
    }

    def _populateInclusivePriceWithCurrency(r: Room) : PropertyAvailabilityPriceWithCurrency =
    {
        val priceWithCurrencyInclusive:PropertyAvailabilityPriceWithCurrency = new PropertyAvailabilityPriceWithCurrency

        val billable: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        billable.value = r.rateInfo.totalPaymentAmount.inclusive
        billable.currency = r.currency

        priceWithCurrencyInclusive.billable = billable

        val request: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        request.value = r.rateInfo.totalPaymentAmount.inclusive
        request.currency = r.currency

        priceWithCurrencyInclusive.request = request

        priceWithCurrencyInclusive
    }

    def _populateExclusivePriceWithCurrency(r: Room) : PropertyAvailabilityPriceWithCurrency =
    {
        val priceWithCurrencyExclusive:PropertyAvailabilityPriceWithCurrency = new PropertyAvailabilityPriceWithCurrency

        val billable: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        billable.value = r.rateInfo.totalPaymentAmount.exclusive
        billable.currency = r.currency

        priceWithCurrencyExclusive.billable = billable

        val request: PropertyAvailabilityPrice = new PropertyAvailabilityPrice
        request.value = r.rateInfo.totalPaymentAmount.exclusive
        request.currency = r.currency

        priceWithCurrencyExclusive.request = request

        priceWithCurrencyExclusive
    }

    def _populateCancelPolicies(r: Room): Option[Array[PropertyAvailabilityCancelPenalties]] =
    {
        if (r.rateInfo.totalPaymentAmount.inclusive != 0)
        {
            val policiesList = new ListBuffer[PropertyAvailabilityCancelPenalties]
            if (r.cancellation.policyDates != null)
            {
                for (policyDate <- r.cancellation.policyDates)
                {
                    val cancelPolicies = new PropertyAvailabilityCancelPenalties
                    cancelPolicies.currency = r.currency
                    cancelPolicies.start = Option(policyDate.after).getOrElse("")
                    cancelPolicies.end = Option(policyDate.before).getOrElse("")
                    cancelPolicies.amount = policyDate.rate.inclusive

                    policiesList += cancelPolicies
                }
            }

            return Option(policiesList.toArray)
        }

        Option.empty
    }

    def _populateLinks(r: Room, hotel: Hotel): Map[String, PropertyAvailabilityLinks] =
    {
        val link = new PropertyAvailabilityLinks
        link.href = "/2.1/properties/availability/" + hotel.id + "/rooms/201300484/rates/206295235/payment-options?token=Ql1WAERHXV1QO"
        link.method = "GET"

        Map("payment_options" -> link)
    }

    def _populateBedGroups(r: Room, hotel: Hotel): Array[PropertyAvailabilityBedGroups] =
    {
        val bedGroup = new PropertyAvailabilityBedGroups
        val link = new PropertyAvailabilityLinks
        link.method = "GET"
        link.href = "/2.1/properties/availability/" + hotel.id + "/rooms/201300484/rates/206295235/price-check?token=Ql1WAERHXV1QO"

        bedGroup.links = Map("price_check" -> link)

        Array(bedGroup)
    }

    def _populateAmenities(r: Room): Array[PropertyAvailabilityAmenities] =
    {
        val amenitiesList = new ListBuffer[PropertyAvailabilityAmenities]

        if (r.benefits != null)
        {
            for (benefit <- r.benefits.iterator)
            {
                val amenity: PropertyAvailabilityAmenities = new PropertyAvailabilityAmenities
                amenity.id = benefit.id
                amenitiesList += amenity
            }
        }

        amenitiesList.toArray
    }
}
