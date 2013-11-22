package akka.lp.domain;

public enum TrackType {
    USER,
    ARTIST,
    GROUP,
    APPLICATION;

    public EntityType toEntityType() {
        for (EntityType et : EntityType.values()) {
            if (et.name().equals(this.name())) {
                return et;
            }
        }
        throw new IllegalStateException(String.format("Entity type for '%s' not found", this.name()));
    }
}
