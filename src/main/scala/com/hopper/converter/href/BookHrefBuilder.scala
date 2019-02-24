package com.hopper.converter.href

object BookHrefBuilder
{
    private val BOOKING_HREF_TEMPLATE: String = s"/itineraries?token=%s"

    def buildHref(): String =
    {
        val bookingToken:String = "TODOTOken"
        BOOKING_HREF_TEMPLATE.format(bookingToken)
    }

    class AvailabilityRequestParams
    {
        var checkInDate: String = _
        var checkOutDate: String = _
        var roomCount: Int = _
        var adultsCount: Int = _
        var childrenCount: Int = _
        var childrenAges: Array[Int] = _
        var language: String = _
        var currency: String = _
        var occupancy: List[String] = _

    }
}
