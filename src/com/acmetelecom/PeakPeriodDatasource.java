package com.acmetelecom;

import org.joda.time.DateTime;

public interface PeakPeriodDatasource {

    /**
     * Returns the number of seconds between two times which fall in
     * a peak period.
     *
     * @param startTime DateTime representing the time at which the call started
     * @param endTime DateTime representing the time at which the call ended
     * @return the number of seconds between startTime and endTime which
     *         were in peak time
     */
    long secondsInPeak(DateTime startTime, DateTime endTime);

}
