package com.zap.hai.controllers

import zap.framework.httpclient.ZapHttpRequest

trait RequestXfmr[T] {

  def transform(request : ZapHttpRequest) : T

}
