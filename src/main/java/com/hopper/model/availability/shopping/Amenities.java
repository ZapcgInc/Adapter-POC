package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Amenities
{
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public Amenities(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return "Amenities{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
