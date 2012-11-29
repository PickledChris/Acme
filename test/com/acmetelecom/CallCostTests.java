package com.acmetelecom;

import org.testng.annotations.Test;

/**
 * User: Andy
 * Date: 29/11/12
 */
public class CallCostTests {

    @Test
     public void testOffPeakCallIsChargedCorrectly() {

    }

    @Test
    public void testPeakCallIsChargedCorrectly() {

    }

    @Test
    public void testOffPeakToPeakCallIsChargedCorrectly() {

    }

    @Test
    public void testThirteenHourCallChargedCorrectRate() {
        // Test that a call lasting 13 hours is charged correctly for peak time
    }

    @Test
     public void testSimpleMultipleCallsChargedCorrectly() {
         // Test customers making several peak or several off peak calls are
         // correctly charged
    }

    @Test
    public void testComplexMultipleCallsChargedCorrectly() {
        // Test that customers making several peak and off peak calls in one session
        // are charged correctly
    }
}
