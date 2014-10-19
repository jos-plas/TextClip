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
package com.google.code.textclip.enums;

import junit.framework.TestCase;
import org.junit.Test;


/**
 * GENERAL: Strive to test at least the public interface of the model, ignoring
 * setters and getters. Tests should be arrange so that the tests
 * start with functionality used by other methods.
 */
public class TextClipErrorTest {

    @Test
    public void contructor_NO_ERROR() throws Exception {
        TextClipError status = TextClipError.NO_ERROR;
        TestCase.assertSame("NO_ERROR", "", status.getMessage());
        TestCase.assertSame("NO_ERROR integer", 0, status.toInt());
    }

    @Test
    public void contructor_ERROR_PARSING_ARGUMENTS() throws Exception {
        TextClipError status = TextClipError.ERROR_PARSING_ARGUMENTS;
        TestCase.assertSame("ERROR_PARSING_ARGUMENTS", "There is a mistake in the arguments; use --help for detailed argument information.", status.getMessage());
        TestCase.assertSame("ERROR_PARSING_ARGUMENTS integer", 1, status.toInt());
    }

    @Test
    public void contructor_ERROR_PARSING_ARGUMENT_ASCII_INT_VALUE_OUT_OF_RANGE() throws Exception {
        TextClipError status = TextClipError.ERROR_PARSING_ARGUMENT_ASCII_INT_VALUE_OUT_OF_RANGE;
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_ASCII_INT_VALUE_OUT_OF_RANGE", "The chosen value is out of range (1..254).", status.getMessage());
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_ASCII_INT_VALUE_OUT_OF_RANGE integer", 2, status.toInt());
    }

    @Test
    public void contructor_ERROR_PARSING_ARGUMENT_WRONG_FORMAT() throws Exception {
        TextClipError status = TextClipError.ERROR_PARSING_ARGUMENT_WRONG_FORMAT;
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_WRONG_FORMAT", "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\".", status.getMessage());
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_WRONG_FORMAT integer", 3, status.toInt());
    }

    @Test
    public void contructor_ERROR_PARSING_ARGUMENT_FILESIZE() throws Exception {
        TextClipError status = TextClipError.ERROR_FILESIZE;
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_FILESIZE", "Chosen file is unreadable, has a filesize of zero or is too large.", status.getMessage());
        TestCase.assertSame("ERROR_PARSING_ARGUMENT_FILESIZE integer", 4, status.toInt());
    }
}
