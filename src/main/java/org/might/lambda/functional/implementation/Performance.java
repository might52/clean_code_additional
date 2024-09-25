package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Artist;

import java.util.stream.Stream;

/**
 * Date: 1/9/2024
 * Time: 11:09 AM
 */
public interface Performance {
    public String getName();

    public Stream<Artist> getMusicians();

    default Stream<Artist> getAllMusicians() {
        return getMusicians().flatMap(artist -> Stream.concat(Stream.of(artist), artist.getMembers()));
    }
}