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
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class CounterStringGeneratorProductTest {
    /**
     * Validates whether the length argument of the generateCounterString is
     * saveguarded correctly : -1
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionCounterStringNegativeLength() throws
            OutOfRangeException {

        TestCase.assertEquals("Counter string with negative length",
                "Don't Care",
                new CounterStringGeneratorProduct('A', -1).generate());
    }

    /**
     * Validates whether the length argument of the generateCounterString is
     * saveguarded correctly : 0
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionCounterStringZeroLength() throws OutOfRangeException {
        TestCase.assertEquals("Counter string with negative length",
                "Don't Care",
                new CounterStringGeneratorProduct('A', 0).generate());
    }


    /**
     * Validates the happy flow of generateCounterString.
     */
    @Test
    public void happyFlowGenerateCounterString() throws OutOfRangeException, IOException {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, length; RESULT: value.
                        {{"A", "1"}, {"A"}},
                        {{"*", "2"}, {"**"}},
                        {{"$", "9"}, {"$2$4$6$8$"}},
                        {{"$", "10"}, {"$2$4$6$8$$"}},
                        {{"$", "11"}, {"$2$4$6$8$$$"}},
                        {{"$", "12"}, {"$2$4$6$8$10$"}},
                        {{"$", "13"}, {"$2$4$6$8$10$$"}},
                        {{"$", "14"}, {"$2$4$6$8$10$$$"}},
                        {{"$", "15"}, {"$2$4$6$8$10$13$"}},
                        {{"$", "25"}, {"$2$4$6$8$10$13$16$19$22$$"}},
                        {{"d", "25"}, {"d2d4d6d8d10d13d16d19d22dd"}}
                };

        for (String[][] arguments : argumentsResultArray) {
            TestCase.assertEquals("Content", arguments[1][0],
                    new CounterStringGeneratorProduct(arguments[0][0].charAt(0),
                            Integer.parseInt(arguments[0][1])).generate());

            TestCase.assertEquals("Length", Integer.parseInt(arguments[0][1]),
                    new CounterStringGeneratorProduct(arguments[0][0].charAt(0),
                            Integer.parseInt(arguments[0][1])).generate().length());
        }


    }
}
