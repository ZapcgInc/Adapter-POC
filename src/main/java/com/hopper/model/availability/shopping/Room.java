package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room
{
    @JsonProperty("id")
    private String id;

    @JsonProperty("room_name")
    private String name;

    @JsonProperty("rates")
    private List<Rate> rates;

    public Room(){}

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<Rate> getRates()
    {
        return rates != null ? rates : ImmutableList.of();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rates=" + rates +
                '}';
    }
}
