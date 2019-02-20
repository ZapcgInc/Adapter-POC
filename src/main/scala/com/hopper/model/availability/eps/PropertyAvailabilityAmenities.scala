package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityAmenities
{
    @JsonProperty("name")
    var id: String = _

    @JsonProperty("name")
    var name: String = _
}
