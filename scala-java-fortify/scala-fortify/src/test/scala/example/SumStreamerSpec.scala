package example

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit._
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

class SumStreamerSpec extends TestKit(ActorSystem("SampleSpec")) with FlatSpecLike with ScalaFutures with Matchers {

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  "The SumStreamer object" should "sum to 100" in {

    SumStreamer.summation(100,materializer).futureValue shouldEqual 5050

  }

}
