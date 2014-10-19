/*
 * Copyright (c) 2014, Jos Plas
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.google.code.textclip.helpers;

import com.google.code.textclip.exceptions.FormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomStringArgumentParser {
    private int upper_limit = 0;
    private int lower_limit = 0;
    private int length = 0;

    public RandomStringArgumentParser(final String optionString)
            throws FormatException {
        Pattern pattern = Pattern.compile("^(\\d+):(\\d+):(\\d+)$");
        Matcher matcher = pattern.matcher(optionString);

        if (matcher.matches()) {
            parseGroups(matcher);
        } else {
            throw new FormatException("String does not meet requirements, e.g. 25:30:35");
        }
    }

    public int getUpperLimit() {
        return upper_limit;
    }

    public int getLowerLimit() {
        return lower_limit;
    }

    public int getLength() {
        return length;
    }

    private void parseGroups(Matcher matcher) {
        this.lower_limit = Integer.parseInt(matcher.group(1));
        this.upper_limit = Integer.parseInt(matcher.group(2));
        this.length = Integer.parseInt(matcher.group(3));
    }
}