package com.hopper.validators
import com.hopper.commons.eps.model.error.EPSErrorResponse
import com.twitter.finagle.http.Request
import com.hopper.commons.eps.model.error.EPSErrorResponseBuilder
import com.hopper.model.constants.AvailabilityRequestParams

object CountryCodeValidator
{
  private val VALID_COUNTRY_CODE: List[String] = List("AG", "AL", "AO", "AS", "AU", "AZ", "BB",
    "BE", "BG", "BI", "BL", "BN", "BQ", "BS", "BV", "BY", "CA", "CD", "CG", "CI", "CL", "CN", "CR", "CW", "CY",
    "DE", "DK", "DO", "EC", "EG", "ER", "ET", "FJ", "FM", "FR", "GB", "GE", "GH", "GL", "GN", "GQ", "GS", "GU", "GW",
      "GY","HN","HT","ID","IL","IO","IS","JM","JP","KG","KI","KN","KW","KZ","LB","LI","LR","LT","LV","MA","MD","MF","MH",
    "MK","ML","MM","MN","MO","MP","MQ","MR","MS","MT","MU","MV","MW","MX","MY","MZ","NA","NC","NE","NF","NG","NI","NL",
    "NO","NP","NR","NU","NZ","OM","PA","PE","PF","PG","PH","PK","PL","PM","PN","PR","PS","PT","PW",
    "PY","QA","RE","RO","RS","RU","RW","SA","SB","SC","SE","SG","SH","SI","SK","SL","SM","SN","SO","SR","SS",
    "ST","SV","SX","SZ","TC","TD","TF","TG","TH","TJ","TK","TL","TM","TN","TO","TR","TT","TV","TW","TZ","UA","US","UY",
    "UZ","VA","VC","VG","VN","VU","WF","WS","XK","YE","YT","ZA","ZM","ZW")


  def validate(request: Request): Option[EPSErrorResponse] =
  {
    val countryCode:String = request.getParam(AvailabilityRequestParams.COUNTRY_CODE_KEY.toString)
    //println("Country Code"+countryCode)
    if (!VALID_COUNTRY_CODE.contains(countryCode))
    {
      return Some(EPSErrorResponseBuilder.createForUnsupportedInput(AvailabilityRequestParams.COUNTRY_CODE_KEY.toString).get)
    }

    None
  }
}
