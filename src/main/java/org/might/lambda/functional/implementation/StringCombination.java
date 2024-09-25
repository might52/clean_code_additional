package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Artist;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Date: 1/9/2024
 * Time: 4:09 PM
 */
public class StringCombination {

    private final List<Artist> artists;

    public StringCombination(List<Artist> artists) {
        this.artists = artists;
    }


    public String getNamesConcat() {
        StringBuilder builder = new StringBuilder("[");
        for (Artist artist : artists) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            String name = artist.getName();
            builder.append(name);
        }
        builder.append("]");
        return builder.toString();
    }

    public String getNamesConcatRefactor1() {
        StringBuilder builder = new StringBuilder("[");
        artists.stream()
                .map(Artist::getName)
                .forEach(name -> {
                    if (name.length() > 1) {
                        builder.append(", ");
                    }
                    builder.append(name);
                });
        builder.append("]");
        return builder.toString();
    }

    public String getNamesConcatRefactor2() {
        StringBuilder reducer =
                artists.stream()
                        .map(Artist::getName)
                        .reduce(new StringBuilder(), (builder, name) -> {
                            if (builder.length() > 1) {
                                builder.append(", ");
                            }
                            return builder;
                        }, (left, right) -> left.append(right));
        reducer.insert(0, "[");
        reducer.append("]");
        return reducer.toString();
    }

    public String getNamesConcatRefactor3() {
        return artists.stream()
                .map(Artist::getName)
                .reduce(new StringCombiner(", ", "[", "]"),
                        StringCombiner::add,
                        StringCombiner::merge
                ).toString();
    }

    // bad approach.
    public String getNamesConcatRefactor4() {
        return artists.stream()
                .map(Artist::getName)
                .collect(Collectors.reducing(
                        new StringCombiner(", ", "[", "]"),
                        name -> new StringCombiner(", ", "[", "]").add(name),
                        StringCombiner::merge))
                .toString();
    }



}
