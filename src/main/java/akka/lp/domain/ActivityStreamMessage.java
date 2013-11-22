package akka.lp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Represents Activity Stream Message DTO that we receive from external world.
 */
public class ActivityStreamMessage implements Serializable {
    private static final long serialVersionUID = 3606159914038843207L;

    private final Generator generator;

    private final Collection<Participant> targets;

    public ActivityStreamMessage(Generator generator,
                                 Collection<Participant> targets) {
        this.generator = generator;
        this.targets = Collections.unmodifiableCollection(new ArrayList(targets));
    }

    public Generator getGenerator() {
        return generator;
    }

    public Collection<Participant> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return "ActivityStreamMessage{" +
                "generator=" + generator +
                ", targets=" + targets +
                '}';
    }

    public ActivityStreamMessage withGenerator(Generator generator) {
        return new ActivityStreamMessage(generator, targets);
    }

}
