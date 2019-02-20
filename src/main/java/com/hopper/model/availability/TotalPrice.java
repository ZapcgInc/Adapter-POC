package com.hopper.model.availability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalPrice
{
    @JsonProperty("inclusive")
    private PriceWithCurrency inclusive;

    @JsonProperty("exclusive")
    private PriceWithCurrency exclusive;

    @JsonProperty("strikethrough")
    private PriceWithCurrency strikeThrough;

    @JsonProperty("marketing_fee")
    private PriceWithCurrency marketingFee;

    @JsonProperty("minimum_selling_price")
    private PriceWithCurrency minSellingPrice;

    public PriceWithCurrency getInclusive()
    {
        return inclusive;
    }

    public PriceWithCurrency getExclusive()
    {
        return exclusive;
    }

    public PriceWithCurrency getStrikeThrough()
    {
        return strikeThrough;
    }

    public PriceWithCurrency getMarketingFee()
    {
        return marketingFee;
    }

    public PriceWithCurrency getMinSellingPrice()
    {
        return minSellingPrice;
    }

    public void setInclusive(PriceWithCurrency inclusive) {
        this.inclusive = inclusive;
    }

    public void setExclusive(PriceWithCurrency exclusive) {
        this.exclusive = exclusive;
    }

    public void setStrikeThrough(PriceWithCurrency strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public void setMarketingFee(PriceWithCurrency marketingFee) {
        this.marketingFee = marketingFee;
    }

    public void setMinSellingPrice(PriceWithCurrency minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    @Override
    public String toString() {
        return "TotalPrice{" +
                "inclusive=" + inclusive +
                ", exclusive=" + exclusive +
                ", strikeThrough=" + strikeThrough +
                ", marketingFee=" + marketingFee +
                ", minSellingPrice=" + minSellingPrice +
                '}';
    }
}
