package akka.greeting;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

    public static void main(String...args) {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef greeter = system.actorOf(new Props(GreetingActor.class), "greeter");
        greeter.tell(new Greeting("John Smith"), greeter);
    }

}
