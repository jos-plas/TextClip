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

public class CounterStringArgumentParserTest {
    final String actualExceptionMessage = "String does not meet requirements, e.g. 25:d.";

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
            new CounterStringArgumentParser("");
        } catch (FormatException e) {

            TestCase.assertSame("Option string is empty.", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }

    @Test
    public void constructor_lengthOnly_FormatException() {
        /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new CounterStringArgumentParser("25");
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
    public void constructor_lengthWithSeparator_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new CounterStringArgumentParser("25:");
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
    public void constructor_NegativeLength_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new CounterStringArgumentParser("-25:w");
        } catch (FormatException e) {
            TestCase.assertSame("Option string contains negative length", e.getMessage(), actualExceptionMessage);
            exception = true;
        }
        /*
        ASSERT
        */
        TestCase.assertTrue("Exception has been thrown (final check)", exception);
    }

    @Test
    public void constructor_MultipleCharacters_FormatException() {
                /*
        ARRANGE
         */
        boolean exception = false;

        /*
        ACT
         */
        try {
            new CounterStringArgumentParser("25:aa");
        } catch (FormatException e) {
            TestCase.assertSame("Option string contains multiple", e.getMessage(), actualExceptionMessage);
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
        CounterStringArgumentParser csar = new CounterStringArgumentParser("25:A");

        /*
        ASSERT
        */
        TestCase.assertSame("Length", 25, csar.getLength());
        TestCase.assertSame("Character", 'A', csar.getCharacter());
    }

    @Test
    public void constructor_CorrectDoubleSemiColumnOptionString() throws FormatException {
        /*
        ARRANGE & ACT
         */
        CounterStringArgumentParser csar = new CounterStringArgumentParser("25::");

        /*
        ASSERT
        */
        TestCase.assertSame("Length", 25, csar.getLength());
        TestCase.assertSame("Character", ':', csar.getCharacter());
    }

}
