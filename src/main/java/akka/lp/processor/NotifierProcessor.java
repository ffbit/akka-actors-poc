package akka.lp.processor;

import java.util.Collection;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.Utils;
import akka.lp.domain.Participant;

public class NotifierProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        if (Utils.isCollectionOf(msg, Participant.class)) {
            log.info("Got participants to be notified: {}", msg);

            notify((Collection<Participant>) msg);
            sender().tell("done", self());
        } else {
            unhandled(msg);
        }
    }

    private void notify(Collection<Participant> participants) {
        for (Participant p : participants) {
            log.info("About to notify a participant: {}", p.getId());
            Utils.sleep();
            log.info("Notified the participant: {}", p.getId());
        }
    }

}
