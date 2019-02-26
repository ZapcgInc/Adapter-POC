package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object CurrencyCodeValidator
{
    private val VALID_CURRENCY_CODES: List[String] = List("AED", "ARS", "AUD", "BRL", "CAD", "CHF", "CNY", "DKK", "EGP", "EUR", "GBP",
        "HKD", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RUB", "SAR", "SEK", "SGD", "THB",
        "TRY", "TWD", "USD", "VND", "ZAR")

    def validate(request: Request): Option[EPSErrorResponse] =
    {
        val currencyCode:String = request.getParam(AvailabilityRequestParams.CURRENCY_CODE_KEY.toString)

        if (!VALID_CURRENCY_CODES.contains(currencyCode))
        {
            return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.CURRENCY_CODE_KEY.toString).get)
        }

        None
    }
}
