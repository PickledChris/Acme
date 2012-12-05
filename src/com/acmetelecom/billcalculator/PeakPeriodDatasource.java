package com.acmetelecom.billcalculator;

import org.joda.time.DateTime;

public interface PeakPeriodDatasource {

    long secondsInPeak(DateTime startTime, DateTime endTime);

}
