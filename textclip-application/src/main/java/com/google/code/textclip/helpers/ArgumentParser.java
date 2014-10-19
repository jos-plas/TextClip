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


import org.kohsuke.args4j.Option;

import java.io.File;

/**
 * Argument parser class for com.google.code.textclip.
 * <p/>
 * This class configures and stores the commandline arguments. It is tailored
 * for the application that copies text to the clipboard depending on the
 * arguments provided to the application. The application is called
 * com.google.code.textclip.
 */
public class ArgumentParser {
    @Option(name = "-h", aliases = "--help", usage = "print this message")
    private boolean help = false;


    @Option(name = "-m", aliases = {"--multiply"}, metaVar = "<number>",
            usage = "multiply factor; standard value is 1")
    private long multiply = 1;


    @Option(name = "-a", aliases = {"--allchars"},
            usage = "produces a string that includes all character codes from" +
                    " 1 to 255 ",
            forbids = {"-ch", "-co", "-t", "-f", "-ra"})
    private boolean allcharacters = false;


    @Option(name = "-ch", aliases = {"--char"}, metaVar = "<ASCII value>",
            usage = "produces a character from given ASCII value; \"-ch 32\" " +
                    "will produce a space character",
            forbids = {"-a", "-co", "-t", "-f", "-ra"})
    private String ascii_value = "";


    @Option(name = "-co", aliases = {"--counterstring"},
            metaVar = "number:character",
            usage = "\"-co 25:*\" would produce \"1*3*5*7*9*11*14*17*20*23*\"" +
                    " which is a twentyfive character long string, such that " +
                    "each numeric starts at position in the string equal to " +
                    "it's number. So 11 starts at the elevens position in " +
                    "the string. This is useful for pasting into fields that " +
                    "cut off text, so that you can tell how many characters" +
                    " were actually pasted.",
            forbids = {"-ch", "-a", "-t", "-f", "-ra"})
    private String counterstring = "";


    @Option(name = "-t", aliases = {"--text"}, metaVar = "<text>",
            usage = "<text> will be copied to the clipboard (hint: use quotes \")",
            forbids = {"-ch", "co", "-a", "-f", "-ra"})
    private String text = "";


    @Option(name = "-f", aliases = {"--filename"}, metaVar = "<file>",
            usage = "this text will be copied to the clipboard",
            forbids = {"-ch", "co", "-a", "-t", "-ra"})
    private File file = null;

    /* RANDOM */
    @Option(name = "-ra", aliases = {"--random"},
            metaVar = "lower:upper:number",
            usage = "\"-ra 65:66:4\" could produce \"ABBA\". In this case the" +
                    "ASCII characters A(65) & B(66) are randomly chosen creating" +
                    "a string of 4 characters.",
            forbids = {"-co", "-ch", "-a", "-t", "-f"})
    private String random_string = "";
    /*
    |---------------------------------------------------------------------------
    | Getter functions only.
    |---------------------------------------------------------------------------
    |
    | The internal data (state) of the object is performed with the method:
    | parser.parseArguments. This means that no setters are needed. Below the
    | getter methods are provided.
    */

    /**
     * @return <code>true</code> if the user is requesting for help text
     * (-h | --help on the command line);
     * <code>false</code> otherwise
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * @return the multiply factor e.g. 10 means that the string will copied 10
     * times to the clipboard.
     */
    public long getMultiply() {
        return multiply;
    }

    /**
     * @return
     */
    public boolean isAllcharacters() {
        return allcharacters;
    }

    /**
     * @return
     */
    public boolean isAsciiValue() {
        return !ascii_value.isEmpty();
    }


    /**
     * @return
     */
    public String getAscii_value() {
        return ascii_value;
    }

    /**
     * @return
     */
    public boolean isCounterstring() {
        return !counterstring.isEmpty();
    }

    /**
     * @return
     */
    public String getCounterstring() {
        return counterstring;
    }

    /**
     * @return
     */
    public boolean isText() {
        return !text.isEmpty();
    }


    /**
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     * @return
     */
    public boolean isFile() {
        return file != null;
    }


    public boolean isRandom() {
        return !random_string.isEmpty();
    }

    public String getRandom() {
        return random_string;
    }
}