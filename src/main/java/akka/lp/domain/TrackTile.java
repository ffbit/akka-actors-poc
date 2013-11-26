package akka.lp.domain;

import java.util.Date;
import java.util.UUID;

import com.google.common.base.Objects;
import com.netflix.astyanax.annotations.Component;
import com.netflix.astyanax.mapping.Id;

public class TrackTile {
    @Id
    private String trackId;

    @Component(ordinal = 0)
    private Date timestamp = new Date();

    @Component(ordinal = 1)
    private UUID tileId;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public UUID getTileId() {
        return tileId;
    }

    public void setTileId(UUID tileId) {
        this.tileId = tileId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("trackId", trackId)
                .add("tileId", tileId)
                .add("timestamp", timestamp)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackTile)) {
            return false;
        }

        TrackTile that = (TrackTile) o;
        return Objects.equal(trackId, that.trackId) &&
                Objects.equal(tileId, that.tileId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(trackId, tileId);
    }
}
