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

    def this(p_value: Double, p_currency: String)
    {
        this()
        value = p_value
        currency = p_currency
    }

    def this(p_type : String, p_value: Double, p_currency: String)
    {
        this()
        priceType  = p_type
        value = p_value
        currency = p_currency
    }
}
