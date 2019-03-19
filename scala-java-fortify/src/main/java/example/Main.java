package example;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.time.Duration;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    class Tick{}

    public static void main(String[] args){

        final ActorSystem system = ActorSystem.create("SumStreamer");
        final Materializer materializer = ActorMaterializer.create(system);



        Source.tick(Duration.ZERO,Duration.ofSeconds(2),Tick.class)
                .runWith(Sink.foreach(tick -> SumStreamer.printSummation((int)(Math.random() * 1000),materializer)),materializer);
    }
}
