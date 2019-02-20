package com.hopper.model.availability.eps

import java.util.List

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityRoomRates
{
    @JsonProperty("nightly")
    var nightlyPrice: List[List[PropertyAvailabilityPrice]] = _

    @JsonProperty("stay")
    var stayPrice: List[PropertyAvailabilityPrice] = _

    @JsonProperty("fees")
    var fees: List[PropertyAvailabilityPrice] = _

    @JsonProperty("totals")
    var totals: PropertyAvailabilityTotalPrice = _
}