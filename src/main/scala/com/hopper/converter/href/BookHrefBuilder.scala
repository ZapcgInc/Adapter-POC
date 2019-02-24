package com.hopper.converter.href

object BookHrefBuilder
{
    private val BOOKING_HREF_TEMPLATE: String = s"/itineraries?token=%s"

    def buildHref(): String =
    {
        val bookingToken:String = "TODOTOken"
        BOOKING_HREF_TEMPLATE.format(bookingToken)
    }
}
