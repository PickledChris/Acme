package com.acmetelecom;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class PeakPeriodManager implements PeakPeriodDatasource {

    private List<DaytimePeakPeriod> peakPeriods;

    public PeakPeriodManager() {
        this.peakPeriods = new ArrayList<DaytimePeakPeriod>();
    }

    @Override
    public void addPeakPeriod(LocalTime beginTime, LocalTime endTime) {
        this.peakPeriods.add(new DaytimePeakPeriod(beginTime, endTime));
    }

    @Override
    public boolean offPeak(LocalTime time) {
        LocalTime localTime = new LocalTime(time);
        boolean isOffPeak = false;
        for (DaytimePeakPeriod peakPeriod : peakPeriods) {
            isOffPeak = isOffPeak || peakPeriod.offPeak(localTime);
        }
        return isOffPeak;
    }
    
    public int amountOfTimeOffpeak(LocalTime startTime, LocalTime endTime) {
    	for (DaytimePeakPeriod peakPeriod : peakPeriods) {
    		//if (startTime.isBefore(peakPeriod.))
    	}
    	return 0;
    }

    @Override
    public void removeAll() {
        this.peakPeriods.clear();
    }
}
