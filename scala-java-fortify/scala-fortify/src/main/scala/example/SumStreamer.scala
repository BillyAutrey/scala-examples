package example

import akka.stream._
import akka.stream.scaladsl._

import scala.concurrent.Future

object SumStreamer {

  //Calling from Java, so we need to explicitly take a materializer and pass it as an implicit
  def summation(x: Int, materializer: Materializer): Future[Int] = Source(1 to x).runWith(Sink.fold(0)(_ + _))(materializer)

  def printSummation(x: Int, materializer: Materializer): Unit = summation(x,materializer).foreach(println)(materializer.executionContext)

}
