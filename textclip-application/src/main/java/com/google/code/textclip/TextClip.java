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
import com.google.code.textclip.generators.GeneratorFactory;
import com.google.code.textclip.helpers.ArgumentParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;

public class TextClip implements ClipboardOwner {
    private final ArgumentParser options = new ArgumentParser();
    private final CmdLineParser parser = new CmdLineParser(options);
    private TextClipError status = TextClipError.NO_ERROR;
    private GeneratorFactory factory = new GeneratorFactory();


    /**
     * @param argumentList provide here the argument list like a string array:
     *                     {@code {"-t","textfile.txt","-m","10"}. This
     *                     indicates that the content of "textfile.txt" will be
     *                     concatenated 10 times and put in the systems
     *                     clipboard.
     */
    public TextClip(String[] argumentList) {
        try {
            parser.parseArgument(argumentList);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            this.status = TextClipError.ERROR_PARSING_ARGUMENTS;
        }
    }

    /**
     * @return the status object of the class. For instance the status can
     * change due to errors in the argument list provided by the
     * user (see constructor). When a user enters an unknown
     * argument the status will change to
     * ERROR_UNKNOWN_ARGUMENT_PROVIDED.
     */
    public TextClipError getStatus() {
        return status;
    }


    /**
     * Place the test string on the clipboard, and generate this class the
     * owner of the Clipboard's contents.
     */
    public void toClipboard(String theString) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(theString), this);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        //Empty implementation of the ClipboardOwner interface. -> do nothing
    }


    /**
     * this method delegates the actual work. It processes the read arguments and
     * constructs the test string. When the test string is constructed it is
     * copied to the clipboard.
     */
    public void textToClipboard() throws IOException, OutOfRangeException, FormatException, FileSizeException, InvalidGeneratorProductException {
        if (!this.options.isHelp()) {
            this.toClipboard(this.factory.make(this.options).generate());
        } else {
            parser.printUsage(System.out);
            System.out.println();
        }
    }


    /**
     * The TextClip class implements an application that reads the terminal
     * commandline options. Depending on the arguments text will be copied to
     * the Operating Systems clipboard.
     * <p/>
     * The application terminates the currently running Java Virtual Machine
     * with a status code. By convention, a nonzero status code indicates
     * abnormal termination.
     *
     * @param args the argument list from the terminal commandline. The
     *             arguments are parsed with the ArgumentParser class.
     */
    public static void main(String[] args) throws IOException, OutOfRangeException, FormatException, FileSizeException, InvalidGeneratorProductException {
        final TextClip theApp = new TextClip(args);
        theApp.textToClipboard();
        final String message = (theApp.getStatus() == TextClipError.NO_ERROR)
                ? "Test string is copied to the clipboard."
                : theApp.getStatus().getMessage();
        System.out.println(message);
        System.exit(theApp.getStatus().toInt());
    }
}