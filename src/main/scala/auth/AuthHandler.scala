package auth

import com.twitter.finagle.SimpleFilter
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.Service
import com.twitter.util.Future

class AuthHandler extends SimpleFilter[Request, Response]
{
    override def apply(request: Request, service: Service[Request, Response]): Future[Response] =
    {
        service.apply(request)
    }
}
