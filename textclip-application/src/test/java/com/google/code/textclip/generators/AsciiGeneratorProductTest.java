package com.google.code.textclip.generators;

import com.google.code.textclip.exceptions.OutOfRangeException;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

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
public class AsciiGeneratorProductTest {
    /**
     * GENERAL: Strive to test the public interface of the model, ignoring
     * setters and getters. Tests should be arranged so that the tests
     * start with functionality used by other methods.
     *
     */

    /**
     * validates the lower limits with a negative value.
     */
    @Test(expected = OutOfRangeException.class)
    public void generate_LowerLimitHasNegativeValue_OutOfRangeException()
            throws OutOfRangeException, IOException {

        /*
        ARRANGE
         */
        String[][] testData = {
                {"Limits: lower < 0", "Don't Care", "-1", "100"}
        };

        performActualTest(testData);
    }

    /**
     * validates the lower limit being zero.
     */
    @Test(expected = OutOfRangeException.class)
    public void generate_LowerLimitHasZeroValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
         /*
            ARRANGE
         */
        String[][] testData = {
                {"Limits: lower < 0", "Don't Care", "0", "100"}
        };

        performActualTest(testData);
    }

    /**
     * validates lower bigger the the upper limit.
     */
    @Test(expected = OutOfRangeException.class)
    public void generate_LowerIsBiggerValueThanUpperValue_OutOfRangeException()
            throws OutOfRangeException, IOException {

         /*
            ARRANGE
         */
        String[][] testData = {
                {"Limits: lower >= upper", "Don't Care", "101", "100"}
        };

        performActualTest(testData);
    }

    /**
     * validates the upper over max boundaries.
     */
    @Test(expected = OutOfRangeException.class)
    public void generate_UpperOverMaxValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
         /*
        ARRANGE
         */
        String[][] testData = {
                {"Limits: upper > 254", "Don't Care", "100", "255"}
        };

        performActualTest(testData);
    }

    /**
     * validates the happy flow.
     */
    @Test
    public void generate_HapplyFlow() throws OutOfRangeException, IOException {
        /*
        ARRANGE
         */
        String[][] testData = {
                {"Alphabet LOWER case characters", "abcdefghijklmnopqrstuvwxyz", "97", "122"},
                {"Alphabet UPPER case characters", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "65", "90"},
                {"Limits: lower == upper", "d", "100", "100"}
        };

        performActualTest(testData);
    }

    /**
     * validates the happy flow of the Ascii Generator.
     */
    @Test
    public void generate_HapplyFlowAllCharacters()
            throws OutOfRangeException {

        for (int i = 1; i <= 254; i++) {
            TestCase.assertEquals(Integer.toString(i),
                    Character.toString((char) i),
                    new AsciiGeneratorProduct(i).generate());
        }
    }

    /*
     |--------------------------------------------------------------------------
     | TEST GENERATE FULL RANGE ASCII
     |--------------------------------------------------------------------------
     */

    /**
     * validates the full range 1 .. 254 with parameters. It produces a string
     * containing all ASCII characters from 1 to 254.
     * the actual content of the generated all ASCII characters string is
     * hard to test, therefor the test is performed on length in stead of
     * string content comparison.
     */
    @Test
    public void validateFullRangeWithArguments()
            throws OutOfRangeException {
        TestCase.assertEquals("ASCII (with arguments) full range",
                254,
                new AsciiGeneratorProduct(1, 254).generate().length());
    }

    /**
     * validates the full range 1 .. 254 with parameters. It produces a string
     * containing all ASCII characters from 1 to 254.
     * the actual content of the generated all ASCII characters string is
     * hard to test, therefor the test is performed on length in stead of
     * string content comparison.
     */
    @Test
    public void validateFullRangeWithoutArguments()
            throws OutOfRangeException {
        TestCase.assertEquals("ASCII (without arguments) full range",
                254,
                new AsciiGeneratorProduct().generate().length());
    }


    /*
     |--------------------------------------------------------------------------
     | TEST GENERATE SINGLE CHARACTER ASCII
     |--------------------------------------------------------------------------
     */

    /**
     * validates the lower limit of the character value (-10).
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionLowerLimitMoreNegative() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == -10", "",
                new AsciiGeneratorProduct(-10).generate());
    }

    /**
     * validates the lower limit of the character value (-1).
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionLowerLimitNegative() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == -1", "",
                new AsciiGeneratorProduct(-1).generate());
    }

    /**
     * validates the lower limit of the character value (0).
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionLowerLimitgenerateASCII() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 0", "",
                new AsciiGeneratorProduct(0).generate());
    }


    /**
     * validates the lower limit of the character value (generateASCII: 1).
     */
    @Test
    public void exceptionOnLowerLimitgenerateASCII()
            throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 1", "\u0001",
                new AsciiGeneratorProduct(1).generate());
    }

    /**
     * validates the upper limit of the character value (generateASCII: 254).
     */
    @Test
    public void onUpperLimitBoundary() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 254", "\u00fe",
                new AsciiGeneratorProduct(254).generate());
    }


    /**
     * validates the upper limit of the character value (generateASCII: 255).
     */
    @Test(expected = OutOfRangeException.class)
    public void exceptionOnUpperLimit()
            throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 1", "Don't Care",
                new AsciiGeneratorProduct(255).generate());
    }


    private void performActualTest(String[][] testData) throws OutOfRangeException, IOException {
        for (String[] arguments : testData) {
            /*
            ACT
            */
            GeneratorProduct theProduct = new AsciiGeneratorProduct(
                    Integer.parseInt(arguments[2]),
                    Integer.parseInt(arguments[3]));
            String theTestString = theProduct.generate();

            /*
            ASSERT
            */
            TestCase.assertEquals(arguments[0], arguments[1], theTestString);
        }
    }
}
