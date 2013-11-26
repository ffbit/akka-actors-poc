package akka.lp.processor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.lp.domain.EntityType;
import akka.lp.domain.Generator;
import akka.lp.domain.Participant;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class NotifierProcessorTest {

    private static ActorSystem system;
    private TestActorRef<NotifierProcessor> notifierRef;
    private NotifierProcessor notifier;

    @BeforeClass
    public static void setUpClass() throws Exception {
        system = ActorSystem.create("MyUnitTestSystem");
    }

    @Before
    public void setUp() throws Exception {
        Props props = Props.create(NotifierProcessor.class);
        notifierRef = TestActorRef.create(system, props, "TestNotifier");
        notifier = notifierRef.underlyingActor();
    }

    @Test
    public void itShouldNotifyParticipants() throws Exception {
        Object message = Arrays.asList(new Participant("test-participant-id", EntityType.USER));

        Future<Object> future = Patterns.ask(notifierRef, message, 3000L);
        assertTrue(future.isCompleted());

        assertEquals("done", Await.result(future, Duration.Zero()));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

}
