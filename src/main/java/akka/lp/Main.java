package akka.lp;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.lp.domain.ActivityStreamGenerator;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.ActivityStreamTarget;

public class Main {

    public static void main(String... args) {
        ActorRef actor = getActorRef(ActivityStreamProcessor.class, "activityStreamProcessor");
        actor.tell(getMessage(), ActorRef.noSender());
    }

    private static ActorRef getActorRef(Class<?> actorClass, String name) {
        ActorSystem system = ActorSystem.create("MySystem");

        return system.actorOf(Props.create(actorClass), name);
    }

    private static ActivityStreamMessage getMessage() {
        return new ActivityStreamMessage(getGenerator(), getTargets());
    }

    private static ActivityStreamGenerator getGenerator() {
        return new ActivityStreamGenerator(UUID.randomUUID(), "http://apple.com", "Apple Inc");
    }

    private static Collection<ActivityStreamTarget> getTargets() {
        return Arrays.asList(
                new ActivityStreamTarget(UUID.randomUUID(), "user"),
                new ActivityStreamTarget(UUID.randomUUID(), "user")
        );
    }

}
