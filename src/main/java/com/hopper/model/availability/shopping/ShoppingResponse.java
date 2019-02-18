package com.hopper.model.availability.shopping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

//import com.hopper.tests.data.model.response.ErrorResponse;

/**
 * Simple Value Object for Shopping Response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingResponse {
    private List<Property> m_properties;
    // private ErrorResponse errorResponse;

    public ShoppingResponse() {
        m_properties = null;
    }

//    public void setM_properties(List<Property> m_properties) {
//        this.m_properties = m_properties;
//    }

    public ShoppingResponse(Property[] properties) {
        m_properties = Arrays.asList(properties);
    }


    public List<Property> getProperties() {
        return m_properties != null ? m_properties : ImmutableList.of();
    }

    @Override
    public String toString() {
        return "ShoppingResponse{" +
                "m_properties=" + m_properties +
                '}';
    }
}
