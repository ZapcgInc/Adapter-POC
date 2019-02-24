package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityRoomRates
{
    @JsonProperty("nightly")
    var nightlyPrice: List[List[PropertyAvailabilityPrice]] = _

    @JsonProperty("stay")
    var stayPrice: Array[PropertyAvailabilityPrice] = _

    @JsonProperty("fees")
    var fees: Array[PropertyAvailabilityPrice] = _

    @JsonProperty("totals")
    var totals: PropertyAvailabilityTotalPrice = _
}