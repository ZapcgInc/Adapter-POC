package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityPrice
{
    @JsonProperty("type")
    var priceType: String = _

    @JsonProperty("value")
    var value: Double = _

    @JsonProperty("currency")
    var currency: String = _
}
