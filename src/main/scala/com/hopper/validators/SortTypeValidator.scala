package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object SortTypeValidator
{
  private val VALID_SORT_TYPE: List[String] = List("preferred")

  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val sortType:String = request.getParam(AvailabilityRequestParams.SORT_TYPE.toString)

    if (!VALID_SORT_TYPE.contains(sortType))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.SORT_TYPE.toString).get)
    }

    None
  }
}
