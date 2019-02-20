package com.hopper

import com.hopper.request.Routes
import com.twitter.finagle.http._
import com.twitter.server.TwitterServer
import com.twitter.util.Await

object HttpServer extends TwitterServer {


  def main() {
    println("APPLICATION STARTING !!!")
    HttpMuxer.addHandler("/", new Routes{}.service)
    Await.ready(adminHttpServer)
  }



}
