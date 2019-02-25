package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityTotalPrice
{

    @JsonProperty("inclusive")
    var inclusive: PropertyAvailabilityPriceWithCurrency = _

    @JsonProperty("exclusive")
    var exclusive: PropertyAvailabilityPriceWithCurrency = _

    @JsonProperty("strikethrough")
    var strikeThrough: PropertyAvailabilityPriceWithCurrency = _

    @JsonProperty("marketing_fee")
    var marketingFee: PropertyAvailabilityPriceWithCurrency = _

    @JsonProperty("minimum_selling_price")
    var minSellingPrice: PropertyAvailabilityPriceWithCurrency = _

    def this(p_inclusive:PropertyAvailabilityPriceWithCurrency, p_exclusive:PropertyAvailabilityPriceWithCurrency)
    {
        this()
        inclusive = p_inclusive
        exclusive = p_exclusive
    }
}