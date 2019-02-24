package com.hopper.commons.eps.model.error

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseError
{
    @JsonProperty("type")
    var errorType: String = _

    @JsonProperty("message")
    var message: String = _

    @JsonProperty("fields")
    var fields: Array[ResponseErrorFields] = _

    def this(p_errorType: String, p_message: String, p_field: ResponseErrorFields)
    {
        this()
        errorType = p_errorType
        message = p_message
        fields = Array(p_field)
    }

    def this(p_errorType: String, p_message: String)
    {
        this()
        errorType = p_errorType
        message = p_message
    }
}
