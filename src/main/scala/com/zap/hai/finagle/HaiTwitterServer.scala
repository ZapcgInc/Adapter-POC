package com.zap.hai.finagle

import com.twitter.finagle.http.HttpMuxer
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import com.zap.hai.HaiFactoryDefault
import com.zap.hai.finch.routes.Routes

object HaiTwitterServer extends TwitterServer  {

  def main() {

    val factory = new HaiFactoryDefault {}

    HttpMuxer.addHandler("/", factory.finagleService.routes)
    Await.ready(adminHttpServer)
  }


}
