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
package com.google.code.textclip.generators;

import com.google.code.textclip.exceptions.OutOfRangeException;

public class AsciiGeneratorProduct implements GeneratorProduct {
    final static int CONST_LOWER_LIMIT = 1;
    final static int CONST_UPPER_LIMIT = 254;

    private int lowerLimit = 0;
    private int upperLimit = 0;

    public AsciiGeneratorProduct(final int theLowerLimit, final int theUpperLimit)
            throws OutOfRangeException {
        checkLimit("lower", theLowerLimit);
        checkLimit("upper", theUpperLimit);

        if (theLowerLimit > theUpperLimit) {
            throw new OutOfRangeException("lower value is larger than " +
                    "upper value.");
        }

        this.lowerLimit = theLowerLimit;
        this.upperLimit = theUpperLimit;
    }

    public AsciiGeneratorProduct(final int character) throws OutOfRangeException {
        this(character, character);
    }

    public AsciiGeneratorProduct() throws OutOfRangeException {
        this(CONST_LOWER_LIMIT, CONST_UPPER_LIMIT);
    }


    private boolean isLimitOutOfRange(final int theTestValue) {
        return ((theTestValue < CONST_LOWER_LIMIT) ||
                (theTestValue > CONST_UPPER_LIMIT));
    }

    private void checkLimit(String type, int theLowerLimit)
            throws OutOfRangeException {
        if (this.isLimitOutOfRange(theLowerLimit)) {
            throw new OutOfRangeException(type + " limit is out of range (" +
                    CONST_LOWER_LIMIT + " .. " + CONST_UPPER_LIMIT + ").");
        }
    }

    @Override
    public String generate() {
        String ascii = "";
        for (int i = this.lowerLimit; i <= this.upperLimit; i++) {
            ascii += Character.toString((char) i);
        }

        return ascii;
    }
}
