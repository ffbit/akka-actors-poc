package akka.lp.processor;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.lp.domain.Generator;
import akka.testkit.TestActorRef;
import akka.pattern.Patterns;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TitleScrapperProcessorTest {

    private ActorSystem system;
    private TitleScrapperProcessor tileScrapper;
    private TestActorRef<TitleScrapperProcessor> tileScrapperRef;

    @Before
    public void setUp() throws Exception {
        system = ActorSystem.create("MyUnitTestSystem");
        Props props = Props.create(TitleScrapperProcessor.class);
        tileScrapperRef = TestActorRef.create(system, props, "TestTitleScrapper");
        tileScrapper = tileScrapperRef.underlyingActor();
    }

    @Test
    public void testName() throws Exception {
        Generator message = new Generator("id", "http://apple.com");

        Future<Object> future = Patterns.ask(tileScrapperRef, message, 3000L);
        assertTrue(future.isCompleted());

        Generator expected = new Generator("id", "http://apple.com", "Scrapped title");
        assertEquals(expected, Await.result(future, Duration.Zero()));
    }

    @After
    public void tearDown() throws Exception {
        system.shutdown();
    }

}
