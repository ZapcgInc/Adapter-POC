package com.zap.hai.eps

case class ErrorField(name:String, `type`:String, value:String)
case class ErrorMessage(`type`:String, message:String, fields: List[ErrorField], errors:List[ErrorMessage])


