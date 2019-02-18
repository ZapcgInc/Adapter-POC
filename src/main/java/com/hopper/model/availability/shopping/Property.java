package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hopper.model.availability.Link;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Property
{
    @JsonProperty("property_id")
    private String propertyId;

    @JsonProperty("rooms")
    private List<Room> rooms;

    @JsonProperty("score")
    private String score;

    @JsonProperty("links")
    private Map<String, Link> links;


    public Property(){}

    public String getPropertyId()
    {
        return propertyId;
    }

    public List<Room> getRooms()
    {
        return rooms != null ? rooms : ImmutableList.of();
    }

    public String getScore()
    {
        return score;
    }

    public Map<String, Link> getLinks()
    {
        return links != null ? links : ImmutableMap.of();
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyId='" + propertyId + '\'' +
                ", rooms=" + rooms +
                ", score='" + score + '\'' +
                ", links=" + links +
                '}';
    }
}
