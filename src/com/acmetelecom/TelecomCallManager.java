package com.acmetelecom;

import java.util.Collection;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public interface TelecomCallManager {

    /**
     * Register a call from caller to callee starting at the current system time.
     * @param caller telephone number of customer placing the call
     * @param callee telephone number the call is being made to
     */
    public void callInitiatedNow(String caller, String callee);

    /**
     * Register a call from caller to callee starting at a specific time.
     * @param caller telephone number of customer placing the call
     * @param callee telephone number the call is being made to
     * @param time time at which the call was started
     */
    public void callInitiatedAt(String caller, String callee, long time);

    /**
     * End an existing call from caller to callee at the current system time.
     * @param caller telephone number of customer who placed the call
     * @param callee telephone number the call is being made to
     */
    public void callCompletedNow(String caller, String callee);

    /**
     * End an existing call from caller to callee at a given time.
     * @param caller telephone number of customer who placed the call
     * @param callee telephone number the call is being made to
     * @param time time at which the call ended
     */
    public void callCompletedAt(String caller, String callee, long time);

    /**
     * Get all calls made by a customer with a given number.
     * @param caller telephone number of the customer to get calls for
     * @return Collection of Call objects representing calls made by
     *         the given customer.
     */
    public Collection<Call> getCallsFor(String caller);

    /**
     * Clear all calls from the call log.
     */
    public void clearLog();
}
