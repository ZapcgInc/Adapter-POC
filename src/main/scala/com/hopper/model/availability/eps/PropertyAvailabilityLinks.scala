package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityLinks
{
    @JsonProperty("method")
    var method: String = _

    @JsonProperty("href")
    var href: String = _
}
