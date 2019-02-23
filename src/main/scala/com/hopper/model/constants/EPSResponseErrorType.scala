package com.hopper.model.constants

object EPSResponseErrorType extends Enumeration
{
    case class ErrorInfo(errorType: String, errorMessage:String) extends Val(errorType)

    type EPSResponseErrorType = ErrorInfo

    val NO_AVAILABILITY = new ErrorInfo("no_availability", "No availability was found for the properties requested.")
    val INVALID_INPUT = new ErrorInfo("invalid_input", "An invalid request was sent in, please check the nested errors for details.")
    val REQUEST_FORBIDDEN = new ErrorInfo("request_forbidden", "Your request could not be authorized. Ensure that you have access.")
    val SERVICE_UNAVAILABLE = new ErrorInfo("service_unavailable", "This service is currently unavailable.")
    val UNKNOWN_INTERNAL_ERROR = new ErrorInfo("unknown_internal_error", "An internal server error has occurred.")
}