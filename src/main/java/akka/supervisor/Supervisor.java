package akka.supervisor;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

class Supervisor extends UntypedActor {

    private SupervisorStrategy strategy = new OneForOneStrategy(
            10, Duration.apply("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) {
            if (t instanceof ArithmeticException) {
                return resume();
            } else if (t instanceof NullPointerException) {
                return restart();
            } else {
                return escalate();
            }
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    ActorRef worker = getContext().actorOf(Props.create(Worker.class));

    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            worker.forward(message, getContext());
        }
    }
}
