package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object SalesChannelValidator
{
  private val VALID_SALES_CHANNEL: List[String] = List("website", "agent_tool", "mobile_app", "mobile_web", "cache", "meta")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val rateOptions:String = request.getParam(AvailabilityRequestParams.SALES_CHANNEL.toString)

    if (!VALID_SALES_CHANNEL.contains(rateOptions))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SALES_CHANNEL.toString).get)
    }

    None
  }
}
