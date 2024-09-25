
import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.ArtistAnalyzer;
import org.might.lambda.functional.implementation.CallbackArtistAnalyser;
import org.might.lambda.functional.implementation.CompletableFutureArtistAnalyser;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/15/2024
 * Time: 9:59 AM
 */

public class ArtistAnalyzerTest {

    @Test
    public void largerGroupsAreLargerCallback() {
        assertLargerGroup(true, "The Beatles", "John Coltrane", new CallbackArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }
    @Test
    public void largerGroupsAreLargerCompletableFuture() {
        assertLargerGroup(true, "The Beatles", "John Coltrane", new CompletableFutureArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }


    @Test
    public void smallerGroupsArentLargerCallback() {
        assertLargerGroup(false, "John Coltrane", "The Beatles", new CallbackArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }

    @Test
    public void smallerGroupsArentLargerCompletableFuture() {
        assertLargerGroup(false, "John Coltrane", "The Beatles", new CompletableFutureArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }


    public void assertLargerGroup(boolean expected, String artistName, String otherArtistName, ArtistAnalyzer analyzer) {
        AtomicBoolean isLarger = new AtomicBoolean(!expected);
        analyzer.isLargerGroup(artistName, otherArtistName, isLarger::set);
        assertEquals(expected, isLarger.get());
    }


}