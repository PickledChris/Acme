package com.acmetelecom.callmanager;

import com.acmetelecom.billingsystem.CallEvent;

public class CallStart extends CallEvent {
    public CallStart(String caller, String callee) {
        this(caller, callee, System.currentTimeMillis());
    }

    public CallStart(String caller, String callee, long time) {
        super(caller, callee, time);
    }
}
