package error

import com.twitter.finagle.SimpleFilter
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.Service
import com.twitter.util.Future

/**
  * Class for handling Exceptions
  */
class ExceptionHandler extends SimpleFilter[Request, Response]
{

    override def apply(request: Request, service: Service[Request, Response]): Future[Response] =
    {
       service.apply(request)
    }
}
