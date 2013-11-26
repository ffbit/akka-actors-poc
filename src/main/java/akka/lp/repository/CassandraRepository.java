package akka.lp.repository;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.processor.exception.TilePersistentException;

public class CassandraRepository extends UntypedActor {
    private final static AtomicInteger counter = new AtomicInteger();
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("\nGot message: {}", msg);

        if (msg instanceof Serializable) {
            Serializable entity = (Serializable) msg;
            sender().tell(save(entity), self());
        } else {
            unhandled(msg);
        }
    }

    private <T extends Serializable> T save(T t) {
        if (isItTimeForException()) {
            log.error("\nAbout to throw an exception");

            throw new TilePersistentException("\nTa-da! Could not persist a tile: " + t);
        }

        log.info("\nA new {} has been persisted: {}", t.getClass(), t);

        return t;
    }

    private boolean isItTimeForException() {
        return counter.getAndIncrement() % 2 != 0;
    }

}
