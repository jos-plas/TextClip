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

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


/**
 * GENERAL: Strive to test the public interface of the model, ignoring
 * setters and getters. Tests should be arrange so that the tests
 * start with functionality used by other methods.
 */
public class ArgumentParserTest {
    private CmdLineParser parser = null;
    private ArgumentParser options = null;

    /**
     * Methods with the annotation ’Before ’ are executed before every test.
     * The test object should be brought to the initial state all tests
     * assume it to be in.
     * <p/>
     * In case of the ArgumentParser the initialization needs to be done
     * only once. The @BeforeClass would be the best choice, but that
     * method needs to be declared static.
     */
    @Before
    public void initialize() {
        options = new ArgumentParser();
        parser = new CmdLineParser(options);
    }


    @Test
    public void getMultiple_validArguments() throws CmdLineException {
        final String[][] args = {{"-m", "25"}, {"--multiply", "21"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertSame("Multiple with option: " + arg[0], (long) Integer.parseInt(arg[1]), options.getMultiply());
        }
    }

    @Test
    public void isHelp_validArguments() throws CmdLineException {
        final String[][] args = {{"-h"}, {"--help"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("Help with option: " + arg[0], options.isHelp());
        }
    }

    @Test
    public void isText_validArguments() throws CmdLineException {
        /*
        ARRANGE
         */
        final String[][] args = {{"-t", "4:z"}, {"--text", "2:ë"}};


        for (String[] arg : args) {
            /*
            ACT
             */
            parser.parseArgument(arg);

            /*
            ASSERT
             */
            TestCase.assertTrue("Text with option: " + arg[0], options.isText());
            TestCase.assertFalse("NOT counterstring", options.isCounterstring());
            TestCase.assertFalse("NOT file", options.isFile());
            TestCase.assertFalse("NOT all chars", options.isAllcharacters());
            TestCase.assertFalse("NOT ascii", options.isAsciiValue());
        }
    }

    @Test
    public void isText_EmptySecondArgument() throws CmdLineException {
        final String[][] args = {{"-t", ""}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("Text with option: " + arg[0], !options.isText());
        }
    }

    @Test
    public void getText_validArguments() throws CmdLineException {
        final String[][] args = {{"-t", "4:z"}, {"--text", "2:ë"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertSame("Text with option: " + arg[0], arg[1], options.getText());
        }
    }


    @Test
    public void isCounterString_validArguments() throws CmdLineException {
        final String[][] args = {{"-co", "4:z"}, {"--counterstring", "2:ë"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("Counterstring with option: " + arg[0], options.isCounterstring());
            TestCase.assertFalse("NOT text", options.isText());
            TestCase.assertFalse("NOT file", options.isFile());
            TestCase.assertFalse("NOT all chars", options.isAllcharacters());
            TestCase.assertFalse("NOT ascii", options.isAsciiValue());
        }
    }

    @Test
    public void isCounterString_EmptySecondArgument() throws CmdLineException {
        final String[][] args = {{"-co", ""}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("Counterstring with option: " + arg[0], !options.isCounterstring());
        }
    }

    @Test
    public void getCounterString_validArguments() throws CmdLineException {
        final String[][] args = {{"-co", "4:z"}, {"--counterstring", "2:ë"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertSame("Counterstring with option: " + arg[0], arg[1], options.getCounterstring());
        }
    }

    @Test
    public void isAllCharacters_validArguments() throws CmdLineException {
        final String[][] args = {{"-a"}, {"--allchars"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("AllCharacters with option: " + arg[0], options.isAllcharacters());
            TestCase.assertFalse("NOT text", options.isText());
            TestCase.assertFalse("NOT counterstring", options.isCounterstring());
            TestCase.assertFalse("NOT file", options.isFile());
            TestCase.assertFalse("NOT ascii", options.isAsciiValue());
        }
    }


    @Test
    public void isFile_validArguments() throws CmdLineException {
        final String[][] args = {{"-f", "file.txt"}, {"--filename", "file.txt"}, {"-f", ""}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("File with option: " + arg[0], options.isFile());

            TestCase.assertFalse("NOT text", options.isText());
            TestCase.assertFalse("NOT counterstring", options.isCounterstring());
            TestCase.assertFalse("NOT all chars", options.isAllcharacters());
            TestCase.assertFalse("NOT ascii", options.isAsciiValue());
        }
    }


    @Test
    public void getFile_validArguments() throws CmdLineException {
        final String[][] args = {{"-co", "4:z"}, {"--counterstring", "2:ë"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertSame("File with option: " + arg[0], arg[1], options.getCounterstring());
        }
    }


    @Test
    public void isAscii_validArguments() throws CmdLineException {
        final String[][] args = {{"-ch", "101"}, {"--char", "102"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertTrue("All characters with option: " + arg[0], options.isAsciiValue());
            TestCase.assertFalse("NOT text", options.isText());
            TestCase.assertFalse("NOT counterstring", options.isCounterstring());
            TestCase.assertFalse("NOT all chars", options.isAllcharacters());
            TestCase.assertFalse("NOT file", options.isFile());
        }
    }


    @Test
    public void getAscii_validArguments() throws CmdLineException {
        final String[][] args = {{"-ch", "32"}, {"--char", "32"}};

        for (String[] arg : args) {
            parser.parseArgument(arg);
            TestCase.assertSame("All characters with option: " + arg[0], arg[1], options.getAscii_value());
        }
    }


    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_a_ch() throws CmdLineException {
        String[] testData = {"-a", "-ch", "25"};
        parser.parseArgument(testData);
    }


    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_a_c0() throws CmdLineException {
        String[] testData = {"-a", "-c0", "25:*"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_a_t() throws CmdLineException {
        String[] testData = {"-a", "-t", "TEXT"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_a_f() throws CmdLineException {
        String[] testData = {"-a", "-f", "the_filename.txt"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_ch_co() throws CmdLineException {
        String[] testData = {"-ch", "251", "-co", "80:a"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_ch_t() throws CmdLineException {
        String[] testData = {"-ch", "251", "-t", "TEXT2"};
        parser.parseArgument(testData);
    }


    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_ch_f() throws CmdLineException {
        String[] testData = {"-ch", "251", "-f", "filename.txt"};
        parser.parseArgument(testData);
    }


    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_co_t() throws CmdLineException {
        String[] testData = {"-co", "251:*", "-t", "TEXT3"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_co_f() throws CmdLineException {
        String[] testData = {"-co", "251:*", "-f", "filename.txt"};
        parser.parseArgument(testData);
    }

    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a string (-t) and
     * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
     * these type of combinations. All forbidden combinations are tested in this testcase.
     */
    @Test(expected = CmdLineException.class)
    public void validateForbiddenCombinations_t_f() throws CmdLineException {
        String[] testData = {"-t", "TEXT4", "-f", "filename.txt"};
        parser.parseArgument(testData);
    }
}
