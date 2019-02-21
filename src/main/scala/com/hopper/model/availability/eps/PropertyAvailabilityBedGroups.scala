package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityBedGroups
{
    @JsonProperty("links")
    var links: Map[String, PropertyAvailabilityLinks] = null
}
