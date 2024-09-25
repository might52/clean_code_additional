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

import org.junit.jupiter.api.Test;
import org.might.lambda.functional.implementation.ArtistAnalyzer;
import org.might.lambda.functional.implementation.CallbackArtistAnalyser;
import org.might.lambda.functional.implementation.CompletableFutureArtistAnalyser;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 1/15/2024
 * Time: 9:59 AM
 */

public class ArtistAnalyzerTest {

    @Test
    public void largerGroupsAreLargerCallback() {
        assertLargerGroup(true, "The Beatles", "John Coltrane", new CallbackArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }
    @Test
    public void largerGroupsAreLargerCompletableFuture() {
        assertLargerGroup(true, "The Beatles", "John Coltrane", new CompletableFutureArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }


    @Test
    public void smallerGroupsArentLargerCallback() {
        assertLargerGroup(false, "John Coltrane", "The Beatles", new CallbackArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }

    @Test
    public void smallerGroupsArentLargerCompletableFuture() {
        assertLargerGroup(false, "John Coltrane", "The Beatles", new CompletableFutureArtistAnalyser(new FakeLookupService()::lookupArtistName));
    }


    public void assertLargerGroup(boolean expected, String artistName, String otherArtistName, ArtistAnalyzer analyzer) {
        AtomicBoolean isLarger = new AtomicBoolean(!expected);
        analyzer.isLargerGroup(artistName, otherArtistName, isLarger::set);
        assertEquals(expected, isLarger.get());
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