package akka.lp.domain;

import javax.persistence.Column;

import com.google.common.base.Objects;
import com.netflix.astyanax.annotations.Component;

public class Track {
    /**
     * compound key makes tracks with specific owner id and type unique
     */
    @Component(ordinal = 0)
    private String ownerId;

    @Component(ordinal = 0)
    private String name;

    @Column
    private TrackType type;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrackType getType() {
        return type;
    }

    public void setType(TrackType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ownerId", ownerId)
                .add("type", type)
                .add("name", name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Track)) {
            return false;
        }

        Track that = (Track) o;
        return Objects.equal(ownerId, that.ownerId) &&
                Objects.equal(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ownerId, type);
    }
}
