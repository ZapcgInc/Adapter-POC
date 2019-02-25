package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityAmenities
{
    @JsonProperty("id")
    var id: String = _

    @JsonProperty("name")
    var name: String = _

    def this(p_id:String)
    {
        this()
        id = p_id
    }
}
