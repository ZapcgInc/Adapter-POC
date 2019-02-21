package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityCancelPenalties
{
    @JsonProperty("currency")
    var currency: String = _

    @JsonProperty("start")
    var start: String = _

    @JsonProperty("end")
    var end: String = _

    @JsonProperty("amount")
    var amount: Double = _

    @JsonProperty("nights")
    var nights: Int = _

    @JsonProperty("percent")
    var percentage: Float = _

}