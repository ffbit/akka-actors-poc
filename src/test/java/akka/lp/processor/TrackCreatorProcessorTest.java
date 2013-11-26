package akka.lp.processor;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.lp.domain.Tile;
import akka.lp.domain.Track;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TrackCreatorProcessorTest {

    private static ActorSystem system;
    private TestActorRef<TrackCreatorProcessor> trackCreatorRef;

    @BeforeClass
    public static void setUpClass() throws Exception {
        system = ActorSystem.create("MyUnitTestSystem");
    }

    @Before
    public void setUp() throws Exception {
        Props props = Props.create(TrackCreatorProcessor.class);
        trackCreatorRef = TestActorRef.create(system, props, "TestTrackCreator");
    }

    @Test
    public void itShouldCreateAndReturnTracks() throws Exception {
        Object message = new Tile();

        Future<Object> future = Patterns.ask(trackCreatorRef, message, 3000L);
        assertTrue(future.isCompleted());

        Object result = Await.result(future, Duration.Zero());
        assertThat(result, is(instanceOf(Collection.class)));

        Collection<Track> tracks = (Collection<Track>) result;
        assertFalse(tracks.isEmpty());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

}
