package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityRooms
{
    @JsonProperty("id")
    var id: String = _

    @JsonProperty("room_name")
    var room_name: String = _

    @JsonProperty("rates")
    var rates: List[PropertyAvailabilityRates] = _
}
