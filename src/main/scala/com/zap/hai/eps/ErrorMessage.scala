package com.zap.hai.eps

import com.fasterxml.jackson.annotation.JsonProperty

case class ErrorField(name:String, `type`:String, value:String)
case class ErrorMessage(@JsonProperty("type") `type`:String, message:String, fields: List[ErrorField], errors:List[ErrorMessage])


