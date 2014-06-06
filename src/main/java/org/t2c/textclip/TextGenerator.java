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
package org.t2c.textclip;

import org.t2c.textclip.exceptions.FileContentException;
import org.t2c.textclip.exceptions.OutOfRangeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TextGenerator {
    final static int CONST_LOWER_LIMIT = 1;
    final static int CONST_UPPER_LIMIT = 254;

    /**
     * Generates a string with ASCII characters starting from the lower_limit
     * up to the upper limit.
     *
     * Example:
     * .generateASCII(65,70) returns string "ABCDEF".
     *
     *
     * @param lower_limit  larger equal to 1 and smaller than upper_limit
     * @param upper_limit  upper limit
     * @return generated ascii character string
     */
    public static String generateASCII(int lower_limit, int upper_limit) throws OutOfRangeException {
        boolean inUpperRange =
                (lower_limit <= CONST_UPPER_LIMIT) &&
                        (upper_limit <= CONST_UPPER_LIMIT);

        if (!inUpperRange) {
            throw new OutOfRangeException("upper value is out of range (" +
                    CONST_UPPER_LIMIT + ").");
        }

        boolean inLowerRange =
                (lower_limit >= CONST_LOWER_LIMIT) &&
                        (upper_limit >= CONST_LOWER_LIMIT);
        if (!inLowerRange) {
            throw new OutOfRangeException("lower value is out of range (" +
                    CONST_LOWER_LIMIT + ").");
        }

        if (lower_limit > upper_limit) {
            throw new OutOfRangeException("lower value is larger than " +
                    "upper value.");
        }


        String ascii = "";
        for (int  i = lower_limit; i <= upper_limit; i++) {
            ascii += generateASCII(i);
        }

        return ascii;
    }

    /**
     *
     * @return a String containing all ASCII characters from 1 to 254.
     */
    public static String generateASCII() throws OutOfRangeException {
        return generateASCII(1,254);
    }


    /**
     * Generates a string with one ASCII character indicated by character_value.
     *
     * Example:
     * .generateASCIIChar(65) returns string "A".
     *
     *
     * @param character_value  integer character value in the range from 1 up
     *                         to 254.
     * @return generated one ascii character string
     */
    public static String generateASCII(final int character_value) throws OutOfRangeException {
        if ((character_value < 1) || (character_value > 254)) {
            throw new OutOfRangeException(
                    "value is out of range (1..254).");
        }
        return Character.toString((char) character_value);
    }

    /**
     * Generates a string with one ASCII character indicated by character_value.
     *
     * Example:
     * .generateASCIIChar(65) returns string "A".
     *
     *
     * @param character_value
     *              integer character value in the range from 1 up
     *              to 254.
     *
     * @return generated one ascii character string
     */
    public static String generateFromFile(final File theFile) throws
            IOException, FileContentException {
        if (!theFile.exists()){
            throw new FileNotFoundException("could not find file (" +
                        theFile.getName() + ").");
        }

        /*
         * The file exists; proceed testing the size (0 or >=2^31)
         */
        if ((theFile.length() == 0) || (theFile.length() >= Math.pow(2,31))) {
            throw new FileContentException(theFile);
        }

        /*
         * File exists and is of the correct size; proceed
         */
        FileInputStream fis = new FileInputStream(theFile.toString());
        byte [] buffer = new byte[(int)theFile.length()];
        fis.read(buffer);
        fis.close();
        return new String(buffer);
    }



    /**
     * Generates a string with with which the test/developer can verify the
     * maximum length of an input field.
     *
     * Example:
     * "generateCounterString('*',25) would produce "*2*4*6*8*10*13*16*19*22**"
     *
     * @param theChar
     * @param length
     *
     * @return generated counter string.
     */
    public static String generateCounterString(char theChar, int length)
                                            throws OutOfRangeException {
        if (length < 1) {
            throw new OutOfRangeException("length value is smaller than 1");
        }

        String result = Character.toString(theChar);
        int position  = 0;
        while (position <= length) {
            position = result.length() + 1;
            /*
             * If there is still enough position in the result string left,
             * then add position in text to the result string */
            String position_in_text = Integer.toString(position);
            if ((position+position_in_text.length()) <= length) {
                result += position_in_text;
            }
            position = result.length() + 1;

            /*
             * If there is still room for a single character, than add it.
             */
            if (position <= length) {
                result += Character.toString(theChar);
            }
        }
        return result;
    }
}