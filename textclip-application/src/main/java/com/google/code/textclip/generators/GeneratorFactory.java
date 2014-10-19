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
import com.google.code.textclip.helpers.ASCIIStringArgumentParser;
import com.google.code.textclip.helpers.ArgumentParser;
import com.google.code.textclip.helpers.CounterStringArgumentParser;

import java.io.FileNotFoundException;

public class GeneratorFactory {
    /**
     *
     */
    public GeneratorProduct make(ArgumentParser options)
            throws OutOfRangeException, FileNotFoundException, FileSizeException, FormatException, InvalidGeneratorProductException {
        GeneratorProduct generatorProduct;
        if (options.isAllcharacters()) {
            generatorProduct = new AsciiGeneratorProduct();
        } else if (options.isAsciiValue()) {
            generatorProduct = createASCIIProductFromInputString(options);
        } else if (options.isFile()) {
            generatorProduct = new FromFileGeneratorProduct(options.getFile());
        } else if (options.isCounterstring()) {
            generatorProduct = createCounterStringFromInputString(options);
        } else {
            throw new InvalidGeneratorProductException("Specify correct option");
        }
        return generatorProduct;
    }

    private GeneratorProduct createCounterStringFromInputString(ArgumentParser options) throws FormatException, OutOfRangeException {
        CounterStringArgumentParser csar = new CounterStringArgumentParser(options.getCounterstring());
        return new CounterStringGeneratorProduct(csar.getCharacter(), csar.getLength());
    }

    private GeneratorProduct createASCIIProductFromInputString(ArgumentParser options) throws FormatException, OutOfRangeException {
        ASCIIStringArgumentParser asap = new ASCIIStringArgumentParser(options.getAscii_value());
        return  new AsciiGeneratorProduct(asap.getLowerLimit(),asap.getUpperLimit());
    }


}
