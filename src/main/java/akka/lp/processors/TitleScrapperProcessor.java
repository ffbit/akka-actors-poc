package akka.lp.processors;

import java.util.concurrent.TimeUnit;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.domain.Generator;

public class TitleScrapperProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("Got a message: {}", msg);

        if (msg instanceof Generator) {
            Generator in = (Generator) msg;

            TimeUnit.MILLISECONDS.sleep(10);
            Generator out = new Generator(in.getId(), in.getSourceUrl(), "Scrapped title");

            getSender().tell(out, self());
        } else {
            log.info("Could not handle the message: {}", msg);

            unhandled(msg);
        }
    }

}
