package com.hopper.model.error

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class EPSErrorResponse
{
    @JsonProperty("type")
    var errorType: String = _

    @JsonProperty("message")
    var message: String = _

    @JsonProperty("errors")
    var errors: Array[ResponseErrors] = _
}
