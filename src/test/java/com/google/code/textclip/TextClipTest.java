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

import com.google.code.textclip.ArgumentParser;
import com.google.code.textclip.TextClip;
import com.google.code.textclip.enums.TextClipError;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
* GENERAL: Strive to test the public interface of the model, ignoring
* setters and getters. Tests should be arrange so that the tests
* start with functionality used by other methods.
*
*/
public class TextClipTest {
    private CmdLineParser parser = null;


    /**
     * Get the String residing on the clipboard.
     *
     * @return any text found on the Clipboard; if none found, return an
     * empty String.
     *
     * @TODO: code needs to be revised for unit test purposes!!!
     */
    private String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =
                (contents != null) &&
                        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
                ;
        if (hasTransferableText) {
            try {
                result = (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException ex){
                System.out.println(ex);
                ex.printStackTrace();
            }
            catch (IOException ex){
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Methods with the annotation ’Before ’ are executed before every test.
     * The test object should be brought to the initial state all tests
     * assume it to be in.
     *
     * In case of the ArgumentParser the initialization needs to be done
     * only once. The @BeforeClass would be the best choice, but that
     * method needs to be declared static.
     */
    @Before
    public void initializeParser() {
        // Read arguments in the ArgumentParser will not be evaluated. Therefore
        // declare at method call:
        this.parser = new CmdLineParser(new ArgumentParser());
    }

    /**
     * Validate whether the command line parser object has been created
     * successfully.
     *
     */
    @Test
    public void testInitObject() {
        TestCase.assertNotNull("Parser object is created and can be used for " +
                "further testing.",this.parser);
    }

    /**
    * Validate Forbidden commandline combinations
    *
    * It makes no sense (at least for the moment) to for instance create a
    * string (-t) and have a character (-ch) for output as well. The
    * ArgumentParser is setup to forbid a these type of combinations. All
    * forbidden combinations are tested in this testcase.
    */
    @Test
    public void validateForbiddenCombinations() {
        final String[][] argumentsArray = {
                { "-a","-ch","25"},
                { "-a","-co","30:*"},
                { "-a","-t","TEXT"},
                { "-a","-f","FILENAME"},
                { "-ch","251","-co","80:a"},
                { "-ch","252","-t","TEXT"},
                { "-ch","253","-f","FILENAME"},
                { "-co","30:#","-t","TEXT"},
                { "-co","33:!","-f","FILENAME"},
                { "-t","TEXT","-f","FILENAME"}
        };

        String buffer = "";
        for (String[] arguments : argumentsArray) {
            TextClip theTestApp = new TextClip(arguments);
            buffer="";
            for (String argument: arguments) {
                buffer += String.format("%s ",argument);
            }
            TestCase.assertEquals("Tested arguments: \""+buffer+"\"",
                    theTestApp.getStatus(),
                    TextClipError.ERROR_PARSING_ARGUMENTS);

        }
    }

    /**
     * Validate the same commandline option twice.
     *
     * Args4j has a problem using the same commandline option twice. This test
     * is ony present to investigate this arg4j issue.
     */
    @Test
    @Ignore ("problem with args4j and same commandline option twice.")
    public void validateTheSameArgumentTwice(){
        final String[][] argumentsArray = {
                { "-a","-a"},
                { "-ch","251","-ch","251"},
                { "-co","30:#","-co","30:#"},
                { "-t","TEXT","-t","TEXT"},
                { "-f","FILENAME","-f","FILENAME"}
        };

        for (String[] arguments : argumentsArray) {
            TextClip theTestApp = new TextClip(arguments);
            String buffer = "";
            for (String argument: arguments) {
                buffer += String.format("%s ",argument);
            }
            TestCase.assertEquals("Tested arguments: \""+buffer+"\"",
                    theTestApp.getStatus(),
                    TextClipError.NO_ERROR);

        }
    }


    /**
     * Validates the all characters options op textclipt (-a and --allchars).
     */
    @Test
    public void optionAllCharacters() throws IOException {
        final String[][] argumentsArray =
                {
                        { "-a"},
                        {"--allchars"}
                };

        for (String[] arguments:argumentsArray) {
            TextClip theApp = new TextClip(arguments);
            theApp.createTestString();
            TestCase.assertEquals("All characters ("+ arguments[0] +")",
                    254,
                    theApp.getTestString().length());

            TestCase.assertEquals("Error message on All characters ("+ arguments[0] +")",
                    "",
                    theApp.getStatus().getMessage());
        }
    }


    /**
     * Validates the character options of textclipt (-ch and --character).
     */
    @Test
    public void optionCharacter() throws IOException {
        final String[][][] argumentsResultArray =
                {           // Format of argumentsResultArray
                            // INPUT: argument, value; RESULT: value, error message.
                        { { "-ch","-1"}, {"",""}},
                        { { "-ch","0"}, {"",""}},
                        { {"-ch","65"}, {"A",""} },
                        { {"--char","65"},{"A",""} },
                        { { "-ch","254"}, {"\u00fe",""}},
                        { { "-ch","255"}, {"",""}}
                };

        for (String[][] arguments:argumentsResultArray) {
            TextClip theApp = new TextClip(arguments[0]);
            theApp.createTestString();
            TestCase.assertEquals("Test on value ("+ arguments[0][0]+", "+arguments[0][1] +")",
                    arguments[1][0],
                    theApp.getTestString());
            TestCase.assertEquals("Test on error message ("+ arguments[0][0]+", "+arguments[0][1] +")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }

    /**
     * Validates the character options of textclipt (-f and --filename).
     */
    @Test
    public void optionFilename() {
        final String[][][] argumentsResultArray =
                {       // Format of argumentsResultArray
                        // INPUT: argument, value; RESULT: value, error message.
                        { { "-f","src/test/resources/textgenerator/file_has_no_content.txt"}, {"",""}},
                        { { "-f","src/test/resources/textgenerator/file_has_content.txt"}, {"This is a test file with a little content.",""}},
                        { { "-f","file_does_not_exist.txt"}, {"",""}}
                };

        for (String[][] arguments:argumentsResultArray) {
            TextClip theApp = new TextClip(arguments[0]);
            theApp.createTestString();
            TestCase.assertEquals("Test on value ("+ arguments[0][0]+", "+arguments[0][1] +")",
                    arguments[1][0],
                    theApp.getTestString());
            TestCase.assertEquals("Test on error message (" + arguments[0][0] + ", " + arguments[0][1] + ")",
                    arguments[1][1],
                    theApp.getStatus().getMessage());
        }
    }
}
