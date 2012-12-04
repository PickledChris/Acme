package com.acmetelecom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public class CallManager implements TelecomCallManager {

    private Collection<CallEvent> callLog;

    public CallManager() {
        callLog = new ArrayList<CallEvent>();
    }

    @Override
    public void callInitiatedNow(String caller, String callee) {
        callLog.add(new CallStart(caller, callee));
    }

    @Override
    public void callInitiatedAt(String caller, String callee, long time) {
        callLog.add(new CallStart(caller, callee, time));
    }

    @Override
    public void callCompletedNow(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee));
    }

    @Override
    public void callCompletedAt(String caller, String callee, long time) {
        callLog.add(new CallEnd(caller, callee, time));
    }

    @Override
    public Collection<Call> getCallsFor(String callerNumber) {

        List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : callLog) {
            if(event.getCaller().equals(callerNumber))
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }

        return calls;
    }

    @Override
    public void clearLog() {
        this.callLog.clear();
    }
}
