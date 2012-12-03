package com.acmetelecom;

import org.joda.time.LocalTime;

public interface PeakPeriodDatasource {

    void addPeakPeriod(LocalTime beginTime, LocalTime endTime);

    boolean offPeak(LocalTime time);

    void removeAll();
}
