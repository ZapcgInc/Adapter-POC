package com.hopper.commons.eps.model.booking

import com.fasterxml.jackson.annotation.JsonProperty

class EPSBookingResponse
{
    @JsonProperty("itinerary_id")
    var itineraryID: String = _

    @JsonProperty("links")
    var links: Map[String, Links] = _

    def this(p_itineraryID: String, p_links: Map[String, Links])
    {
        this()
        itineraryID = p_itineraryID
        links = p_links
    }
}
