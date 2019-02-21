package com.hopper.model.constants

object GlobalConstants
{
    val HTTP_POST = "POST"
    val CONTENT_TYPE = "Content-Type"
    val CONTENT_FORMAT = "text/xml; charset=utf-8"

    // Availability Request Constants
    val CHECKIN_PARAM_KEY = "checkin"
    val CHECKOUT_PARAM_KEY = "checkout"
    val CURRENCY_CODE_KEY = "currency"
    val LANGUAGE_CODE_KEY = "language"
    val COUNTRY_CODE_KEY = "country_code"
    val OCCUPANCY_KEY = "occupancy"
    val PROPERTY_ID = "property_id"


    val AUTH_KEY = "Authorization"
    val SANDBOX_API_HOST = "http://sandbox-affiliateapi.agoda.com"
    val AVAIL_END_POINT = "xmlpartner/xmlservice/search_lrv3"
    val ACCEPT_ENCODING = "Accept-Encoding"
    val ACCEPT_FORMAT = "application/gzip"
}
