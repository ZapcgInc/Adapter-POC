package com.hopper.converter.href

import com.hopper.model.agoda.availability.request.AvailabilityRequestV2
import com.hopper.model.agoda.availability.response.{AvailabilityLongResponseV2, Hotel, Room}

object BookHrefBuilder
{

    import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
    import com.fasterxml.jackson.module.scala.DefaultScalaModule
    import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
    import com.hopper.commons.util.GZIPStringURLEncoder

    private val BOOKING_HREF_TEMPLATE: String = s"/itineraries?token=%s"

    def buildHref(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2, hotelID: String, roomID: String): String =
    {
        val hotel: Option[Hotel] = response.hotels.find(hotel => hotel.id == hotelID)
        if (hotel.isEmpty)
        {
            None
        }

        val room: Option[Room] = hotel.get.rooms.find(room => room.id == roomID)
        if (room.isEmpty)
        {
            None
        }

        val requestParams: BookingRequestParams = create(request, response, hotel.get, room.get)

        val jsonResponse: String = (new ObjectMapper).registerModule(DefaultScalaModule).writeValueAsString(requestParams)
        val encodedToken: String = GZIPStringURLEncoder.encodeString(jsonResponse)

        BOOKING_HREF_TEMPLATE.format(encodedToken)
    }

    def getBookRequestDetail(token: String): BookingRequestParams =
    {
        val jsonString: String = GZIPStringURLEncoder.decodeString(token)

        val objectMapper = new ObjectMapper() with ScalaObjectMapper
        objectMapper.registerModule(DefaultScalaModule)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        objectMapper.readValue[BookingRequestParams](jsonString)
    }

    def create(request: AvailabilityRequestV2, response: AvailabilityLongResponseV2, hotel: Hotel, room: Room): BookingRequestParams =
    {
        val bookRoom: com.hopper.model.agoda.booking.Room = new com.hopper.model.agoda.booking.Room

        bookRoom.id = room.id
        bookRoom.name = room.name
        bookRoom.lineItemID = room.lineItemID
        bookRoom.ratePlan = room.ratePlan
        bookRoom.rateType = room.rateType
        bookRoom.currency = room.currency
        bookRoom.model = room.model
        bookRoom.rateCategoryID = room.rateCategoryID
        bookRoom.blockID = room.blockID

        bookRoom.roomCount = request.roomCount
        bookRoom.adultsCount = request.adultsCount
        bookRoom.childrenCount = request.childrenCount
        bookRoom.childrenAges = request.childrenAges

        bookRoom.rate = new com.hopper.model.agoda.booking.Rate(room.rateInfo.rate)
        bookRoom.surcharges = Option(room.rateInfo.surcharges)
          .map(sc => sc.map(c => new com.hopper.model.agoda.booking.Surcharge(c)))
          .orNull

        val bookHotel: com.hopper.model.agoda.booking.Hotel = new com.hopper.model.agoda.booking.Hotel
        bookHotel.id = hotel.id
        bookHotel.rooms = Array(bookRoom)

        val requestParams: BookingRequestParams = new BookingRequestParams
        requestParams.searchID = response.searchID
        requestParams.checkInDate = request.checkInDate
        requestParams.checkOutDate = request.checkOutDate
        requestParams.userCountry = "US"
        requestParams.hotel = bookHotel

        requestParams
    }

    class BookingRequestParams
    {
        var searchID: String = _
        var checkInDate: String = _
        var checkOutDate: String = _
        var userCountry: String = _
        var hotel: com.hopper.model.agoda.booking.Hotel = _
    }

}
