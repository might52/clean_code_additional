import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.BlockingArtistAnalyzer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockingArtistAnalyzerTest {

    BlockingArtistAnalyzer analyser = new BlockingArtistAnalyzer(new FakeLookupService()::lookupArtistName);

    @Test
    public void largerGroupsAreLarger() {
        assertTrue(analyser.isLargerGroup("The Beatles", "John Coltrane"));
    }

    @Test
    public void smallerGroupsArentLarger() {
        assertFalse(analyser.isLargerGroup("John Coltrane", "The Beatles"));
    }

}
