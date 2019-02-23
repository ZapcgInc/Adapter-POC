package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
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
