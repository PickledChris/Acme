package com.acmetelecom;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class PeakPeriodManager {

    private List<DaytimePeakPeriod> peakPeriods;

    public PeakPeriodManager() {
        this.peakPeriods = new ArrayList<DaytimePeakPeriod>();
    }

    public void addPeakPeriod(LocalTime beginTime, LocalTime endTime) {
        this.peakPeriods.add(new DaytimePeakPeriod(beginTime, endTime));
    }

    public boolean offPeak(LocalTime time) {
        LocalTime localTime = new LocalTime(time);
        boolean isOffPeak = false;
        for (DaytimePeakPeriod peakPeriod : peakPeriods) {
            isOffPeak = isOffPeak || peakPeriod.offPeak(localTime);
        }
        return isOffPeak;
    }
}
