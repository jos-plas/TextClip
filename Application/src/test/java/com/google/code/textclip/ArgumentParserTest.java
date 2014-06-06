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
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


/**
* GENERAL: Strive to test the public interface of the model, ignoring
* setters and getters. Tests should be arrange so that the tests
* start with functionality used by other methods.
*
*/
public class ArgumentParserTest {
    private CmdLineParser parser = null;

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
        // Read arguments in the ArgumentParser will not be evaluated. Therefore declare at method call:
        this.parser = new CmdLineParser(new ArgumentParser());
    }

    /**
     * Validate whether the command line parser object has been created successfully.
     *
     */
    @Test
    public void testInitObject() {
        TestCase.assertNotNull("Parser object is created and can be used for further testing.", this.parser);
    }

    /**
    * Validate Forbidden commandline combinations
    *
    * It makes no sense (at least for the moment) to for instance create a string (-t) and
    * have a character (-ch) for output as well. The ArgumentParser is setup to forbid a
    * these type of combinations. All forbidden combinations are tested in this testcase.
    */
    @Test (expected=CmdLineException.class)
    public void validateForbiddenCombinations() throws CmdLineException {
        final String[][] args = {
                { "-a","-ch","25"},
                { "-a","-co","30:*"},
                {"-a","-t","TEXT"},
                { "-a","-f","FILENAME"},
                { "-ch","251","-co","80:a"},
                { "-ch","252","-t","TEXT"},
                { "-ch","253","-f","FILENAME"},
                { "-co","30:#","-t","TEXT"},
                { "-co","33:!","-f","FILENAME"},
                { "-t","TEXT","-f","FILENAME"}
        };
        for (String[] arg : args) {
            parser.parseArgument(arg);
        }
    }
}
