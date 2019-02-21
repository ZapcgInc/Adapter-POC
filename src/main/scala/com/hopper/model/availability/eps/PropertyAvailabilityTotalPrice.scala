package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
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
}