package akka.lp.domain;

import java.util.UUID;

import com.google.common.base.Objects;

public class TrackSubscriber {
    private UUID trackId;
    private UUID subscriberId;

    public UUID getTrackId() {
        return trackId;
    }

    public void setTrackId(UUID trackId) {
        this.trackId = trackId;
    }

    public UUID getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(UUID subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("trackId", trackId)
                .add("subscriberId", subscriberId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrackSubscriber)) {
            return false;
        }

        TrackSubscriber that = (TrackSubscriber) o;
        return Objects.equal(trackId, that.trackId) &&
                Objects.equal(subscriberId, that.subscriberId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(trackId, subscriberId);
    }
}
