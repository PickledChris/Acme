package com.acmetelecom;

import org.joda.time.LocalTime;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Tests for the DaytimePeakPeriod class
 */
public class DaytimePeakPeriodTests {

    // 7:00
    private LocalTime peakPeriodStart = new LocalTime(7,0);
    // 19:00
    private LocalTime peakPeriodEnd = new LocalTime(19,0);
    private PeakPeriodManager peakPeriodManager;

    @BeforeTest
    public void setupPeakPeriod() {
        this.peakPeriodManager = new PeakPeriodManager();
        this.peakPeriodManager.addPeakPeriod(peakPeriodStart, peakPeriodEnd);
    }


    @Test
    public void offPeakEveningTest() {

        // 19:00:01
        LocalTime offPeakEvening = new LocalTime(19,0,1);
        boolean result = this.peakPeriodManager.offPeak(offPeakEvening);
        assertTrue("The time " + offPeakEvening +
                " was not declared off peak when it should have", result);
    }


    @Test
    public void onPeakEveningTest() {

        // 18:59:59
        LocalTime peakEvening = new LocalTime(18,59,59);
        boolean result = this.peakPeriodManager.offPeak(peakEvening);
        assertFalse("The time " + peakEvening +
                " was declared off peak when it should have been on peak.", result);

    }

    @Test
    public void onPeakMorningTest() {

        // 07:00:01
        LocalTime peakMorning = new LocalTime(7,0,1);
        boolean result = this.peakPeriodManager.offPeak(peakMorning);
        assertFalse("The time " + peakMorning +
                " was declared off peak when it should have been on peak.", result);
    }

    @Test
    public void offPeakMorningTest() {

        // 06:59:59
        LocalTime offPeakMorning = new LocalTime(6,59,59);
        boolean result = this.peakPeriodManager.offPeak(offPeakMorning);
        assertTrue("The time " + offPeakMorning +
                "was not declared off peak when it should have", result);
    }

}
