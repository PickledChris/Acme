package com.acmetelecom.billcalculator.peak;

import org.joda.time.LocalTime;

class DaytimePeakPeriod {

    private LocalTime beginTime;
    private LocalTime endTime;

    public DaytimePeakPeriod(LocalTime beginTime, LocalTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean offPeak(LocalTime time) {
        return !(time.isAfter(beginTime) && time.isBefore(endTime));
    }
}
