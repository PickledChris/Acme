package com.acmetelecom;

import java.util.Collection;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public interface CallManager {

    public void callInitiated(String caller, String callee);

    public void callCompleted(String caller, String callee);

    public Collection<Call> getCallsFor(String caller);

    public void clearLog();
}
