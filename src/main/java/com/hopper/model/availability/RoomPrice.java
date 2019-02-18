package com.hopper.model.availability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomPrice
{
    @JsonProperty("nightly")
    private List<List<Price>> nightlyPrice;

    @JsonProperty("stay")
    private List<Price> stayPrice;

    @JsonProperty("fees")
    private List<Price> fees;

    @JsonProperty("totals")
    private TotalPrice totals;

    public RoomPrice() {}

    public List<List<Price>> getNightlyPrice()
    {
        return nightlyPrice != null ? nightlyPrice : ImmutableList.of();
    }

    public List<Price> getStayPrice()
    {
        return stayPrice != null ? stayPrice : ImmutableList.of();
    }

    public List<Price> getFees()
    {
        return fees != null ? fees : ImmutableList.of();
    }

    public TotalPrice getTotals()
    {
        return totals;
    }

    public void setNightlyPrice(List<List<Price>> nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }

    public void setStayPrice(List<Price> stayPrice) {
        this.stayPrice = stayPrice;
    }

    public void setFees(List<Price> fees) {
        this.fees = fees;
    }

    public void setTotals(TotalPrice totals) {
        this.totals = totals;
    }

    @Override
    public String toString() {
        return "RoomPrice{" +
                "nightlyPrice=" + nightlyPrice +
                ", stayPrice=" + stayPrice +
                ", fees=" + fees +
                ", totals=" + totals +
                '}';
    }
}
