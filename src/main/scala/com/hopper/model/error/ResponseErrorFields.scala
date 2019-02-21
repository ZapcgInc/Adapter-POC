package com.hopper.model.error

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseErrorFields
{
    @JsonProperty("type")
    var errorType: String = _

    @JsonProperty("name")
    var name: String = _

    @JsonProperty("value")
    var value: String = _
}
