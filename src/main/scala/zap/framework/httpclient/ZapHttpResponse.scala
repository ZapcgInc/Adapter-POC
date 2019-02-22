package zap.framework.httpclient

case class ZapHttpResponse(statusCode: Int, statusMessage: String, body: Option[ZapHttpEntity], headers: (String, String)*) {

  def headersToMap(): Map[String, List[String]] = {
    ???
  }


}
