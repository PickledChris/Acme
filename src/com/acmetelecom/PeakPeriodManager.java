package com.acmetelecom;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

public class PeakPeriodManager implements PeakPeriodDatasource {

    private LocalTime peakPeriodStartTime = new LocalTime(7,0,0);
    private LocalTime peakPeriodEndTime = new LocalTime(19,0,0);

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
            return Seconds.secondsBetween(startTime, peakEnd).getSeconds();
        }
        return 0;
    }
}
