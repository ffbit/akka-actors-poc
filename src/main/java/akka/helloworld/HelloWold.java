package akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloWold extends UntypedActor {

    @Override
    public void preStart() throws Exception {
        final ActorRef greeter =
                getContext().actorOf(Props.create(Greater.class), "greeter");
        greeter.tell(Greater.Message.GREET, getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Greater.Message.DONE) {
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }

}
