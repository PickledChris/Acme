package com.acmetelecom;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import com.acmetelecom.billingsystem.Call;
import com.acmetelecom.billingsystem.TelecomCustomer;

class TelecomMockFactory {
	
	public static TelecomCustomer createFakeCustomer(String name, String phoneNumber, String tariff) {
    	TelecomCustomer customer = mock(TelecomCustomer.class);
    	when(customer.getFullName()).thenReturn(name);
    	when(customer.getPhoneNumber()).thenReturn(phoneNumber);
    	when(customer.getPricePlan()).thenReturn(tariff);
    	return customer;
    }
	

    public static Call createFakeCall(String calleeNumber, DateTime startTime, DateTime endTime) {
        Call call = mock(Call.class);
        when(call.callee()).thenReturn(calleeNumber);
        when(call.startTime()).thenReturn(startTime.toDate());
        when(call.endTime()).thenReturn(endTime.toDate());
        when(call.durationSeconds()).thenReturn(Seconds.secondsBetween(startTime, endTime).getSeconds());
        return call;
    }

}
