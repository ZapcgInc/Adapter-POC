package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object FilterValidator
{
  private val VALID_FILTER_OPTION: List[String] = List("refundable", "expedia_collect","property_collect")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val filter:String = request.getParam(AvailabilityRequestParams.FILTER.toString)

    if (filter!=null && !VALID_FILTER_OPTION.contains(filter))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.FILTER.toString).get)
    }
    None
  }
}
