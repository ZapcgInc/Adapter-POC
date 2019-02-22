package zap.framework.exceptions

class ZapException(message:String, throwable: Throwable) extends RuntimeException(message,throwable) {

  def this(message:String) = this(message,null)

}
