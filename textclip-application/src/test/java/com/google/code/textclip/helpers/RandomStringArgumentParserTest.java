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
import junit.framework.TestCase;
import org.junit.Test;

public class RandomStringArgumentParserTest {
    final String actualExceptionMessage = "String does not meet requirements, e.g. 25:30:35";

    @Test
    public void constructor_EmptyOptionString_FormatException() {
        /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT & ASSERT
         */
        try {
            new RandomStringArgumentParser("");
        } catch (FormatException e) {
            TestCase.assertSame("Option string is empty.", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }

    @Test(expected = FormatException.class)
    public void constructor_lowerLimitOnly_FormatException() throws FormatException {
        /*
        ARRANGE, ACT & ASSERT
         */
        final String value = "32";

        new RandomStringArgumentParser(value);
    }

    @Test(expected = FormatException.class)
    public void constructor_LimitsOnly_FormatException() throws FormatException {
        /*
        ARRANGE
         */
        final String value = "32:33";
        /*
            ACT & ASSERT
         */
        new RandomStringArgumentParser(value);
    }

    @Test
    public void constructor_NegativeLowerLimit_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new RandomStringArgumentParser("-25:30:100");
        } catch (FormatException e) {
            TestCase.assertSame("Option string contains negative lower limit", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        /*
        ASSERT
        */
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }

    @Test
    public void constructor_lowerLimitWithSeparator_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new RandomStringArgumentParser("25:");
        } catch (FormatException e) {
            TestCase.assertSame("Option String contains text about missing separator", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        /*
        ASSERT
        */
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }

    @Test
    public void constructor_multipleSeparators_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new RandomStringArgumentParser("25:30:40:50");
        } catch (FormatException e) {
            TestCase.assertSame("Option String contains text about multiple separator", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        /*
        ASSERT
        */
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }


    @Test
    public void constructor_CorrectOptionString() throws FormatException {
        /*
        ARRANGE & ACT
         */
        RandomStringArgumentParser product = new RandomStringArgumentParser("25:30:50");

        /*
        ASSERT
        */
        TestCase.assertTrue("Lower Limit", 25 == product.getLowerLimit());
        TestCase.assertTrue("Upper Limit", 30 == product.getUpperLimit());
        TestCase.assertTrue("Length", 50 == product.getLength());

    }

}
