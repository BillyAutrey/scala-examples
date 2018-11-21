package com.example

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Await

//Important - Needs to be an object (singleton), and needs to extend "App"
object Main extends App{

  //No main required, everything in here will execute
  println("I'm running my main application!")

  //Typically, you wouuld use this class to do config work or initialize your actor systems
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = materializer.system.dispatcher

  //And then instantiate your actual classes
  val runner = new StreamRunner()
  val result = runner.runSimpleStream()

  //Shut down once the stream finishes
  result.onComplete {
    case _ =>
      //shut down the actor system
      system.terminate()
      //shut down the JVM
      System.exit(0)
  }

}
