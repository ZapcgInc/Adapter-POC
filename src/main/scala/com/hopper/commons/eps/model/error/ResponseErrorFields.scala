package com.hopper.commons.eps.model.error

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

    /** used for initialization of error in case of missing fields*/
    def this(p_name: String)
    {
        this()
        errorType = "querystring"
       // value = "null"
        name = p_name
    }

    /** used for initialization of error in case of invalid field values*/
    def this(p_name: String, p_value: String)
    {
        this()
        errorType = "querystring"
        value = p_value
        name = p_name
    }
}
