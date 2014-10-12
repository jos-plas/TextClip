package com.google.code.textclip.generators;

import com.google.code.textclip.exceptions.FileSizeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Generates a string with one ASCII character indicated by character_value.
 * <p/>
 * Example:
 * .generateASCIIChar(65) returns string "A".
 *
 * @param character_value integer character value in the range from 1 up
 *                        to 254.
 * @return generated one ascii character string
 */
public class FromFileGeneratorProduct implements GeneratorProduct {
    private File fromFile;

    public FromFileGeneratorProduct(final File theFile, int size)
            throws FileNotFoundException, FileSizeException {
        if (!theFile.exists()) {
            throw new FileNotFoundException("Could not find file (" + theFile.getName() + ").");
        }

        /*
         * The file exists; proceed testing the size (0 or >=2^31)
         */
        if ((theFile.length() == 0) || (theFile.length() >= size)) {
            throw new FileSizeException(theFile);
        }

        this.fromFile = theFile;
    }

    public FromFileGeneratorProduct(final File theFile)
            throws FileNotFoundException, FileSizeException {
        this(theFile, (int) Math.pow(2, 31));
    }


    @Override
    public String generate() throws IOException {
        FileInputStream fis = new FileInputStream(this.fromFile.toString());
        byte[] buffer = new byte[(int) this.fromFile.length()];
        fis.read(buffer);
        fis.close();
        return new String(buffer);
    }
}
