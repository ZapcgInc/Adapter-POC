package com.hopper.model.availability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Link
{
    @JsonProperty("method")
    private String method;

    @JsonProperty("href")
    private String href;

    public Link(){}

    public String getMethod()
    {
        return method;
    }

    public String getHref()
    {
        return href;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String toString()
    {
        return new StringBuilder()
                .append("METHOD").append(":").append(method).append(",")
                .append("HREF").append(":").append(href)
                .toString();
    }
}
