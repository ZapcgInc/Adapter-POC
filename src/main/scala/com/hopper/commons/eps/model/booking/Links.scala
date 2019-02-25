package com.hopper.commons.eps.model.booking

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
class Links
{
    @JsonProperty("method")
    var method: String = _

    @JsonProperty("href")
    var href: String = _

    def this(httpMethod: String, hrefLink: String)
    {
        this()
        method = httpMethod
        href = hrefLink
    }
}
