package akka.lp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ActivityStreamMessage implements Serializable {
    private static final long serialVersionUID = 3606159914038843207L;

    private final ActivityStreamGenerator generator;

    private final Collection<ActivityStreamTarget> targets;

    public ActivityStreamMessage(ActivityStreamGenerator generator,
                                 Collection<ActivityStreamTarget> targets) {
        this.generator = generator;
        this.targets = Collections.unmodifiableCollection(new ArrayList(targets));
    }

    public ActivityStreamGenerator getGenerator() {
        return generator;
    }

    public Collection<ActivityStreamTarget> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return "ActivityStreamMessage{" +
                "generator=" + generator +
                ", targets=" + targets +
                '}';
    }

}
