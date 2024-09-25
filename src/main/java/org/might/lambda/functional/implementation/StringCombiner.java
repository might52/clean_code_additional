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

/**
 * Date: 1/9/2024
 * Time: 4:12 PM
 */
public class StringCombiner {

    private final String prefix;
    private final String suffix;
    private final String delim;
    private final StringBuilder builder;

    public StringCombiner(String delim, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delim = delim;
        this.builder = new StringBuilder();
    }

    public StringCombiner add (String word) {
        if(!this.areAtStart()) {
            this.builder.append(delim);
        }
        this.builder.append(word);

        return this;
    }

    public StringCombiner merge (StringCombiner other) {
        if(!other.equals(this)) {
            if(!other.areAtStart() && !this.areAtStart()){
                other.builder.insert(0, this.delim);
            }
            this.builder.append(other.builder);
        }
        return this;
    }

    @Override
    public String toString() {
        return prefix + builder.toString() + suffix;
    }

    private boolean areAtStart() {
        return builder.length() == 0;
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