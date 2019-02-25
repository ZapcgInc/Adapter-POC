package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestHeaders

object RateOptionValidator
{
  private val VALID_RATE_OPTION: List[String] = List("net_rates", "closed_user_group")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val rateOptions:String = request.getParam(AvailabilityRequestHeaders.RATE_OPTION.toString)

    if (!VALID_RATE_OPTION.contains(rateOptions))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestHeaders.RATE_OPTION.toString).get)
    }

    None
  }
}
