package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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