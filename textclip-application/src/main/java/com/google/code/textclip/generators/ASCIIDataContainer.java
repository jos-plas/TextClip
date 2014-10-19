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

public class ASCIIDataContainer {
    final static int CONST_LOWER_LIMIT = 1;
    final static int CONST_UPPER_LIMIT = 254;
    final static int CONST_LENGTH = 254;

    private int lowerLimit = CONST_LOWER_LIMIT;
    private int upperLimit = CONST_UPPER_LIMIT;
    private int length = CONST_LENGTH;

    public ASCIIDataContainer(final int theLowerLimit, final int theUpperLimit, final int theLength)
            throws OutOfRangeException {
        checkLimit("lower", theLowerLimit);
        checkLimit("upper", theUpperLimit);

        if (theLowerLimit > theUpperLimit) {
            throw new OutOfRangeException("lower value is larger than upper value.");
        }

        checkLength(theLength);

        this.lowerLimit = theLowerLimit;
        this.upperLimit = theUpperLimit;
        this.length = theLength;
    }

    public ASCIIDataContainer(final int theLowerLimit, final int theUpperLimit)
            throws OutOfRangeException {
        this(theLowerLimit, theUpperLimit, theUpperLimit - theLowerLimit + 1);
    }


    public ASCIIDataContainer() {
    }

    public ASCIIDataContainer(final int theLength) throws OutOfRangeException {
        this(CONST_LOWER_LIMIT, CONST_UPPER_LIMIT, theLength);
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public int getLength() {
        return length;
    }

    /*
    PRIVATE METHODS
     */

    private boolean isLimitOutOfRange(final int theTestValue) {
        return ((theTestValue < CONST_LOWER_LIMIT) ||
                (theTestValue > CONST_UPPER_LIMIT));
    }

    private boolean isLengthOutOfRange(final int theTestValue) {
        return (theTestValue <= 0);
    }

    private void checkLimit(String type, int theLowerLimit)
            throws OutOfRangeException {
        if (this.isLimitOutOfRange(theLowerLimit)) {
            throw new OutOfRangeException(type + " limit is out of range (" +
                    CONST_LOWER_LIMIT + " .. " + CONST_UPPER_LIMIT + ").");
        }
    }

    private void checkLength(int theLength)
            throws OutOfRangeException {
        if (this.isLengthOutOfRange(theLength)) {
            throw new OutOfRangeException("Length is out of range (0 .. " +
                    CONST_LENGTH + ").");
        }
    }
}
