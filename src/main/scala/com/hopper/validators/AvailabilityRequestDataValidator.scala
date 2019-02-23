package com.hopper.validators

import java.time.format.DateTimeFormatter
import java.time.{Duration, LocalDate}
import java.util

import com.hopper.model.constants.EPSResponseErrorType
import com.hopper.model.error.{EPSErrorResponse, EPSErrorResponseBuilder, ResponseError, ResponseErrorFields}
import com.twitter.finagle.http.Request
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.StringUtils
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import com.twitter.finagle.http.Status

import scala.collection.JavaConversions._

/**
  * Util class to validate Request Data
  */
object AvailabilityRequestDataValidator extends RequestValidator
{

    import com.hopper.model.constants.AvailabilityRequestHeaders

    val VALID_CURRENCY_CODES: util.List[String] = List("AED", "ARS", "AUD", "BRL", "CAD", "CHF", "CNY", "DKK", "EGP", "EUR", "GBP",
        "HKD", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RUB", "SAR", "SEK", "SGD", "THB",
        "TRY", "TWD", "USD", "VND", "ZAR")

    val VALID_LANGUAGE_CODES: util.List[String] = List("ar-SA", "cs-CZ", "da-DK", "de-DE", "el-GR", "en-US", "es-ES",
        "es-MX", "fi-FI", "fr-CA", "fr-FR", "hr-HR", "hu-HU", "id-ID", "is-IS", "it-IT", "ja-JP", "ko-KR",
        "lt-LT", "ms-MY", "nb-NO", "nl-NL", "pl-PL", "pt-BR", "pt-PT", "ru-RU", "sk-SK", "sv-SE", "th-TH",
        "tr-TR", "uk-UA", "vi-VN", "zh-CN", "zh-TW")

    def validate(request: Request): Option[(HttpResponseStatus, EPSErrorResponse)] =
    {
        for (header <- AvailabilityRequestHeaders.values)
        {
            var errorResponse: Option[EPSErrorResponse] = _validateMissingOrBlank(request, header.toString)
            if (errorResponse.isDefined)
            {
                return Some(Status.BadRequest, errorResponse.get);
            }

            errorResponse = _validateStayInfo(request)
            if (errorResponse.isDefined)
            {
                return Some(Status.BadRequest, errorResponse.get)
            }

            header match
            {
                case AvailabilityRequestHeaders.CURRENCY_CODE_KEY =>
                {
                    if (!VALID_CURRENCY_CODES.contains(request.getParam(header.toString)))
                    {
                        return Some(Status.BadRequest, EPSErrorResponseBuilder.createForUnsupportedInput(header.toString).get)
                    }
                }
                case AvailabilityRequestHeaders.LANGUAGE_CODE_KEY =>
                {
                    if (!VALID_LANGUAGE_CODES.contains(request.getParam(header.toString)))
                    {
                        return Some(Status.BadRequest, EPSErrorResponseBuilder.createForUnsupportedInput(header.toString).get)
                    }
                }
                case AvailabilityRequestHeaders.PROPERTY_ID =>
                {
                    if (request.getParams(header.toString).size() > 250)
                    {
                        val responseError: ResponseError = new ResponseError(
                            "property_id.above_maximum",
                            "The number of property_id's passed in must not be greater than 250.",
                            new ResponseErrorFields("property_id", request.getParams("property_id").size().toString))

                        return Some(Status.BadRequest, new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
                    }
                }
                case AvailabilityRequestHeaders.OCCUPANCY_KEY =>
                {
                    val errorResponse: Option[EPSErrorResponse] = _validateOccupancy(request)
                    if (errorResponse.isDefined)
                    {
                        return Some(Status.BadRequest, errorResponse.get)
                    }

                }
                case _ => None
            }
        }

        None
    }

    def _validateMissingOrBlank(request: Request, header: String): Option[EPSErrorResponse] =
    {
        val headerValues: util.List[String] = request.getParams(header.toString);
        if (CollectionUtils.isEmpty(headerValues))
        {
            return Some(EPSErrorResponseBuilder.createForMissingInput(header.toString).get)

        }

        val countNonBlankValues: Int = headerValues.count(StringUtils.isNotBlank)
        if (countNonBlankValues == 0)
        {
            return Some(EPSErrorResponseBuilder.createForMissingInput(header.toString).get);
        }

        None
    }

    def _validateOccupancy(request: Request): Option[EPSErrorResponse] =
    {

        val countNonBlankOccupancy: Int = request.getParams(AvailabilityRequestHeaders.OCCUPANCY_KEY.toString).count(StringUtils.isNotBlank)
        if (countNonBlankOccupancy == 0)
        {
            return Some(EPSErrorResponseBuilder.createForMissingInput(AvailabilityRequestHeaders.OCCUPANCY_KEY.toString).get);
        }

        for (occupancy: String <- request.getParams(AvailabilityRequestHeaders.OCCUPANCY_KEY.toString))
        {
            if (StringUtils.isNotBlank(occupancy))
            {
                val split = occupancy.split("-")
                val adultsCount = split(0).toInt

                if (adultsCount > 8)
                {
                    val responseError: ResponseError = new ResponseError(
                        "number_of_occupancies.invalid_above_maximum",
                        "Number of occupancies must be less than 9.",
                        new ResponseErrorFields("occupancy", adultsCount.toString))

                    return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
                }

                if (adultsCount == 0)
                {
                    val responseError: ResponseError = new ResponseError(
                        "number_of_occupancies.invalid_below_minimum",
                        "Number of adults must be greater than 0.",
                        new ResponseErrorFields("occupancy", adultsCount.toString))

                    return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
                }

                if (split.length == 2)
                {
                    val invalidChildrenAge: Option[Int] = split(1).split(",").map(_.toInt).find(_ >= 18)
                    if (invalidChildrenAge.isDefined)
                    {
                        val responseError: ResponseError = new ResponseError(
                            "child_age.invalid_outside_accepted_range",
                            "Child age must be between 0 and 17.",
                            new ResponseErrorFields("occupancy", invalidChildrenAge.toString))

                        return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
                    }
                }
            }
        }

        None
    }

    def _validateStayInfo(request: Request): Option[EPSErrorResponse] =
    {
        val checkin = request.getParam("checkin")

        val differenceInDays: Long = Duration.between(
            LocalDate.now().atStartOfDay(),
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkin)).atStartOfDay()
        ).toDays

        if (differenceInDays < 0)
        {
            val responseError: ResponseError = new ResponseError(
                "checkin.invalid_date_in_the_past",
                "Checkin cannot be in the past.",
                new ResponseErrorFields("checkin", request.getParam("checkin")))

            return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
        }

        if (differenceInDays > 500)
        {
            val responseError: ResponseError = new ResponseError(
                "checkin.invalid_date_too_far_out",
                "Checkin too far in the future.",
                new ResponseErrorFields("checkin", request.getParam("checkin")))

            return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
        }

        val checkout = request.getParam("checkout");

        val differenceBetweenStayDates: Long = Duration.between(
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkin)).atStartOfDay(),
            LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkout)).atStartOfDay()
        ).toDays

        if (differenceBetweenStayDates < 0)
        {
            val responseError: ResponseError = new ResponseError("checkout.invalid_checkout_before_checkin", "Checkout must be after checkin.")
            responseError.fields = Array(
                new ResponseErrorFields("checkin"),
                new ResponseErrorFields("checkout")
            )

            return Some(new EPSErrorResponse(EPSResponseErrorType.INVALID_INPUT, responseError))
        }

        None
    }
}
