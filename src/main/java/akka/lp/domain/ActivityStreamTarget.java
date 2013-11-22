package akka.lp.domain;

import java.io.Serializable;
import java.util.UUID;

public class ActivityStreamTarget implements Serializable {
    private static final long serialVersionUID = -5542249166685265145L;

    private final UUID id;

    private final String type;

    public ActivityStreamTarget(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ActivityStreamTarget{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

}
