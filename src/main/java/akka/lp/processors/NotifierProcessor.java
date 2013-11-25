package akka.lp.processors;

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
        log.info("Got participants to be notified: {}", msg);

        if (!Utils.isCollectionOf(msg, Participant.class)) {
            unhandled(msg);

            return;
        }

        Collection<Participant> participants = (Collection<Participant>) msg;

        for (Participant p : participants) {
            log.info("About to notify a participant: {}", p.getId());
            Utils.sleep();
            log.info("Notified the participant: {}", p.getId());
        }
    }

}
