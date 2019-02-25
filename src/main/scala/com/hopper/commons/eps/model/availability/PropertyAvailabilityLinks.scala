package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityLinks
{
    @JsonProperty("method")
    var method: String = _

    @JsonProperty("href")
    var href: String = _

    def this(httpMethod:String, hrefLink: String)
    {
        this()
        method = httpMethod
        href = hrefLink
    }
}
