package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object CountryCodeValidator
{
  private val VALID_COUNTRY_CODE: List[String] = List("AG", "AL", "AO", "AS", "AU", "AZ", "BB",
    "BE", "BG", "BI", "BL", "BN", "BQ", "BS", "BV", "BY", "CA", "CD", "CG", "CI", "CL", "CN", "CR", "CW", "CY",
    "DE", "DK", "DO", "EC", "EG", "ER", "ET", "FJ", "FM", "FR", "GB", "GE", "GH", "GL", "GN", "GQ", "GS", "GU")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val countryCode:String = request.getParam(AvailabilityRequestParams.COUNTRY_CODE_KEY.toString)

    if (!VALID_COUNTRY_CODE.contains(countryCode))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.COUNTRY_CODE_KEY.toString).get)
    }

    None
  }
}
