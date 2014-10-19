package com.google.code.textclip.generators;

import com.google.code.textclip.exceptions.OutOfRangeException;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

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
public class RandomGeneratorProductTest {
    /**
     * GENERAL: Strive to test the public interface of the model, ignoring
     * setters and getters. Tests should be arranged so that the tests
     * start with functionality used by other methods.
     *
     */

    /**
     * validates the lower limits with a negative value (-10).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasNegativeValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(-10,100,1);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasZeroValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(0,100,1);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitLargerThanUpperLimit_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(101,100,1);
    }


    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitOverMax_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(255,10,1);
    }


    /**
     * validates the lower limits with a negative value).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasNegativeValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(10,-100,1);
    }

    /**
     * validates the upper limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasZeroValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(100,0,1);
    }

    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitOverMax_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(101,255,1);
    }

    /**
     * validates the length argument with a negative value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LengthNegativeValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(101,255,-1000);
    }


    /**
     * validates the length argument with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LengthZeroValue_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(101,255,0);
    }

    /**
     * validates the length argument with a negative value (1 argument constructor).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LengthNegativeValue1Arg_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(-999);
    }

    /**
     * validates the length argument with a zero value (1 argument constructor).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LengthZeroValue1Arg_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new RandomGeneratorProduct(0);
    }


    /**
     * validates the length argument with a zero value (1 argument constructor).
     */
    @Test
    public void generate_happyFlow1Arg()
            throws OutOfRangeException, IOException {
        /* ARRANGE*/
        final int length = 10000000;
        RandomGeneratorProduct product  = new RandomGeneratorProduct(length);

        /*
        ACT
         */
        String expValue1 = product.generate();
        String expValue2 = product.generate();

        /* ASSERT */
        TestCase.assertTrue("Expected value 1 is not null", expValue1 != null);
        TestCase.assertTrue("Expected value 2 is not null", expValue2 != null);

        TestCase.assertTrue("Generated strings are not equal",
                                           !expValue1.contentEquals(expValue2));

        TestCase.assertTrue("Generated string length",
                                                  length == expValue1.length());
    }


    /**
     * validates the length argument with a zero value (3 argument constructor).
     */
    @Test
    public void generate_happyFlow3Arg()
            throws OutOfRangeException, IOException {
        /* ARRANGE*/
        final int lower_limit = 20;
        final int upper_limit = 100;
        final int length = 10000000;

        RandomGeneratorProduct product  = new RandomGeneratorProduct
                                               (lower_limit,upper_limit,length);

        /*
        ACT
         */
        String expValue1 = product.generate();
        String expValue2 = product.generate();

        /* ASSERT */
        TestCase.assertTrue("Expected value 1 is not null", expValue1!=null);
        TestCase.assertTrue("Expected value 2 is not null", expValue2!=null);

        TestCase.assertTrue("Generated strings are not equal",
                                           !expValue1.contentEquals(expValue2));

        TestCase.assertTrue("Generated string length",
                                                  length == expValue1.length());

        doesContentMeetLimitSettings(upper_limit, lower_limit, expValue1);
        doesContentMeetLimitSettings(upper_limit, lower_limit, expValue2);
    }

    private void doesContentMeetLimitSettings(final int upper_limit,
                                              final int lower_limit,
                                              final String expValue) {
        for (int i = 0 ; i < expValue.length(); i++) {
            TestCase.assertTrue("lower limit character value",
                                             expValue.charAt(i) >= lower_limit);
            TestCase.assertTrue("upper limit character value",
                                             expValue.charAt(i) <= upper_limit);
        }
    }
}
