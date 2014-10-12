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

import com.google.code.textclip.exceptions.FileSizeException;
import com.google.code.textclip.exceptions.FormatException;
import com.google.code.textclip.exceptions.InvalidGeneratorProductException;
import com.google.code.textclip.exceptions.OutOfRangeException;
import com.google.code.textclip.helpers.ArgumentParser;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class GeneratorFactoryTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    /**
     * Validates whether option character creates the correct product. Be aware do not
     * test argument parser etc.
     */
    @Test
    public void make_withOptionCH65()
            throws IOException, FileSizeException, OutOfRangeException, FormatException, CmdLineException, InvalidGeneratorProductException {

        /*
        ARRANGE
         */
        final ArgumentParser options = new ArgumentParser();
        final CmdLineParser parser = new CmdLineParser(options);

        String[] testData = {"-ch", "65"};
        parser.parseArgument(testData);

        /*
        ACT
         */
        GeneratorProduct product = new GeneratorFactory().make(options);
        String generatedString = product.generate();

        /*
        ASSERT
        */
        TestCase.assertEquals("Generated character (String)", "A", generatedString);

    }

    /**
     * Validates whether option all characters creates the correct product.
     */
    @Test
    public void make_withOptionA()
            throws IOException, FileSizeException, OutOfRangeException, FormatException, CmdLineException, InvalidGeneratorProductException {

        /*
        ARRANGE
         */
        final ArgumentParser options = new ArgumentParser();
        final CmdLineParser parser = new CmdLineParser(options);

        String[] testData = {"-a"};
        parser.parseArgument(testData);

        /*
        ACT
         */
        GeneratorProduct product = new GeneratorFactory().make(options);
        String generatedString = product.generate();

        /*
        ASSERT
        */
        TestCase.assertEquals("Generated character (String)", 254, generatedString.length());

    }

    /**
     * Validates whether option counterstring creates the correct product.
     */
    @Test
    public void make_withOptionCounterString()
            throws IOException, FileSizeException, OutOfRangeException, FormatException, CmdLineException, InvalidGeneratorProductException {

        /*
        ARRANGE
         */
        final ArgumentParser options = new ArgumentParser();
        final CmdLineParser parser = new CmdLineParser(options);

        String[] testData = {"-co", "25:d"};

        parser.parseArgument(testData);

        /*
        ACT
         */
        GeneratorProduct product = new GeneratorFactory().make(options);
        String generatedString = product.generate();

        /*
        ASSERT
        */
        TestCase.assertEquals("Generated counter string", "d2d4d6d8d10d13d16d19d22dd", generatedString);

    }

    /**
     * Validates whether option counterstring creates the correct product.
     */
    @Test
    public void make_withOptionFromFile()
            throws IOException, FileSizeException, OutOfRangeException, FormatException, CmdLineException, InvalidGeneratorProductException {

        /*
        ARRANGE
         */
        final ArgumentParser options = new ArgumentParser();
        final CmdLineParser parser = new CmdLineParser(options);

        final String content = "This is the content";
        File theFile = createFile(content);

        String[] testData = {"-f", theFile.getAbsolutePath()};
        parser.parseArgument(testData);

        /*
        ACT
         */
        GeneratorProduct product = new GeneratorFactory().make(options);
        String generatedString = product.generate();

        /*
        ASSERT
        */
        TestCase.assertEquals("Generated String from file", content, generatedString);

    }


    /**
     * Validates whether invalid option throws the correct Exception.
     */
    @Test(expected = InvalidGeneratorProductException.class)
    public void make_withIncorrectOption_InvalidGeneratorProductException()
            throws CmdLineException, IOException, FileSizeException, OutOfRangeException, FormatException, InvalidGeneratorProductException {

        /*
        ARRANGE
         */
        final ArgumentParser options = new ArgumentParser();
        final CmdLineParser parser = new CmdLineParser(options);

        String[] testData = {"-h"};
        parser.parseArgument(testData);

        /*
        ACT and ASSERT (should throw an exception).
         */
        new GeneratorFactory().make(options);


    }

    private File createFile(String testContent) throws IOException {
        final String fileName = "file.txt";
        File theTestFile = testFolder.newFile(fileName);

        if (!testContent.isEmpty()) {
            BufferedWriter output = new BufferedWriter(new FileWriter(theTestFile));
            output.write(testContent);
            output.close();
        }
        return theTestFile;
    }


}
