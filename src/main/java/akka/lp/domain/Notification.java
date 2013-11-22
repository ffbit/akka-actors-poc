package akka.lp.domain;

import com.google.common.base.Objects;

public class Notification {
    private final Tile tile;
    private final Type type;

    public Notification(Tile tile, Type type) {
        this.tile = tile;
        this.type = type;
    }

    public Tile getTile() {
        return tile;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tile", tile)
                .add("type", type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }

        Notification that = (Notification) o;
        return Objects.equal(tile, that.tile) &&
                Objects.equal(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tile, type);
    }

    public static enum Type {
        TILE_ADDED,
        TILE_UPDATED,
        GROWL
    }
}
