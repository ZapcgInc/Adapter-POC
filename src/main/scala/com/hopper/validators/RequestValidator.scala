package com.hopper.validators

import com.twitter.finagle.http.Request
import com.hopper.model.error.EPSErrorResponse
import org.jboss.netty.handler.codec.http.HttpResponseStatus

trait RequestValidator
{
    def validate(request:Request) : Option[(HttpResponseStatus, EPSErrorResponse)]
}
