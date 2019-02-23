package com.hopper.validators

import com.hopper.model.constants.AvailabilityRequestHeaders
import com.hopper.model.error.{EPSErrorResponse, EPSErrorResponseBuilder}
import com.twitter.finagle.http.Request

object LanguageCodeValidator
{
    private val VALID_LANGUAGE_CODES: List[String] = List("ar-SA", "cs-CZ", "da-DK", "de-DE", "el-GR", "en-US", "es-ES",
        "es-MX", "fi-FI", "fr-CA", "fr-FR", "hr-HR", "hu-HU", "id-ID", "is-IS", "it-IT", "ja-JP", "ko-KR",
        "lt-LT", "ms-MY", "nb-NO", "nl-NL", "pl-PL", "pt-BR", "pt-PT", "ru-RU", "sk-SK", "sv-SE", "th-TH",
        "tr-TR", "uk-UA", "vi-VN", "zh-CN", "zh-TW")

    def validate(request: Request): Option[EPSErrorResponse] =
    {
        val languageCode: String = request.getParam(AvailabilityRequestHeaders.LANGUAGE_CODE_KEY.toString)

        if (!VALID_LANGUAGE_CODES.contains(languageCode))
        {
            return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestHeaders.LANGUAGE_CODE_KEY.toString).get)
        }

        None
    }
}
