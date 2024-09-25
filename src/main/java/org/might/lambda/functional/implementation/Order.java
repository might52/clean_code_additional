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