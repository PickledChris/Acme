package com.acmetelecom;

import org.testng.annotations.Test;

import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * User: Chris Bates
 * Date: 03/12/12
 */
public class CallManagerTests {

    String callerNo1 = "00000";
    String callerNo2 = "00001";
    String calleeNo1 = "00010";
    String calleeNo2 = "00011";


    public void singleCallTest() {

        CallManager c = new CallManagerImpl();

        c.callInitiated(callerNo1, calleeNo1);
        c.callCompleted(callerNo1, calleeNo1);
        Collection<Call> calls = c.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 1);
        Call theCall = calls.iterator().next();
        assertEquals(theCall.callee(), calleeNo1);

    }

    @Test
    public void twoCallTest() {

        CallManager c = new CallManagerImpl();

        c.callInitiated(callerNo1, calleeNo1);
        c.callCompleted(callerNo1, calleeNo1);

        c.callInitiated(callerNo1, calleeNo2);
        c.callCompleted(callerNo1, calleeNo2);

        Collection<Call> calls = c.getCallsFor(callerNo1);

        assertNotNull(calls);
        assertEquals(calls.size(), 2);
        Call call1 = calls.iterator().next();
        Call call2 = calls.iterator().next();
        if(call1.callee().equals(calleeNo1)) {
            assertEquals(call2.callee(), calleeNo2, "CalleeNo2 was not recorded as being called");
        } else if (call1.callee().equals(calleeNo2)) {
            assertEquals(call2.callee(), calleeNo1, "CalleeNo1 was not recorded as being called");
        } else {
            fail("The first call was to neither callee1 or 2! OMG");
        }

    }

    public void twoCallersTest() {

    }

}
