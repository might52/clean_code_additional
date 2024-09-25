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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

/**
 * Date: 1/9/2024
 * Time: 5:16 PM
 */
public class Questions5 {

    public List<String> getUpperCasesList(List<String> list) {
        return list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }


    public int getCount(List<Artist> artists) {
        return artists.stream()
                .filter(artist -> artist.isFrom("London"))
                .map(artist -> Long.valueOf(artist.getMembers().count()).intValue())
                .reduce(0, Integer::sum);
    }

    public List<Artist> concatLists(List<Artist> artists) {
        return artists.stream()
                .flatMap(Artist::getMembers)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String getLongestNameReduce(Stream<String> names) {
        return names.reduce("", (left, right) -> left.length() < right.length() ? right : left);
    }

    public String getLongestNameCollector(Stream<String> names) {
        return names.collect(maxBy(comparing(String::length))).orElse("");
    }

    public Map<String, Integer> getNameAmountMap(Stream<String> names) {
        return new HashMap<>();
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