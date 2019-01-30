import auth.AuthHandler

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, RichHttp, Request}
import com.twitter.finagle.Service
import java.net.InetSocketAddress
import com.twitter.finagle.http.Response
import error.ExceptionHandler
import response.shopping.ShoppingRequestHandler

object HttpServer
{
    val handleExceptions = new ExceptionHandler
    val authorize = new AuthHandler
    val respond = new ShoppingRequestHandler
    val service: Service[Request, Response] = handleExceptions.andThen(authorize).andThen(respond);
    val address = new InetSocketAddress(10000)

    def start() = ServerBuilder()
      .codec(new RichHttp[Request](Http()))
      .name("HttpServer")
      .bindTo(address)
      .build(service)

    def main(args: Array[String])
    {
        println("Start HTTP server on port 10000")
        val server = start()
    }
}