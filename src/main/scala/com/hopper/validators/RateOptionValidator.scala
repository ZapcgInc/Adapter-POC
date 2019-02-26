package com.hopper.validators
import com.hopper.commons.eps.model.error.{EPSErrorResponse, EPSErrorResponseBuilder}
import com.hopper.model.constants.AvailabilityRequestParams
import com.twitter.finagle.http.Request

object RateOptionValidator
{
  private val VALID_RATE_OPTION: List[String] = List("net_rates", "closed_user_group")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val rateOptions:String = request.getParam(AvailabilityRequestParams.RATE_OPTION.toString)

    if (rateOptions!=null && !VALID_RATE_OPTION.contains(rateOptions))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.RATE_OPTION.toString).get)
    }

    None
  }
}
