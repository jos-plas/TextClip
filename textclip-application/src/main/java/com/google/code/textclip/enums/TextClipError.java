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


public enum TextClipError {
    NO_ERROR(0),
    ERROR_PARSING_ARGUMENTS(1),
    ERROR_PARSING_ARGUMENT_ASCII_INT_VALUE_OUT_OF_RANGE(2),
    ERROR_PARSING_ARGUMENT_WRONG_FORMAT(3),
    ERROR_FILESIZE(4);

    private final int error;

    /**
     * @param theError value is one of the error values in the enumeration.
     */
    TextClipError(int theError) {
        this.error = theError;
    }

    /**
     * @return
     */
    public int toInt() {
        return this.error;
    }

    public String getMessage() {
        String result = ""; // NO_ERROR
        switch (error) {
            case 1:
                result = "There is a mistake in the arguments; use --help for detailed argument information.";
                break;

            case 2:
                result = "The chosen value is out of range (1..254).";
                break;

            case 3:
                result = "Incorrect format e.g. counterstring: -co \"10:a\" or random: -ra \"10:20:30\".";
                break;

            case 4:
                result = "Chosen file is unreadable, has a filesize of zero or is too large.";
                break;

            case 0:
            default:
                result = "";
                break;
        }
        return result;
    }
}
