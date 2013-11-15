package akka.helloworld;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Greater extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Message {
        GREET,
        DONE;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Message.GREET) {
            log.info("Hello World");
            getSender().tell(Message.DONE, getSelf());
        } else {
            unhandled(msg);
        }
    }

}
