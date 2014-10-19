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


import com.google.code.textclip.exceptions.OutOfRangeException;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class ASCIIDataContainerTest {

    /**
     * validates the lower limits with a negative value (-10).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasNegativeValue3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(-10,100,1);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasZeroValue3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(0,100,1);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitLargerThanUpperLimit3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(101,100,1);
    }


    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitOverMax3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(255,10,1);
    }


    /**
     * validates the lower limits with a negative value).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasNegativeValue3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(10,-100,1);
    }

    /**
     * validates the upper limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasZeroValue3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(100,0,1);
    }

    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitOverMax3Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(101,255,1);
    }

    /**
     * validates a negative length value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_NegativeLength_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /*
        ARRANGE & ACT
         */
        new ASCIIDataContainer(100,101,-1);
    }

    /**
     * validates a negative length value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_ZeroLength_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /*
        ARRANGE & ACT
         */
        new ASCIIDataContainer(100,101,0);
    }


    /**
     * validates the lower limits with a negative value (-10).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasNegativeValue2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(-10,100);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitHasZeroValue2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(0,100);
    }

    /**
     * validates the lower limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitLargerThanUpperLimit2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(101,100);
    }


    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_LowerLimitOverMax2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(255,10);
    }


    /**
     * validates the lower limits with a negative value).
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasNegativeValue2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(10,-100);
    }

    /**
     * validates the upper limits with a zero value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitHasZeroValue2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(100,0);
    }

    /**
     * validates the upper limits exceeds the maximum value.
     */
    @Test(expected = OutOfRangeException.class)
    public void constructor_UpperLimitOverMax2Args_OutOfRangeException()
            throws OutOfRangeException, IOException {
        /* ARRANGE, ACT & ASSERT */
        new ASCIIDataContainer(101,255);
    }

    
    /**
     * validates a Happy flow for three arguments.
     */
    @Test
    public void constructor_HappyFlow3Args()
            throws OutOfRangeException, IOException {
        /*
        ARRANGE & ACT
         */
        ASCIIDataContainer container  = new ASCIIDataContainer(1,254,2);
        int expectedLowerLimit = container.getLowerLimit();
        int expectedUpperLimit = container.getUpperLimit();
        int expectedLength = container.getLength();

        TestCase.assertTrue("Lower limit",1 == expectedLowerLimit);
        TestCase.assertTrue("Upper limit",254 == expectedUpperLimit);
        TestCase.assertTrue("Length",2 == expectedLength);
    }

    /**
     * validates a Happy flow for two arguments.
     */
    @Test
    public void constructor_HappyFlow2Args()
            throws OutOfRangeException, IOException {
        /*
        ARRANGE & ACT
         */
        ASCIIDataContainer container  = new ASCIIDataContainer(99,100);
        int expectedLowerLimit = container.getLowerLimit();
        int expectedUpperLimit = container.getUpperLimit();
        int expectedLength = container.getLength();

        TestCase.assertTrue("Lower limit",99 == expectedLowerLimit);
        TestCase.assertTrue("Upper limit",100 == expectedUpperLimit);
        TestCase.assertTrue("Length",2 == expectedLength);
    }


}
