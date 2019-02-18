package com.hopper.model.availability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceWithCurrency
{
    @JsonProperty("billable_currency")
    private Price billable;

    @JsonProperty("request_currency")
    private Price request;

    public PriceWithCurrency(){}

    public Price getBillable()
    {
        return billable;
    }

    public Price getRequest()
    {
        return request;
    }

    @Override
    public String toString() {
        return "PriceWithCurrency{" +
                "billable=" + billable +
                ", request=" + request +
                '}';
    }
}
