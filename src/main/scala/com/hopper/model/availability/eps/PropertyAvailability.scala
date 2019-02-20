package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailability
{
    @JsonProperty("property_id")
    var property_id: String = _

    @JsonProperty("rooms")
    var rooms: List[PropertyAvailabilityRooms] = _

    @JsonProperty("links")
    var links: Map[String, PropertyAvailabilityLinks] = _
}
