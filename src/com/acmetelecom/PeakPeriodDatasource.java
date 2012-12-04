package com.acmetelecom;

import org.joda.time.DateTime;

public interface PeakPeriodDatasource {

    long secondsInPeak(DateTime startTime, DateTime endTime);

}
