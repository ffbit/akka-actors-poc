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
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.Generator;
import akka.lp.domain.Tile;
import akka.lp.processor.NotifierProcessor;
import akka.lp.processor.TileCreatorProcessor;
import akka.lp.processor.TileRouterProcessor;
import akka.lp.processor.TitleScrapperProcessor;
import akka.lp.processor.exception.PersistentException;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

public class ActivityStreamProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private ActorRef tileCreator;
    private ActorRef tileRouter;
    private ActorRef notifier;
    private ActorRef titleScrapper;

    private ActivityStreamMessage streamMessage;
    private Tile tile;

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                10, Duration.apply("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>() {
            @Override
            public SupervisorStrategy.Directive apply(Throwable t) {
                if (t instanceof PersistentException) {
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
        tileRouter = actorOf(TileRouterProcessor.class, "tileRouter");
        notifier = actorOf(NotifierProcessor.class, "notifier");
        titleScrapper = actorOf(TitleScrapperProcessor.class, "titleScrapper");

        super.preStart();
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("Got a message: {}", msg);

        if (msg instanceof ActivityStreamMessage) {
            log.info("\nHandling an Activity Stream message: {}", msg);

            streamMessage = (ActivityStreamMessage) msg;
            titleScrapper.tell(streamMessage.getGenerator(), self());

            become(expectGenerator());
        } else {
            unhandled(msg);
        }
    }

    private ActorRef actorOf(Class<?> actorClass, String name) {
        return context().system().actorOf(Props.create(actorClass), name);
    }

    private PartialFunction expectGenerator() {
        return new JavaPartialFunction() {
            private final LoggingAdapter log = Logging.getLogger(context().system(), this);

            @Override
            public Object apply(Object msg, boolean isCheck) throws Exception {
                if (msg instanceof Generator) {
                    Generator generator = (Generator) msg;
                    streamMessage = streamMessage.withGenerator(generator);

                    tileCreator.tell(streamMessage, self());

                    become(expectPersistedTile());
                } else {
                    unhandled(msg);
                }

                // TODO: Find what should be returned
                return null;
            }

        };
    }

    private PartialFunction expectPersistedTile() {
        return new JavaPartialFunction() {
            private final LoggingAdapter log = Logging.getLogger(context().system(), this);

            @Override
            public Object apply(Object msg, boolean isCheck) throws Exception {
                if (msg instanceof Tile) {
                    log.info("\nGot a created tile: {}", msg);
                    tile = (Tile) msg;

                    tileRouter.tell(tile, self());

                    become(expectRoutedTile());
                } else {
                    unhandled(msg);
                }

                // TODO: Find what should be returned
                return null;
            }
        };
    }

    private PartialFunction expectRoutedTile() {
        return new JavaPartialFunction() {
            private final LoggingAdapter log = Logging.getLogger(context().system(), this);

            @Override
            public Object apply(Object msg, boolean isCheck) throws Exception {
                if (msg instanceof Tile) {
                    log.info("\nGot routed tile: {}", msg);

                    notifier.tell(streamMessage.getParticipants(), self());
                    become(expectDone());
                } else {
                    unhandled(msg);
                }

                // TODO: Find what should be returned
                return null;
            }

        };
    }

    private PartialFunction expectDone() {
        return new JavaPartialFunction() {
            private final LoggingAdapter log = Logging.getLogger(context().system(), this);

            @Override
            public Object apply(Object msg, boolean isCheck) throws Exception {
                if ("done".equals(msg)) {
                    log.info("\nGot the `done` status: {}", msg);
                } else {
                    unhandled(msg);
                }

                // TODO: Find what should be returned
                return null;
            }

        };
    }

    @Override
    public void unhandled(Object message) {
        log.info("\nCould not handle the message: {}", message);
        super.unhandled(message);
    }

    private void become(PartialFunction expect) {
        context().become(expect, false);
    }

}
