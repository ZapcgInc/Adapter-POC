package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailability
{
    @JsonProperty("property_id")
    var property_id: String = _

    @JsonProperty("rooms")
    var rooms: Array[PropertyAvailabilityRoom] = _

    @JsonProperty("links")
    var links: Map[String, PropertyAvailabilityLinks] = _

    def this(hotelID: String, hotelRooms: Array[PropertyAvailabilityRoom])
    {
        this()
        property_id = hotelID
        rooms = hotelRooms
    }
}
