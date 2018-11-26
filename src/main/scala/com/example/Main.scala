package com.example

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, ExecutionContext, Future}

//Important - Needs to be an object (singleton), and needs to extend "App"
object Main extends App{

  //No main required, everything in here will execute
  println("I'm running my main application!")

  //Typically, you wouuld use this class to do config work or initialize your actor systems
  implicit val system: ActorSystem = ActorSystem("QuickStart")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = materializer.system.dispatcher

  //And then instantiate your actual classes
  val runner = new StreamRunner()
  val result: Future[Done] = runner.runSimpleStream()

  //Shut down once the stream finishes
  result.onComplete {
    _ =>
      //shut down the actor system
      system.terminate()
      //shut down the JVM
      System.exit(0)
  }

}
