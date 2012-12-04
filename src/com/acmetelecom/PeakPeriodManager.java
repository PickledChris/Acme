package com.acmetelecom;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

import java.util.ArrayList;
import java.util.List;

public class PeakPeriodManager implements PeakPeriodDatasource {

    private LocalTime peakPeriodStartTime = new LocalTime(7,0,0);
    private LocalTime peakPeriodEndTime = new LocalTime(19,0,0);

    @Override
    public long secondsInPeak(DateTime startTime, DateTime endTime) {
        if (startTime.toLocalTime().isBefore(peakPeriodStartTime)) {
           // Call starts before peak period
           if (endTime.toLocalTime().isBefore(peakPeriodStartTime)) {
               // Entire call is before peak period
               return 0;
           }
           Seconds secondsSincePeak = Seconds.secondsBetween(peakPeriodStartTime, endTime.toLocalTime());
           if (endTime.toLocalTime().isBefore(peakPeriodEndTime)) {
               // Call ends in peak period
               return secondsSincePeak.getSeconds();
           }
           // Call lasts entire peak period and ends after peak period
           return Seconds.secondsBetween(peakPeriodStartTime, peakPeriodEndTime).getSeconds();
        }
        if (startTime.toLocalTime().isBefore(peakPeriodEndTime)) {
            if (endTime.toLocalTime().isBefore(peakPeriodEndTime)) {
                // Entire call is in peak time
                return Seconds.secondsBetween(startTime, endTime).getSeconds();
            }
            return Seconds.secondsBetween(startTime.toLocalTime(), peakPeriodEndTime).getSeconds();
        }
        return 0;
    }
}
