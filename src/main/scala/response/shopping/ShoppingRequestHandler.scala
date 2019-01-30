package response.shopping

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class ShoppingRequestHandler extends Service[Request, Response]
{

    override def apply(request: Request): Future[Response] =
    {
        import com.twitter.finagle.http.Response
        val response = Response()
        response.setContentString("Hello from Agoda Adapter\n")
        Future.value(response)
    }
}
