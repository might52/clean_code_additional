package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Album;
import org.might.lambda.functional.examples.chapter1.Track;

import java.util.function.ToLongFunction;

import static org.might.lambda.functional.examples.chapter1.SampleData.albums;

/**
 * Date: 1/12/2024
 * Time: 3:03 PM
 */
public class Order {

    public long countFeature(ToLongFunction<Album> function) {
        return albums
                .mapToLong(function)
                .peek(value -> System.out.println("current value to logging at count feature function: " + value))
                .sum();
    }

    public long countTracks() {
        return countFeature(albums -> albums.getTracks().count());
    }

    public long countRunningTime() {
        return countFeature(albums -> albums.getTracks()
                .mapToLong(Track::getLength)
                .sum());
    }

    public long countMusicians() {
        return countFeature(albums -> albums.getMusicians().count());
    }

}
