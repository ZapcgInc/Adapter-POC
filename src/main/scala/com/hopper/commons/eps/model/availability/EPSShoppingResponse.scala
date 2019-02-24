package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class EPSShoppingResponse(val properties: Array[PropertyAvailability])
{
    /*no-arg constructor for JSON Parser*/
    def this()
    {
        this(null)
    }
}


