/*
 This software is the confidential information and copyrighted work of
 NetCracker Technology Corp. ("NetCracker") and/or its suppliers and
 is only distributed under the terms of a separate license agreement
 with NetCracker.
 Use of the software is governed by the terms of the license agreement.
 Any use of this software not in accordance with the license agreement
 is expressly prohibited by law, and may result in severe civil
 and criminal penalties. 
 
 Copyright (c) 1995-2024 NetCracker Technology Corp.
 
 All Rights Reserved.
 
*/
/*
 * Copyright 1995-2024 by NetCracker Technology Corp.,
 * University Office Park III
 * 95 Sawyer Road
 * Waltham, MA 02453
 * United States of America
 * All rights reserved.
 */
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
/*
 WITHOUT LIMITING THE FOREGOING, COPYING, REPRODUCTION, REDISTRIBUTION,
 REVERSE ENGINEERING, DISASSEMBLY, DECOMPILATION OR MODIFICATION
 OF THE SOFTWARE IS EXPRESSLY PROHIBITED, UNLESS SUCH COPYING,
 REPRODUCTION, REDISTRIBUTION, REVERSE ENGINEERING, DISASSEMBLY,
 DECOMPILATION OR MODIFICATION IS EXPRESSLY PERMITTED BY THE LICENSE
 AGREEMENT WITH NETCRACKER. 
 
 THIS SOFTWARE IS WARRANTED, IF AT ALL, ONLY AS EXPRESSLY PROVIDED IN
 THE TERMS OF THE LICENSE AGREEMENT, EXCEPT AS WARRANTED IN THE
 LICENSE AGREEMENT, NETCRACKER HEREBY DISCLAIMS ALL WARRANTIES AND
 CONDITIONS WITH REGARD TO THE SOFTWARE, WHETHER EXPRESS, IMPLIED
 OR STATUTORY, INCLUDING WITHOUT LIMITATION ALL WARRANTIES AND
 CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 TITLE AND NON-INFRINGEMENT.
 
 Copyright (c) 1995-2024 NetCracker Technology Corp.
 
 All Rights Reserved.
*/