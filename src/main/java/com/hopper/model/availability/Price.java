package com.hopper.model.availability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price
{
    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("currency")
    private String currency;

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Price(){}

    public String getType()
    {
        return type;
    }

    public Double getValue()
    {
        return value;
    }

    public String getCurrency()
    {
        return currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "type='" + type + '\'' +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
