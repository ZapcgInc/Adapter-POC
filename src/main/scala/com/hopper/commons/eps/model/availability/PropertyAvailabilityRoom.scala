package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityRoom
{

    @JsonProperty("id")
    var id: String = _

    @JsonProperty("room_name")
    var room_name: String = _

    @JsonProperty("rates")
    var rates: Array[PropertyAvailabilityRates] = _

    def this(p_id: String, p_name: String, p_rates: Array[PropertyAvailabilityRates])
    {
        this()
        id = p_id
        room_name = p_name
        rates = p_rates
    }
}
