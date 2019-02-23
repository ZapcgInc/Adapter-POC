package com.hopper.model.availability.eps

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import com.hopper.model.availability.agoda.response.PolicyDate

@JsonIgnoreProperties(ignoreUnknown = true)
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