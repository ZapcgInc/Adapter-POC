package com.hopper.auth

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future
import com.hopper.AvailabilityProcessor

class AvailabilityRequestHandler extends Service[Request, Response]
{
    override def apply(request: Request): Future[Response] =
    {

        Future.value(AvailabilityProcessor.process(request))
    }
}