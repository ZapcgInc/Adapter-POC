package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object SalesEnvironmentValidator
{
  private val VALID_SALES_ENV: List[String] = List("hotel_only", "hotel_package", "loyalty")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val sales:String = request.getParam(AvailabilityRequestParams.SALES_ENVIRONMENT.toString)

    if (!VALID_SALES_ENV.contains(sales))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SALES_ENVIRONMENT.toString).get)
    }

    None
  }
}
