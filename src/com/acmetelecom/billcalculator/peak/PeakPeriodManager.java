package com.acmetelecom.billcalculator.peak;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

import com.acmetelecom.billcalculator.PeakPeriodDatasource;

/**
 * Class to store information about peak period times. Provides a
 * method to calculate how much of a call occurs in a peak period.
 */

public class PeakPeriodManager implements PeakPeriodDatasource {

    private final LocalTime peakPeriodStartTime = new LocalTime(7,0,0);
    private final LocalTime peakPeriodEndTime = new LocalTime(19,0,0);

    @Override
    public long secondsInPeak(DateTime startTime, DateTime endTime) {
        DateTime peakStart = peakPeriodStartTime.toDateTime(startTime);
        DateTime peakEnd = peakPeriodEndTime.toDateTime(startTime);
        if (startTime.isBefore(peakStart)) {
           // Call starts before peak period
           if (endTime.isBefore(peakStart)) {
               // Entire call is before peak period
               return 0;
           }
           Seconds secondsSincePeak = Seconds.secondsBetween(peakStart, endTime);
           if (endTime.isBefore(peakEnd)) {
               // Call ends in peak period
               return secondsSincePeak.getSeconds();
           }
           // Call lasts entire peak period and ends after peak period
           return Seconds.secondsBetween(peakStart, peakEnd).getSeconds();
        }
        if (startTime.isBefore(peakEnd)) {
            if (endTime.isBefore(peakEnd)) {
                // Entire call is in peak time
                return Seconds.secondsBetween(startTime, endTime).getSeconds();
            }
            // Part of the call is in peak time
            return Seconds.secondsBetween(startTime, peakEnd).getSeconds();
        }
        // No part of the call is in peak time
        return 0;
    }
}
