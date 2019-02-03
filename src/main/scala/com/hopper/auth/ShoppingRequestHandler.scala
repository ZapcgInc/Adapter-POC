package com.hopper.auth

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import com.hopper.ShoppingProcessor

class ShoppingRequestHandler extends Service[Request, Response]
{
    override def apply(request: Request): Future[Response] =
    {
        Future.value(ShoppingProcessor.process(request))
    }
}
