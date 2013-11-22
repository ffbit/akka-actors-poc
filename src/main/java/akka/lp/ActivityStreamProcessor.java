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
import akka.japi.JavaPartialFunction;
import akka.lp.domain.ActivityStreamGenerator;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.processors.NotifierActor;
import akka.lp.processors.TileCreatorActor;
import akka.lp.processors.TitleScrapperActor;
import akka.lp.processors.TrackCreatorActor;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

public class ActivityStreamProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private ActorRef tileCreator;
    private ActorRef trackCreator;
    private ActorRef notifier;
    private ActorRef titleScrapper;

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
    public void preStart() throws Exception {
        tileCreator = actorOf(TileCreatorActor.class, "tileCreator");
        trackCreator = actorOf(TrackCreatorActor.class, "trackCreator");
        notifier = actorOf(NotifierActor.class, "notifier");
        titleScrapper = actorOf(TitleScrapperActor.class, "titleScrapper");

        super.preStart();
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("Got a message: {}", msg);

        if (msg instanceof ActivityStreamMessage) {
            log.info("Handling the message: {}", msg);

            ActivityStreamMessage message = (ActivityStreamMessage) msg;

            titleScrapper.tell(message.getGenerator(), self());
            tileCreator.tell(message, self());
            trackCreator.tell(message, self());
            notifier.tell(message.getTargets(), self());

            context().become(expectReply, false);
        } else {
            log.info("Could not handle the message: {}", msg);
            unhandled(msg);
        }
    }

    private ActorRef actorOf(Class<?> actorClass, String name) {
        return context().system().actorOf(Props.create(actorClass), name);
    }

    private PartialFunction expectReply = new JavaPartialFunction() {
        private final LoggingAdapter log = Logging.getLogger(context().system(), this);

        @Override
        public Object apply(Object msg, boolean isCheck) throws Exception {
            if (msg instanceof ActivityStreamGenerator) {

            }


            log.info("Unbecome");

            context().unbecome();

            return null;
        }

    };

}
