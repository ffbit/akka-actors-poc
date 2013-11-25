package akka.lp;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.processor.NotifierProcessor;
import akka.lp.processor.TileCreatorProcessor;
import akka.lp.processor.TrackCreatorProcessor;
import scala.concurrent.duration.Duration;

public class RunAllActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private final ActorRef tileCreator;
    private final ActorRef trackCreator;
    private final ActorRef notifier;

    public RunAllActor() {
        tileCreator = actorOf(TileCreatorProcessor.class);
        trackCreator = actorOf(TrackCreatorProcessor.class);
        notifier = actorOf(NotifierProcessor.class);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10, Duration.apply("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>() {
            @Override
            public SupervisorStrategy.Directive apply(Throwable t) {
                if (t instanceof ArithmeticException) {
                    return resume();
                } else if (t instanceof NullPointerException) {
                    return restart();
                } else {
                    return escalate();
                }
            }
        });
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof ActivityStreamMessage) {
            tileCreator.tell(msg, self());
            tileCreator.tell(msg, self());
            tileCreator.tell(msg, self());
        } else {
            unhandled(msg);
        }
    }

    private ActorRef actorOf(Class<?> actorClass) {
        return context().system().actorOf(Props.create(actorClass));
    }

}
