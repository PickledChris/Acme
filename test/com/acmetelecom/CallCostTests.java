package com.acmetelecom;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.externaladaptors.CustomerAdaptor;
import com.acmetelecom.externaladaptors.TariffLibraryManager;

/**
 * User: Andy
 * Date: 29/11/12
 */
public class CallCostTests {

    TelecomTariff mockTariff;
    TelecomTariffLibrary mockTariffLibrary;
    CallCostCalculator callCostCalculator;
    String callee = "00000001";
    TelecomCustomer customer = new CustomerAdaptor(new Customer("Bates","00000000","PRICE_PLAN"));

    @BeforeTest
    public void setUpTariffLibraryAndCalculator() {
        this.mockTariff = mock(TelecomTariff.class);
        when(mockTariff.offPeakRate()).thenReturn(BigDecimal.valueOf(0.10));
        when(mockTariff.peakRate()).thenReturn(BigDecimal.valueOf(0.20));

        this.mockTariffLibrary = mock(TariffLibraryManager.class);
        when(mockTariffLibrary.getTariffForCustomer(Mockito.any(TelecomCustomer.class))).thenReturn(mockTariff);

        this.callCostCalculator = new CallCostCalculator(this.mockTariffLibrary);
    }

    @Test
    public void testOffPeakCallIsChargedCorrectly() {
        
        // 20:00, 30/11/2012
        DateTime callStart = new DateTime(2012,11,30,20,0,0);
        DateTime callEnd = callStart.plusMinutes(2);
        
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);

        assertEquals(items.size(), 1);
        LineItem item = items.get(0);
        assertEquals(item.cost(), BigDecimal.valueOf(6 * 2));
    }

    @Test
    public void testPeakCallIsChargedCorrectly() {

        // 12:00, 30/11/2012
        DateTime callStart = new DateTime(2012,11,30,12,0,0);
        DateTime callEnd = callStart.plusMinutes(2);
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);

        assertEquals(items.size(), 1);
        LineItem item = items.get(0);
        assertEquals(item.cost(), BigDecimal.valueOf(12 * 2));
    }

    @Test
    public void testOffPeakToPeakCallIsChargedCorrectly() {

        DateTime callStart = new DateTime(2012,11,30,6,50,0);
        DateTime callEnd = callStart.plusMinutes(20);
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        LineItem item = this.callCostCalculator.calculateCallCosts(customer, callList).get(0);
        assertEquals(item.cost(), BigDecimal.valueOf(12 * 20));
    }

    @Test
    public void testThirteenHourCallChargedCorrectRate() {
        // Test that a call lasting 13 hours is charged correctly for peak time
    	DateTime callStart = new DateTime(2012,11,30,6,30,0);
        DateTime callEnd = callStart.plusHours(13);
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        LineItem item = this.callCostCalculator.calculateCallCosts(customer, callList).get(0);
        assertEquals(item.cost(), BigDecimal.valueOf(12 * 60 * 13));
    }

    @Test
    public void testSimpleMultipleCallsChargedCorrectly() {
         // Test customers making several peak or several off peak calls are
         // correctly charged
    	DateTime callStart1 = new DateTime(2012,11,30,9,30,0);
        DateTime callEnd1 = callStart1.plusMinutes(10);

    	DateTime callStart2 = new DateTime(2012,11,30,11,00,0);
        DateTime callEnd2 = callStart2.plusMinutes(5);
        
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart1, callEnd1));
        callList.add(this.createFakeCall(callee, callStart2, callEnd2));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);
        LineItem item1 = items.get(0);
        LineItem item2 = items.get(1);
        assertEquals(item1.cost().add(item2.cost()), BigDecimal.valueOf(12 * (10 + 5)));
    }

    @Test
    public void testComplexMultipleCallsChargedCorrectly() {
        // Test that customers making several peak and off peak calls in one session
        // are charged correctly
    	DateTime callStart1 = new DateTime(2012,11,30,6,30,0);
        DateTime callEnd1 = callStart1.plusMinutes(10);

    	DateTime callStart2 = new DateTime(2012,11,30,11,00,0);
        DateTime callEnd2 = callStart2.plusMinutes(5);
        
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart1, callEnd1));
        callList.add(this.createFakeCall(callee, callStart2, callEnd2));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);
        LineItem item1 = items.get(0);
        LineItem item2 = items.get(1);
        assertEquals(item1.cost().add(item2.cost()), BigDecimal.valueOf((6 * 10) + (12 * 5)));
    }

    /**
     * Helper method to create a fake call
     */
    private Call createFakeCall(String calleeNumber, DateTime startTime, DateTime endTime) {
        Call call = mock(Call.class);
        when(call.callee()).thenReturn(calleeNumber);
        when(call.startTime()).thenReturn(startTime.toDate());
        when(call.endTime()).thenReturn(endTime.toDate());
        when(call.durationSeconds()).thenReturn(Seconds.secondsBetween(startTime, endTime).getSeconds());
        return call;
    }
}
