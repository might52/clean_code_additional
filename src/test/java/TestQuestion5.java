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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.might.lambda.functional.implementation.Questions5;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/10/2024
 * Time: 1:53 PM
 */
@ExtendWith(MockitoExtension.class)
public class TestQuestion5 {

    private Stream<String> fullNameSrteam = Stream.of("John Lennon", "Paul McCartney", "George Harrison",
            "Ringo Starr", "Pete Best", "Stuart Sutcliffe");

    private Stream<String> nameStream = Stream.of("John", "Paul", "George", "John", "Paul", "John");

    private Map<String, Long> calcNames = new HashMap<>();

    @BeforeEach
    public void prepareResult() {
        calcNames.putIfAbsent("John", 3L);
        calcNames.putIfAbsent("Paul", 2L);
        calcNames.putIfAbsent("George", 1L);
    }

    @Test
    public void testGetLongestNameReduce() {
        String longestName = new Questions5().getLongestNameReduce(fullNameSrteam);
        assertEquals("Stuart Sutcliffe", longestName,
                String.format("Test result for %s: %s%n", "testGetLongestNameReduce", longestName));
    }

    @Test
    public void testGetLongestNameCollector() {
        String longestName = new Questions5().getLongestNameCollector(fullNameSrteam);
        assertEquals("Stuart Sutcliffe", longestName,
                String.format("Test result for %s: %s%n", "testGetLongestNameCollector", longestName));
    }

    @Test
    public void testGetNamesCountMap() {
        Map<String, Long> calculatedName = new Questions5().getNameAmountMap(nameStream);
        assertEquals(calcNames, calculatedName);
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