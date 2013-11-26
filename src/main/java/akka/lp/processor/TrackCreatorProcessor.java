package akka.lp.processor;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.lp.domain.Tile;
import akka.lp.domain.Track;

public class TrackCreatorProcessor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    @Override
    public void onReceive(Object msg) throws Exception {
        log.info("Got message {}", msg);

        if (msg instanceof Tile) {
            Collection<Track> tracks = createTracks((Tile) msg);

            getSender().tell(tracks, getSelf());
        } else {
            unhandled(msg);
        }
    }

    private Collection<Track> createTracks(Tile tile) {
        log.info("About to execute Tracks business logic");

        Collection<Track> tracks = Arrays.asList(createTrack(), createTrack());

        log.info("Tracks business logic has been executed");

        return tracks;
    }

    private Track createTrack() {
        Track track = new Track();
        track.setOwnerId(UUID.randomUUID().toString());
        return track;
    }

}
