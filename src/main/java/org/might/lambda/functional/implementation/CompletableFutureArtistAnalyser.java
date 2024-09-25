package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Artist;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Date: 1/15/2024
 * Time: 9:50 AM
 */
public class CompletableFutureArtistAnalyser implements ArtistAnalyzer {

    private final Function<String, Artist> artistLookupService;

    public CompletableFutureArtistAnalyser(Function<String, Artist> artistLookupService) {
        this.artistLookupService = artistLookupService;
    }

    @Override
    public void isLargerGroup(String artistName, String otherArtistName, Consumer<Boolean> handler) {
        CompletableFuture<Long> otherArtistMemberCount = CompletableFuture.supplyAsync(() -> getNumberOfMembers(otherArtistName));
        CompletableFuture<Long> artistMemberCount = CompletableFuture.completedFuture(getNumberOfMembers(artistName));
        artistMemberCount.thenCombine(otherArtistMemberCount, (count, otherCount) -> count > otherCount)
                .thenAccept(handler);
    }

    private long getNumberOfMembers(String artistName) {
        return artistLookupService.apply(artistName)
                .getMembers()
                .count();
    }
}