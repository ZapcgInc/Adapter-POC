package com.hopper.model.availability;

public enum RequestType
{
    HotelSearch(4),
    HotelListSearch(6),
    ;

    private final int m_typeId;

    RequestType(int typeId)
    {
        m_typeId = typeId;
    }

    public int getID()
    {
        return m_typeId;
    }
}
