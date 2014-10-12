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
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

public class FromFileGeneratorProductTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String content = "This file has a little content";

    /**
     * Validates whether an exception is thrown if the input file does not exist.
     */
    @Test(expected = FileNotFoundException.class)
    public void constructor_FileDoesNotExist_FileNotFoundException()
            throws IOException, FileSizeException {
        /*
        ARRANGE
         */
        File theFile = new File("does_not_exist.txt");
        /*
        ACT & ASSERT
         */
        new FromFileGeneratorProduct(theFile);
    }


    /**
     * Validates whether test string can be constructed from the content of a
     * file.
     */
    @Test
    public void generateFromFile()
            throws IOException, FileSizeException {
        /*
        ARRANGE
         */
        File theTestFile = createFile(content);
        GeneratorProduct theProduct = new FromFileGeneratorProduct(theTestFile);


        /*
        ACT
         */
        String theProducedString = theProduct.generate();

        /*
        ASSERT
         */

        TestCase.assertEquals("File has content", content, theProducedString);
    }


    /**
     * Validates whether test string can be constructed from an empty file.
     */
    @Test(expected = FileSizeException.class)
    public void constructor_FileHasNoContent_FileContentException()
            throws IOException, FileSizeException {
        File theTestFile = createFile("");

        TestCase.assertEquals("File has no content",
                "Specified file (file_has_no_content.txt) has no content" +
                        " or does not exist.",
                new FromFileGeneratorProduct(theTestFile).generate());
    }

    /**
     * Validates the file size exception.
     */
    @Test(expected = FileSizeException.class)
    public void constructor_FileSize_FileContentException()
            throws IOException, FileSizeException {
        /*
        ARRANGE
         */
        File theTestFile = createFile(content);
        GeneratorProduct theProduct = new FromFileGeneratorProduct(theTestFile, 3);

        /*
            ACT & ASSERT
         */
        theProduct.generate();
    }

    /**
     * Validates whether a problem with e.g. missing file races an exception.
     */
    @Test(expected = IOException.class)
    public void generate_MissingFile_IOException()
            throws IOException, FileSizeException {
        /*
        ARRANGE
         */
        File theTestFile = createFile(content);
        GeneratorProduct theProduct = new FromFileGeneratorProduct(theTestFile);

        /*
            ACT & ASSERT
         */
        TestCase.assertTrue("Temporary file has been deleted",theTestFile.delete());
        theProduct.generate();
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
