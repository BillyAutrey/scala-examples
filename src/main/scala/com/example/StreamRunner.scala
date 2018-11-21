package com.example

import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}

class StreamRunner()(implicit val materializer: Materializer) {

  def runSimpleStream() = {
    Source.single("I'm a stream and I'm running!").runWith(Sink.foreach(println))
  }
}
