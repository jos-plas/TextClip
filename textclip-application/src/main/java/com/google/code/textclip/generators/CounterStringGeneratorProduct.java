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

/**
 * Generates a string with with which the test/developer can verify the
 * maximum length of an input field.
 * <p/>
 * Example:
 * "generateCounterString('*',25) would produce "*2*4*6*8*10*13*16*19*22**"
 *
 * @param theChar
 * @param length
 * @return generated counter string.
 */
public class CounterStringGeneratorProduct implements GeneratorProduct {
    private char character = 0;
    private int length = 1;

    public CounterStringGeneratorProduct(final char theChar, final int theLength)
            throws OutOfRangeException {
        if (theLength < 1) {
            throw new OutOfRangeException("length value is smaller than 1");
        }
        this.character = theChar;
        this.length = theLength;
    }

    @Override
    public String generate() {
        String result = Character.toString(this.character);
        int position = 0;
        while (position <= this.length) {
            position = result.length() + 1;
            result = addPosition(result, position);
            position = result.length() + 1;
            result = addCharacter(result, position);
        }
        return result;
    }

    /**
     * If there is still room for a single character, than add it.
     */
    private String addCharacter(String result, int position) {
        return (position <= this.length)
                ? result + Character.toString(this.character)
                : result;
    }

    /**
     * If there is still enough position in the result string left,
     * then add position in text to the result string
     */
    private String addPosition(String result, int position) {
        String position_in_text = Integer.toString(position);
        if ((position + position_in_text.length()) <= this.length) {
            result += position_in_text;
        }
        return result;
    }
}
