package akka.lp.processor;

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

import java.util.concurrent.TimeUnit;

public class TileCreatorProcessor extends UntypedActor {
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

        if (msg instanceof ActivityStreamMessage) {
            log.info("\nGot AS message to create a tile: {}", msg);

            Tile tile = createTile((ActivityStreamMessage) msg);
            tile = persistTile(tile);

            getSender().tell(tile, getSender());
        } else {
            log.info("\nCould not handle the message: {}", msg);

            unhandled(msg);
        }


    }

    private Tile createTile(ActivityStreamMessage msg) {
        log.info("\nAbout to create a tile from AS message: {}", msg);

        Utils.sleep();
        Tile tile = new Tile();

        log.info("\nA new tile has been created: {}", tile);

        return tile;
    }

    private Tile persistTile(final Tile tile) throws Exception {
        log.info("\nAbout to persist the created tile: {}", tile);

        // TODO: this is a synchronous block
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(repository, tile, timeout);
        // TODO: synchronous magic goes here
        Tile persisted = (Tile) Await.result(future, timeout.duration());
        // TODO: synchronous block end

        log.info("\nThe tile has been persisted: {}", persisted);

        return persisted;
    }

}
