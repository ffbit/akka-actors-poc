package akka.lp;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.lp.domain.EntityType;
import akka.lp.domain.Generator;
import akka.lp.domain.ActivityStreamMessage;
import akka.lp.domain.Participant;

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

    private static Generator getGenerator() {
        return new Generator(UUID.randomUUID(), "http://apple.com", "Apple Inc");
    }

    private static Collection<Participant> getTargets() {
        return Arrays.asList(
                new Participant(UUID.randomUUID().toString(), EntityType.USER),
                new Participant(UUID.randomUUID().toString(), EntityType.USER)
        );
    }

}
