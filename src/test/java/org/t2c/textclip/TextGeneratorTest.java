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

import junit.framework.TestCase;
import org.t2c.textclip.exceptions.FileContentException;
import org.t2c.textclip.exceptions.OutOfRangeException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;


/**
* GENERAL: Strive to test the public interface of the model, ignoring
* setters and getters. Tests should be arrange so that the tests
* start with functionality used by other methods.
*
*/
public class TextGeneratorTest {

    /*
     |--------------------------------------------------------------------------
     | TEST GENERATE ASCII RANGE
     |--------------------------------------------------------------------------
     */

    /**
     * validates the lower boundary on -1 value of method 'generateASCII'.
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLowerLimitNegativeValueGenerateASCII()
                                                throws OutOfRangeException {
        TestCase.assertEquals("Limits: lower < 0","",
                TextGenerator.generateASCII(-1, 100));
    }


    /**
     * validates the lower boundary on 0 value of method 'generateASCII'.
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLowerLimitZeroGenerateASCII()
                                                throws OutOfRangeException {
        TestCase.assertEquals("Limits: lower set to 0","",
                TextGenerator.generateASCII(0,100));
    }


    /**
     * validates the lower limit larger than the upper limit (limits are in
     * correct range).
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLowLargerUpLimitGenerateASCII()
                                            throws OutOfRangeException {
        TestCase.assertEquals("Limits: lower > upper","",
                TextGenerator.generateASCII(101,100));
    }

    /**
     * validates the upper limit larger than 254.
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLargerUpLimitGenerateASCII()
                                            throws OutOfRangeException {
        TestCase.assertEquals("Limits: upper > 254","",
                TextGenerator.generateASCII(101,255));
    }


    /**
     * validates the happy flow of method generateASCII.
     */
    @Test
    public void validateHapplyFlowGenerateASCII() throws OutOfRangeException {
        TestCase.assertEquals("Alphabet lower case characters",
                    "abcdefghijklmnopqrstuvwxyz",
                    TextGenerator.generateASCII(97,122));

        TestCase.assertEquals("Alphabet upper case characters",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                TextGenerator.generateASCII(65,90));


        /*
         * the content of the generated all ASCII characters string is hard,
         * therefor the test is performed on length in stead of the string.
         */
        TestCase.assertEquals("ASCII (extended) full range",
                254,
                TextGenerator.generateASCII(1,254).length());

        /*
         * validate the lower and upper limit can have the same value.
         */
        TestCase.assertEquals("Limits: lower == upper","\u0064",
                TextGenerator.generateASCII(100,100));
    }


    /*
     |--------------------------------------------------------------------------
     | TEST GENERATE FULL RANGE ASCII
     |--------------------------------------------------------------------------
     */

    /**
     * validates method generateASCII without parameters. It produces a string
     * containing all ASCII characters from 1 to 254.
     */
    @Test
    public void validateGenerateASCII() throws OutOfRangeException {
        /*
         * the actual content of the generated all ASCII characters string is
         * hard to test, therefor the test is performed on length in stead of
         * string content comparison.
         */
        TestCase.assertEquals("ASCII (extended) full range (no arguments)",
                254,
                TextGenerator.generateASCII().length());
    }



    /*
     |--------------------------------------------------------------------------
     | TEST GENERATE SINGLE CHARACTER ASCII
     |--------------------------------------------------------------------------
     */
    /**
     * validates the lower limit of the character value (generateASCII: 0).
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLowerLimitgenerateASCII() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value < 1","",
                TextGenerator.generateASCII(0));
    }


    /**
     * validates the lower limit of the character value (generateASCII: 1).
     */
    @Test
    public void exceptionOnLowerLimitgenerateASCII()
                                                throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 1","\u0001",
                TextGenerator.generateASCII(1));
    }


    /**
     * validates the lower limit of the character value (generateASCII: -10).
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionLowerLimitNegativegenerateASCII()
                                                throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value < 0","",
                TextGenerator.generateASCII(-10));
    }



    /**
     * validates the upper limit of the character value (generateASCII: 255).
     */
    @Test (expected=OutOfRangeException.class)
    public void exceptionOverUpperLimitgenerateASCII()
                                                throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value > 254","",
                TextGenerator.generateASCII(255));
    }



    /**
     * validates the upper limit of the character value (generateASCII: 254).
     */
    @Test
    public void onUpperLimitGenerateASCII() throws OutOfRangeException {
        TestCase.assertEquals("Limits: character_value == 254","\u00fe",
                TextGenerator.generateASCII(254));
    }


    /*
     |--------------------------------------------------------------------------
     | TEST FILE OPTION
     |---------------------------------------------------------------------------
     */

    /**
     * Validates whether an exception is thrown if the inputfile does not exist.
     */
    @Test (expected = FileNotFoundException.class)
    public void fileDoesNotExist() throws IOException, FileContentException {
        File theFile= new File("does_not_exist.txt");

        TestCase.assertEquals("File does not exist","",
                TextGenerator.generateFromFile(theFile));
    }



    /**
     * Validates whether test string can be constructed from the content of a
     * file.
     */
    @Test
    public void generateFromFile() throws
                    IOException, FileContentException, URISyntaxException {
        String fileName = "/textgenerator/file_has_content.txt";
        TestCase.assertNotNull("Test file missing: "+fileName,
                                        getClass().getResource(fileName));

        File theFile= new File(getClass().getResource(fileName).toURI());
        TestCase.assertEquals("File has content", "This is a test file with a" +
                " little content.",
                TextGenerator.generateFromFile(theFile));
    }



    /**
     * Validates whether test string can be constructed from the content of a
     * file.
     */
    @Test (expected = FileContentException.class)
    public void generateFromFileWithNoContent()
            throws IOException, FileContentException, URISyntaxException {

        String fileName = "/textgenerator/file_has_no_content.txt";
        TestCase.assertNotNull("Test file missing: "+fileName,
                                        getClass().getResource(fileName));

        File theFile= new File(getClass().getResource(fileName).toURI());

        TestCase.assertEquals("File has no content",
                "Specified file (file_has_no_content.txt) has no content" +
                        " or does not exist.",
                TextGenerator.generateFromFile(theFile));
    }

    /**
     * Validates whether the length argument of the generateCounterString is
     * saveguarded correctly : -1
     */
    @Test (expected = OutOfRangeException.class)
    public void exceptionCounterStringNegativeLength() throws
                                                        OutOfRangeException {
        TextGenerator.generateCounterString('A',-1);
    }

    /**
     * Validates whether the length argument of the generateCounterString is
     * saveguarded correctly : 0
     */
    @Test (expected = OutOfRangeException.class)
    public void exceptionCounterStringZeroLength() throws OutOfRangeException {
        TextGenerator.generateCounterString('A',0);
    }


    /**
     * Validates the happy flow of generateCounterString.
     */
    @Test
    public void happyFlowGenerateCounterString() throws OutOfRangeException {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, length; RESULT: value.
                        { { "A","1"}, {"A"} },
                        { { "*","2"}, {"**"} },
                        { { "$","9"}, {"$2$4$6$8$"} },
                        { { "$","10"}, {"$2$4$6$8$$"} },
                        { { "$","11"}, {"$2$4$6$8$$$"} },
                        { { "$","12"}, {"$2$4$6$8$10$"} },
                        { { "$","13"}, {"$2$4$6$8$10$$"} },
                        { { "$","14"}, {"$2$4$6$8$10$$$"} },
                        { { "$","15"}, {"$2$4$6$8$10$13$"} },
                        { { "$","25"}, {"$2$4$6$8$10$13$16$19$22$$"} }
                };

        for (String[][] arguments:argumentsResultArray) {
            TestCase.assertEquals("Content",arguments[1][0],
                    TextGenerator.generateCounterString(arguments[0][0].charAt(0),
                               Integer.parseInt(arguments[0][1])));

            TestCase.assertEquals("Length", Integer.parseInt(arguments[0][1]),
                    TextGenerator.generateCounterString(arguments[0][0].charAt(0),
                            Integer.parseInt(arguments[0][1])).length());
        }


    }

}
