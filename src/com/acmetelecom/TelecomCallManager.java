package com.acmetelecom;

import java.util.Collection;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public interface TelecomCallManager {

    public void callInitiatedNow(String caller, String callee);

    public void callInitiatedAt(String caller, String callee, long time);

    public void callCompletedNow(String caller, String callee);

    public void callCompletedAt(String caller, String callee, long time);

    public Collection<Call> getCallsFor(String caller);

    public void clearLog();
}
