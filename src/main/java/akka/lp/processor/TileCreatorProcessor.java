package akka.lp.processor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.Utils;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.Tile;
import akka.lp.repository.CassandraRepository;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TileCreatorProcessor extends UntypedActor {
    private final static AtomicInteger counter = new AtomicInteger();

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private ActorRef repository;

    @Override
    public void preStart() throws Exception {
        // TODO: to be injected
        repository = context().system().actorOf(
                Props.create(CassandraRepository.class), "cassandraRepository");

        super.preStart();
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("{}", msg);

        if (!(msg instanceof ActivityStreamMessage)) {
            log.info("Could not handle the message: {}", msg);

            unhandled(msg);
        }

        log.info("Got AS message to create a tile: {}", msg);

        Tile tile = createTile((ActivityStreamMessage) msg);
        tile = persistTile(tile);

        getSender().tell(tile, getSender());
    }

    private Tile createTile(ActivityStreamMessage msg) {
        log.info("About to create a tile from AS message: {}", msg);

        Utils.sleep();
        Tile tile = new Tile();

        log.info("A new tile has been created: {}", tile);

        return tile;
    }

    private Tile persistTile(final Tile tile) throws Exception {
        log.info("About to persist the created tile: {}", tile);

        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(repository, tile, timeout);
        Tile persisted = (Tile) Await.result(future, timeout.duration());

        log.info("The tile has been persisted: {}", persisted);

        return persisted;
    }

}
