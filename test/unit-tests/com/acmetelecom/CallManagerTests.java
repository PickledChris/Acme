package com.acmetelecom;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.testng.Assert.*;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public class CallManagerTests {

    private String callerNo1 = "00000";
    private String callerNo2 = "00001";
    private String calleeNo1 = "00010";
    private String calleeNo2 = "00011";

    @Test
    public void singleCallTest() {

        CallManager c = new CallManagerImpl();

        c.callInitiatedNow(callerNo1, calleeNo1);
        c.callCompletedNow(callerNo1, calleeNo1);
        Collection<Call> calls = c.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        Call theCall = calls.iterator().next();
        assertEquals(theCall.callee(), calleeNo1);

    }

    @Test
    public void twoCallTest() {

        CallManager c = new CallManagerImpl();

        c.callInitiatedNow(callerNo1, calleeNo1);
        c.callCompletedNow(callerNo1, calleeNo1);

        c.callInitiatedNow(callerNo1, calleeNo2);
        c.callCompletedNow(callerNo1, calleeNo2);

        Collection<Call> calls = c.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 2);
        Iterator<Call> iterator = calls.iterator();
        Call call1 = iterator.next();
        Call call2 = iterator.next();
        if(call1.callee().equals(calleeNo1)) {
            assertEquals(call2.callee(), calleeNo2, "CalleeNo2 was not recorded as being called");
        } else if (call1.callee().equals(calleeNo2)) {
            assertEquals(call2.callee(), calleeNo1, "CalleeNo1 was not recorded as being called");
        } else {
            fail("The first call was to neither callee1 or 2! OMG");
        }

    }

    @Test
    public void twoCallersTest() {

        CallManager c = new CallManagerImpl();

        c.callInitiatedNow(callerNo1, calleeNo1);
        c.callCompletedNow(callerNo1, calleeNo1);

        c.callInitiatedNow(callerNo2, calleeNo2);
        c.callCompletedNow(callerNo2, calleeNo2);

        Collection<Call> calls = c.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        assertEquals(calls.iterator().next().callee(), calleeNo1);

        calls = c.getCallsFor(callerNo2);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        assertEquals(calls.iterator().next().callee(), calleeNo2);

    }

    @Test
    public void fiveSecondCall() {

        CallManager manager = new CallManagerImpl();
        manager.callInitiatedAt(callerNo1, calleeNo1, 1000);
        manager.callCompletedAt(callerNo1, calleeNo1, 6000);

        Collection<Call> calls = manager.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        assertEquals(calls.iterator().next().durationSeconds(), 5);


    }

    @Test
    public void fiveMinuteCall() {
        CallManager manager = new CallManagerImpl();
        manager.callInitiatedAt(callerNo1, calleeNo1, 0);
        manager.callCompletedAt(callerNo1, calleeNo1, 5 * 60 * 1000);

        Collection<Call> calls = manager.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        assertEquals(calls.iterator().next().durationSeconds(), 5 * 60);

    }

}
