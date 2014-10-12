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
package com.google.code.textclip.exceptions;

import com.google.code.textclip.testhelpers.TestFileHelper;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class FileSizeExceptionTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private TestFileHelper fHelper;

    public FileSizeExceptionTest() {
        this.fHelper = new TestFileHelper(this.testFolder);
    }

    /**
     * Validates whether exception with empty file contains text "empty".
     */
    @Test
    public void toString_FileEmpty_MessageContainsEmpty() throws IOException, FileSizeException {
        /*
        ARRANGE
        */
        File theFile = fHelper.createFile("");

        /*
        ACT
        */
        FileSizeException exception = new FileSizeException(theFile);

        /*
        ASSERT
         */
        TestCase.assertTrue("File is empty; therefor empty message",
                exception.toString().toLowerCase().contains("empty"));
    }

    /**
     * Validates whether exception with empty file contains text "Too Large".
     */
    @Test
    public void toString_FileNotEmpty_MessageContainsTooLarge() throws IOException, FileSizeException {
        /*
        ARRANGE
        */
        File theFile = fHelper.createFile("Don't care.");

        /*
        ACT
        */
        FileSizeException exception = new FileSizeException(theFile);

        /*
        ASSERT
         */
        TestCase.assertTrue("File contains text; therefor too large message",
                exception.toString().toLowerCase().contains("too large"));


    }

}
