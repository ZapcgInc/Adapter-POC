package com.hopper.commons.eps.model.error

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonInclude, JsonProperty}
import com.hopper.model.constants.{AvailabilityRequestHeaders, EPSResponseErrorType}
import com.hopper.model.constants.EPSResponseErrorType.EPSResponseErrorType

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class EPSErrorResponse
{
    @JsonProperty("type")
    var m_errorType: String = _

    @JsonProperty("message")
    var m_message: String = _

    @JsonProperty("errors")
    var m_errors: Array[ResponseError] = _

    def this(errorType: EPSResponseErrorType)
    {
        this()
        m_errorType = errorType.errorType
        m_message = errorType.errorMessage
    }

    def this(errorType: EPSResponseErrorType, error: ResponseError)
    {
        this()
        m_errorType = errorType.errorType
        m_message = errorType.errorMessage
        m_errors = Array(error)
    }
}

object EPSErrorResponseBuilder extends EPSErrorResponse
{
    def createForMissingInput(fieldName: String): Option[EPSErrorResponse] =
    {
        val responseErrorType: String = s"$fieldName.required"
        val responseErrorMessage: String = _createErrorMessageForMissingInput(fieldName)

        Some(new EPSErrorResponse(
            EPSResponseErrorType.INVALID_INPUT,
            new ResponseError(responseErrorType, responseErrorMessage, new ResponseErrorFields(fieldName))
        ))
    }

    def createForUnsupportedInput(fieldName: String): Option[EPSErrorResponse] =
    {
        val responseErrorType: String = s"$fieldName.not_supported"
        val responseErrorMessage: String = _createErrorMessageForUnsupportedInput(fieldName)

        Some(new EPSErrorResponse(
            EPSResponseErrorType.INVALID_INPUT,
            new ResponseError(responseErrorType, responseErrorMessage, new ResponseErrorFields(fieldName))
        ))
    }

    def _createErrorMessageForMissingInput(fieldName: String): String =
    {
        AvailabilityRequestHeaders.withNameOpt(fieldName) match
        {
            case Some(AvailabilityRequestHeaders.SALES_CHANNEL) =>
            {
                "Sales Channel is required.  Accepted sales_channel values are: [website, agent_tool, mobile_app, mobile_web, cache, meta]."
            }
            case _ =>
            {
                s"$fieldName is required."
            }
        }
    }

    def _createErrorMessageForUnsupportedInput(fieldName: String): String =
    {
        AvailabilityRequestHeaders.withNameOpt(fieldName) match
        {
            case Some(AvailabilityRequestHeaders.CURRENCY_CODE_KEY) =>
            {
                "Currency is not supported. Supported currencies are: [AED, ARS, AUD, BRL, CAD, CHF, CNY, DKK, EGP, EUR, GBP, HKD, IDR, ILS, INR, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RUB, SAR, SEK, SGD, THB, TRY, TWD, USD, VND, ZAR]."
            }
            case Some(AvailabilityRequestHeaders.LANGUAGE_CODE_KEY) =>
            {
                "Language is not supported. Supported languages are: [ar-SA, cs-CZ, da-DK, de-DE, el-GR, en-US, es-ES, es-MX, fi-FI, fr-CA, fr-FR, hr-HR, hu-HU, id-ID, is-IS, it-IT, ja-JP, ko-KR, lt-LT, ms-MY, nb-NO, nl-NL, pl-PL, pt-BR, pt-PT, ru-RU, sk-SK, sv-SE, th-TH, tr-TR, uk-UA, vi-VN, zh-CN, zh-TW]"
            }
            case Some(AvailabilityRequestHeaders.SALES_ENVIRONMENT) =>
            {
                "Sales Environment is required.  Accepted sales_environment values are: [hotel_only, hotel_package, loyalty]."
            }
            case _ =>
            {
                throw new RuntimeException("No message configured for field" + fieldName)
            }
        }
    }
}