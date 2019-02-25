package com.hopper.commons.eps.model.availability

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}
import com.hopper.model.agoda.availability.response.PolicyDate

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropertyAvailabilityCancelPenalties
{
    @JsonProperty("currency")
    var currency: String = _

    @JsonProperty("start")
    var start: String = _

    @JsonProperty("end")
    var end: String = _

    @JsonProperty("amount")
    var amount: Double = _

    @JsonProperty("nights")
    var nights: Int = _

    @JsonProperty("percent")
    var percentage: Float = _

    def this(p_currency:String, p_start:String, p_end:String, p_amount:Double)
    {
        this()
        currency = p_currency
        start = p_start
        end = p_end
        amount = p_amount
    }


    def this(p_currency:String, policyDate: PolicyDate)
    {
        this()
        currency = p_currency
        start = Option(policyDate.after).getOrElse("")
        end = Option(policyDate.before).getOrElse("")
        amount =  policyDate.rate.inclusive
    }
}