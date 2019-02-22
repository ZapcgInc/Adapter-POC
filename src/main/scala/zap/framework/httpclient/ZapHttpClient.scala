package zap.framework.httpclient

trait ZapHttpClient {

  def execute(request : ZapHttpRequest) : ZapHttpResponse

}
