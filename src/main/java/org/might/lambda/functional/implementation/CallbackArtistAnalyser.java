package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Artist;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Date: 1/15/2024
 * Time: 9:35 AM
 */
public class CallbackArtistAnalyser implements ArtistAnalyzer {
    private final Function<String, Artist> artistLookupService;

    public CallbackArtistAnalyser(Function<String, Artist> artistLookupService) {
        this.artistLookupService = artistLookupService;
    }

    @Override
    public void isLargerGroup(String artistName, String otherArtistName, Consumer<Boolean> handler) {
        boolean isLarger = getNumberOfMembers(artistName) > getNumberOfMembers(otherArtistName);
        handler.accept(isLarger);
    }

    private long getNumberOfMembers(String artistName) {
        return artistLookupService.apply(artistName)
                .getMembers()
                .count();
    }

}