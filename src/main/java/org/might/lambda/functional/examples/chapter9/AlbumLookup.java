package org.might.lambda.functional.examples.chapter9;

import org.might.lambda.functional.examples.chapter1.Album;


public interface AlbumLookup {
    Album lookupByName(String albumName);
}
