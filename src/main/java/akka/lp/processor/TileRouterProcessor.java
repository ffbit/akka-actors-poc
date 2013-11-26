package akka.lp.processor;

import java.util.Collection;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.JavaPartialFunction;
import akka.lp.Utils;
import akka.lp.domain.Tile;
import akka.lp.domain.Track;
import scala.PartialFunction;

public class TileRouterProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private ActorRef trackCreator;
    private ActorRef parent;

    private Tile tile;

    @Override
    public void preStart() throws Exception {
        trackCreator = context().system().actorOf(
                Props.create(TrackCreatorProcessor.class), "trackCreator");
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("{}", msg);

        if (msg instanceof Tile) {
            tile = (Tile) msg;

            trackCreator.tell(tile, self());

            context().become(expectTracks(), false);

            // TODO: get rid of this work around
            parent = getSender();
        } else {
            unhandled(msg);
        }
    }

    private PartialFunction expectTracks() {
        return new JavaPartialFunction() {
            private final LoggingAdapter log = Logging.getLogger(context().system(), this);

            @Override
            public Object apply(Object msg, boolean isCheck) throws Exception {
                if (Utils.isCollectionOf(msg, Track.class)) {
                    log.info("Got created tracks: {}", msg);

                    routeTile((Collection<Track>) msg);

                    parent.tell(tile, self());
                } else {
                    unhandled(msg);
                }

                // TODO: Find what should be returned
                return null;
            }

        };
    }

    private void routeTile(Collection<Track> tracks) {
        for (Track e : tracks) {
            log.info("Tile {} has been routed to track {}", tile.getId(), e.getOwnerId());
        }
    }


}
