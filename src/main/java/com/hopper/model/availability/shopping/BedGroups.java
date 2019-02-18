package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.hopper.model.availability.Link;


import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BedGroups
{
    @JsonProperty("links")
    private Map<String, Link> links;


    public BedGroups(){}

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Map<String, Link> getLinks()
    {
        return links != null ? links : ImmutableMap.of();
    }

    @Override
    public String toString() {
        return "BedGroups{" +
                "links=" + links +
                '}';
    }
}
