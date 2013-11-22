package akka.lp.processors;

import java.util.concurrent.TimeUnit;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TileRouterProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("{}", msg);

        TimeUnit.MILLISECONDS.sleep(10);

        sender().tell(msg, getSender());
    }

}
