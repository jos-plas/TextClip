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
package com.google.code.textclip;

import com.google.code.textclip.enums.TextClipError;
import com.google.code.textclip.exceptions.FileSizeException;
import com.google.code.textclip.exceptions.FormatException;
import com.google.code.textclip.exceptions.InvalidGeneratorProductException;
import com.google.code.textclip.exceptions.OutOfRangeException;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kohsuke.args4j.CmdLineParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GENERAL: Strive to test the public interface of the model, ignoring
 * setters and getters. Tests should be arrange so that the tests
 * start with functionality used by other methods.
 */
public class TextClipTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private CmdLineParser parser = null;
//
//
//    /**
//     * Get the String residing on the clipboard.
//     *
//     * @return any text found on the Clipboard; if none found, return an
//     * empty String.
//     * @TODO: code needs to be revised for unit test purposes!!!
//     */
//    private String getClipboardContents() {
//        String result = "";
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        //odd: the Object param of getContents is not currently used
//        Transferable contents = clipboard.getContents(null);
//        boolean hasTransferableText =
//                (contents != null) &&
//                        contents.isDataFlavorSupported(DataFlavor.stringFlavor);
//        if (hasTransferableText) {
//            try {
//                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
//            } catch (UnsupportedFlavorException ex) {
//                System.out.println(ex);
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                System.out.println(ex);
//                ex.printStackTrace();
//            }
//        }
//        return result;
//    }
//



    /**
     * Validate Forbidden commandline combinations
     * <p/>
     * It makes no sense (at least for the moment) to for instance create a
     * string (-t) and have a character (-ch) for output as well. The
     * ArgumentParser is setup to forbid a these type of combinations. All
     * forbidden combinations are tested in this testcase.
     */
    @Test
    public void constructor_ForbiddenCombinations() {
        final String[][] argumentsArray = {
                {"-a", "-ch", "25"},
                {"-a", "-co", "30:*"},
                {"-a", "-t", "TEXT"},
                {"-a", "-f", "FILENAME"},
                {"-a", "-ra", "100:110:120"},
                {"-ch", "251", "-co", "80:a"},
                {"-ch", "252", "-t", "TEXT"},
                {"-ch", "253", "-f", "FILENAME"},
                {"-ch", "254", "-ra", "100:110:120"},
                {"-co", "30:#", "-t", "TEXT"},
                {"-co", "33:!", "-f", "FILENAME"},
                {"-co", "33:?", "-ra", "100:110:120"},
                {"-t", "TEXT", "-f", "FILENAME"},
                {"-t", "TEXT", "-ra", "100:110:120"},
                {"-f", "FILENAME", "-ra", "100:110:120"}
        };

        String buffer;
        for (String[] arguments : argumentsArray) {
            TextClip theTestApp = new TextClip(arguments);
            buffer = "";
            for (String argument : arguments) {
                buffer += String.format("%s ", argument);
            }
            TestCase.assertEquals("Tested arguments: \"" + buffer + "\"",
                    theTestApp.getStatus(),
                    TextClipError.ERROR_PARSING_ARGUMENTS);

        }
    }

    /**
     * Validate the same commandline option twice.
     * <p/>
     * Args4j has a problem using the same commandline option twice. This test
     * is ony present to investigate this arg4j issue.
     */
    @Test
    public void constructor_TheSameArgumentTwice() {
        final String[][] argumentsArray = {
                {"-a", "-a"},
                {"-ch", "251", "-ch", "251"},
                {"-co", "30:#", "-co", "30:#"},
                {"-t", "TEXT", "-t", "TEXT"},
                {"-f", "FILENAME", "-f", "FILENAME"},
                {"-ra", "70:80:90", "-ra", "80:90:100"}

        };

        for (String[] arguments : argumentsArray) {
            TextClip theTestApp = new TextClip(arguments);
            String buffer = "";
            for (String argument : arguments) {
                buffer += String.format("%s ", argument);
            }
            TestCase.assertEquals("Tested arguments: \"" + buffer + "\"",
                    TextClipError.NO_ERROR, theTestApp.getStatus());

        }
    }

    /**
     * Validates the all characters options op textclipt (-a and --allchars).
     */
    @Test
    public void optionAllCharacters() throws IOException,
            OutOfRangeException, FormatException,
            FileSizeException, InvalidGeneratorProductException {
        /*ARRANGE */
        final String[][] argumentsArray =
                {
                        {"-a"},
                        {"--allchars"}
                };

        /*ACT & ASSERT */
        for (String[] arguments : argumentsArray) {
            TextClip theApp = new TextClip(arguments);
            String actualVal = theApp.createTestString();
            TestCase.assertEquals("All characters (" + arguments[0] + ")",
                    254,
                    actualVal.length());

            TestCase.assertEquals("Error message on All characters (" + arguments[0] + ")",
                    "",
                    theApp.getStatus().getMessage());
        }
    }


    /**
     * Validates the character options of textclip (-ch and --character).
     */
    @Test
    public void constructor_optionCharacter() throws IOException, OutOfRangeException, FormatException, FileSizeException, InvalidGeneratorProductException {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, value; RESULT: value, error message.
                        {{"-ch", "0"}, {"", "The chosen value is out of range (1..254)."}},
                        {{"-ch", "1"}, {"\u0001", ""}},
                        {{"-ch", "65"}, {"A", ""}},
                        {{"--char", "65"}, {"A", ""}},
                        {{"-ch", "254"}, {"\u00fe", ""}},
                        {{"-ch", "255"}, {"", "The chosen value is out of range (1..254)."}}
                };

        for (String[][] arguments : argumentsResultArray) {
            TextClip theApp = new TextClip(arguments[0]);
            String actualVal = theApp.createTestString();
            TestCase.assertEquals("Test on value (" + arguments[0][0] + ", " + arguments[0][1] + ")",
                    arguments[1][0],
                    actualVal);
            TestCase.assertEquals("Test on error message (" + arguments[0][0] + ", " + arguments[0][1] + ")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }

    /**
     * Validates the character options of textclipt (-f and --filename).
     */
    @Test
    public void constuctor_optionFilename() throws IOException {
        String content = "This file has a little content";
        File testFileWithContent = createFile(testFolder,"content.txt",content);
        File testFileWithoutContent = createFile(testFolder,"empty.txt","");

        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, value; RESULT: value, error message.
                        {{"-f", testFileWithContent.getAbsolutePath()}, {content, ""}},
                        {{"-f", testFileWithoutContent.getAbsolutePath()}, {"", "Chosen file is unreadable, has a filesize of zero or is too large."}},
                        {{"-f", "file_does_not_exist.txt"}, {"", "Chosen file is unreadable, has a filesize of zero or is too large."}}
                };

        for (String[][] arguments:argumentsResultArray) {
            TextClip theApp = new TextClip(arguments[0]);
            String actValue = theApp.createTestString();
            TestCase.assertEquals("Test on value ("+ arguments[0][0]+", "+arguments[0][1] +")",
                    arguments[1][0],
                    actValue);
            TestCase.assertEquals("Test on error message (" + arguments[0][0] + ", " + arguments[0][1] + ")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }

    private File createFile(final TemporaryFolder testFolder,
                            final String fileName,
                            final String testContent) throws IOException {
        File theTestFile = testFolder.newFile(fileName);

        if (!testContent.isEmpty()) {
            BufferedWriter output = new BufferedWriter(new FileWriter(theTestFile));
            output.write(testContent);
            output.close();
        }
        return theTestFile;
    }


    /**
     * Validates the counterstring options of textclipt (-co and --counterstring).
     */
    @Test
    public void optionCounterString() {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, length; RESULT: value.
                        {{"-co", "1", "A"}, {"1", "A", ""}},
                        {{"-co", "2", "*"}, {"2", "**", ""}},
                        {{"-co", "9", "$"}, {"9", "$2$4$6$8$", ""}},
                        {{"-co", "10", "$"}, {"10", "$2$4$6$8$$", ""}},
                        {{"-co", "11", "$"}, {"11", "$2$4$6$8$$$", ""}},
                        {{"-co", "12", "$"}, {"12", "$2$4$6$8$10$", ""}},
                        {{"-co", "13", "$"}, {"13", "$2$4$6$8$10$$", ""}},
                        {{"-co", "14", "$"}, {"14", "$2$4$6$8$10$$$", ""}},
                        {{"-co", "15", "$"}, {"15", "$2$4$6$8$10$13$", ""}},
                        {{"--counterstring", "25", "$"}, {"25", "$2$4$6$8$10$13$16$19$22$$", ""}},
                        {{"--counterstring", "0", "$"}, {"0", "", "The chosen value is out of range (1..254)."}},
                        {{"--counterstring", "-10", "$"}, {"0", "", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\"."}},
                        {{"--counterstring", "25", "$b"}, {"0", "", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\"."}}
                };

        for (String[][] arguments : argumentsResultArray) {
            String[] args = {arguments[0][0], arguments[0][1] + ":" + arguments[0][2]};
            TextClip theApp = new TextClip(args);
            String actValue = theApp.createTestString();

            TestCase.assertEquals("Content", arguments[1][1], actValue);
            TestCase.assertEquals("Length", Integer.parseInt(arguments[1][0]),
                    actValue.length());

            TestCase.assertEquals("Test on error message (" + args[0] + ", " + args[1] + ")",
                    arguments[1][2],
                    theApp.getStatus().getMessage());
        }
    }

    /**
     * Validates the counterstring options of textclipt (-co and --counterstring).
     */
    @Test
    public void constructor_optionRandomString() {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, length; RESULT: value.
                        {{"-ra", "65", "100", "1"}, {"1", ""}},
                        {{"-ra", "65", "100", "2"}, {"2", ""}},
                        {{"--random", "25", "100", "10"}, {"10", ""}},
                        {{"--random", "0", "100", "11"}, {"0", "The chosen value is out of range (1..254)."}},
                        {{"--random", "-10", "100", "12"}, {"0", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\"."}},
                        {{"--random", "10", "-100", "12"}, {"0", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\"."}},
                        {{"--random", "10", "100", "-12"}, {"0", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\"."}}
                };

        for (String[][] arguments : argumentsResultArray) {
            String[] args = {arguments[0][0], arguments[0][1] + ":" + arguments[0][2] + ":" + arguments[0][3]};
            TextClip theApp = new TextClip(args);
            String actValue = theApp.createTestString();

            TestCase.assertEquals("Length", Integer.parseInt(arguments[1][0]),
                    actValue.length());

            TestCase.assertEquals("Test on error message (" + args[0] + ", " + args[1] + ")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }

    /**
     * Validates the counterstring options of textclipt (-co and --counterstring).
     */
    @Test
    public void constructor_optionText() {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, length; RESULT: value.
                        {{"-t", "65"}, {"65", ""}},
                        {{"-t", ""}, {"", ""}}
                };

        for (String[][] arguments : argumentsResultArray) {
            String[] args = {arguments[0][0], arguments[0][1]};
            TextClip theApp = new TextClip(args);
            String actValue = theApp.createTestString();

            TestCase.assertEquals("Length", Integer.parseInt(arguments[1][0]),
                    actValue.length());

            TestCase.assertEquals("Test on error message (" + args[0] + ", " + args[1] + ")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }
}
