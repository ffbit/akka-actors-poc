package akka.lp.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.Tile;
import akka.lp.domain.Track;

public class TileCreatorProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("{}", msg);

        if (msg instanceof ActivityStreamMessage) {
            Tile tile = new Tile();

            TimeUnit.MILLISECONDS.sleep(10);

            List<Track> tracks = new ArrayList();
            getSender().tell(tracks, self());
        } else {
            log.info("Could not handle the message: {}", msg);

            unhandled(msg);
        }
    }

}
