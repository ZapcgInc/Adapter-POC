package com.zap.hai.finch

import com.twitter.finagle.http.HttpMuxer
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import com.zap.hai.finch.routes.Routes

object HaiTwitterServer extends TwitterServer  {

  def main() {
    HttpMuxer.addHandler("/", new Routes{}.service)
    Await.ready(adminHttpServer)
  }


}
