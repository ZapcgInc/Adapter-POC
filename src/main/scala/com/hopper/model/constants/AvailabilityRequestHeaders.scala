package com.hopper.model.constants

object AvailabilityRequestHeaders extends Enumeration
{
    val CHECKIN_PARAM_KEY = Value("checkin")
    val CHECKOUT_PARAM_KEY = Value("checkout")
    val CURRENCY_CODE_KEY = Value("currency")
    val LANGUAGE_CODE_KEY = Value("language")
    val COUNTRY_CODE_KEY = Value("country_code")
    val OCCUPANCY_KEY = Value("occupancy")
    val PROPERTY_ID = Value("property_id")
    val SALES_CHANNEL = Value("sales_channel")
    val SALES_ENVIRONMENT = Value("sales_environment")
    val SORT_TYPE = Value("sort_type")
    val RATE_OPTION = Value("rate_option")
//    val FILTER = Value("filter")

    def withNameOpt(s: String): Option[Value] = values.find(_.toString == s)
}
