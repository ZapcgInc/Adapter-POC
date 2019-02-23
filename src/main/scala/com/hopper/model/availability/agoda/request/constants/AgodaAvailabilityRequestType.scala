package com.hopper.model.availability.agoda.request.constants

object AgodaAvailabilityRequestType extends Enumeration
{
    val HotelSearch = Value(4)
    val HotelListSearch = Value(6)

    def get(numberOfPropertyIDs: Int): Int =
    {
        if (numberOfPropertyIDs == 1)
        {
            AgodaAvailabilityRequestType.HotelSearch.id
        }
        else
        {
            AgodaAvailabilityRequestType.HotelListSearch.id
        }
    }
}
