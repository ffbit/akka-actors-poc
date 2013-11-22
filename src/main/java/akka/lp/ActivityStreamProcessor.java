package akka.lp;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.japi.JavaPartialFunction;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.Generator;
import akka.lp.domain.Tile;
import akka.lp.domain.Track;
import akka.lp.processors.NotifierProcessor;
import akka.lp.processors.TileCreatorProcessor;
import akka.lp.processors.TitleScrapperProcessor;
import akka.lp.processors.TrackCreatorProcessor;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

public class ActivityStreamProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private ActorRef tileCreator;
    private ActorRef trackCreator;
    private ActorRef notifier;
    private ActorRef titleScrapper;

    private ActivityStreamMessage streamMessage;
    private boolean generatorProcced;
    private boolean tileProcessed;
    private boolean tracksProcessed;

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
        tileCreator = actorOf(TileCreatorProcessor.class, "tileCreator");
        trackCreator = actorOf(TrackCreatorProcessor.class, "trackCreator");
        notifier = actorOf(NotifierProcessor.class, "notifier");
        titleScrapper = actorOf(TitleScrapperProcessor.class, "titleScrapper");

        super.preStart();
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("Got a message: {}", msg);

        if (msg instanceof ActivityStreamMessage) {
            log.info("Handling an Activity Stream message: {}", msg);

            streamMessage = (ActivityStreamMessage) msg;
            titleScrapper.tell(streamMessage.getGenerator(), self());
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
            if (msg instanceof Generator) {
                Generator generator = (Generator) msg;

                tileCreator.tell(streamMessage.withGenerator(generator), self());
            } else if (msg instanceof Tile) {
                log.info("Got a created tile: {}", msg);
                trackCreator.tell(msg, self());
            } else if (isCollectionOf(msg, Track.class)) {
                log.info("Got created tracks: {}", msg);
                notifier.tell(streamMessage.getTargets(), self());

                stop();
            } else {
                log.info("Could not handle the message: {}", msg);
                unhandled(msg);
            }

//            log.info("Unbecome");
//            context().unbecome();

            return null;
        }
    };

    private boolean isCollectionOf(Object msg, Class<?> clazz) {
        if (!(msg instanceof Collection && msg instanceof ParameterizedType)) {
            return false;
        }


        ParameterizedType pt = (ParameterizedType) msg;
        Type[] types = pt.getActualTypeArguments();

        if (types.length == 0) {
            return false;
        }

        if (clazz.isAssignableFrom(msg.getClass())) {
            return true;
        }

        return false;
    }

}
