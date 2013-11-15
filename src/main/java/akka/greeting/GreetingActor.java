package akka.greeting;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GreetingActor extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof Greeting) {
            log.info("Hello " + ((Greeting) o).getWho());
        }
    }

}
