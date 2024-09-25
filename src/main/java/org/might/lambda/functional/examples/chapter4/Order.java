package org.might.lambda.functional.examples.chapter4;

import org.might.lambda.functional.examples.chapter1.Album;

import java.util.List;

public abstract class Order {

    protected final List<Album> albums;

    public Order(List<Album> albums) {
        this.albums = albums;
    }

    public abstract long countRunningTime();

    public abstract long countMusicians();

    public abstract long countTracks();

}
