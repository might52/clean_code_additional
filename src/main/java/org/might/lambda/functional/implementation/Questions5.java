package org.might.lambda.functional.implementation;

import org.might.lambda.functional.examples.chapter1.Artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;

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

    public Map<String, Long> getNameAmountMap(Stream<String> names) {
//        return names.collect(groupingBy(name -> name, counting()));
        return names.collect(groupingBy(name -> name,
                reducing(0L, name -> 1L, Long::sum)));
    }

}