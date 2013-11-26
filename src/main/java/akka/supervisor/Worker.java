package akka.supervisor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.concurrent.atomic.AtomicInteger;

public class Worker extends UntypedActor {
    private final static AtomicInteger counter = new AtomicInteger();
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Worker received a message: {}", message);

        sender().tell(doSomeWork(message), self());
    }

    private Object doSomeWork(Object message) {
        if (isItTimeForException()) {
            throw new WorkerException("Ta-da! From a worker " + self().path());
        }

        log.info("some work's been done");
        return message;
    }

    private boolean isItTimeForException() {
        return counter.getAndIncrement() % 2 != 0;
    }

}
