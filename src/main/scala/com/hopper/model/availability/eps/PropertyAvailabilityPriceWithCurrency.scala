package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyAvailabilityPriceWithCurrency
{
    @JsonProperty("billable_currency")
    var billable: PropertyAvailabilityPrice = _

    @JsonProperty("request_currency")
    var request: PropertyAvailabilityPrice = _

    def this(p_billable: PropertyAvailabilityPrice, p_request: PropertyAvailabilityPrice)
    {
        this()
        billable = p_billable
        request = p_request
    }
}