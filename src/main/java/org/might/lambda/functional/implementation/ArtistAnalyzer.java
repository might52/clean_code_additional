package org.might.lambda.functional.implementation;

import java.util.function.Consumer;

public interface ArtistAnalyzer {

    public void isLargerGroup(String artistName,
                              String otherArtistName,
                              Consumer<Boolean> handler);
    
}