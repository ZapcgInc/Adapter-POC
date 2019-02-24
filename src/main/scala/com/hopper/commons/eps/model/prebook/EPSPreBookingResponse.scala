package com.hopper.commons.eps.model.prebook

import com.fasterxml.jackson.annotation.JsonProperty
import com.hopper.commons.eps.model.availability.{PropertyAvailabilityLinks, PropertyAvailabilityRoomRates}

class EPSPreBookingResponse
{
    @JsonProperty("status")
    var status: String = _

    @JsonProperty("occupancies")
    var roomPriceByOccupancy: Map[String, PropertyAvailabilityRoomRates] = _

    @JsonProperty("links")
    var links: Map[String, PropertyAvailabilityLinks] = _

    def this(p_status: String, p_roomPriceByOccupancy: Map[String, PropertyAvailabilityRoomRates], p_links: Map[String, PropertyAvailabilityLinks])
    {
        this()
        status = p_status
        roomPriceByOccupancy = p_roomPriceByOccupancy
        links = p_links
    }
}
