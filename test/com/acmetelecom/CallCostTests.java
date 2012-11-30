package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.externaladaptors.TariffAdaptor;
import com.acmetelecom.externaladaptors.TariffLibraryManager;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Andy
 * Date: 29/11/12
 */
public class CallCostTests {

    TariffAdaptor mockTariff;
    TariffLibraryManager mockTariffLibrary;
    CallCostCalculator callCostCalculator;

    @BeforeTest
    public void setUpTariffLibraryAndCalculator() {
        this.mockTariff = mock(TariffAdaptor.class);
        when(mockTariff.offPeakRate()).thenReturn(BigDecimal.valueOf(0.10));
        when(mockTariff.peakRate()).thenReturn(BigDecimal.valueOf(0.20));

        this.mockTariffLibrary = mock(TariffLibraryManager.class);
        when(mockTariffLibrary.getTariffForCustomer(Mockito.any(Customer.class))).thenReturn(mockTariff);

        this.callCostCalculator = new CallCostCalculator(this.mockTariffLibrary);
    }

    @Test
    public void testOffPeakCallIsChargedCorrectly() {
        String caller = "00000000";
        String callee = "00000001";
        // 20:00, 30/11/2012
        DateTime callStart = new DateTime(2012,11,30,20,0,0);
        DateTime callEnd = callStart.plusMinutes(2);
        Customer customer = new Customer("Bates",caller,"PRICE_PLAN");
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);

        assert items.size() == 1;
        LineItem item = items.get(0);
        assert item.cost().equals(BigDecimal.valueOf(12));
    }

    @Test
    public void testPeakCallIsChargedCorrectly() {
        String caller = "00000000";
        String callee = "00000001";
        // 20:00, 30/11/2012
        DateTime callStart = new DateTime(2012,11,30,12,0,0);
        DateTime callEnd = callStart.plusMinutes(2);
        Customer customer = new Customer("Bates",caller,"PRICE_PLAN");
        List<Call> callList = new ArrayList<Call>();
        callList.add(this.createFakeCall(callee, callStart, callEnd));

        List<LineItem> items = this.callCostCalculator.calculateCallCosts(customer, callList);

        assert items.size() == 1;
        LineItem item = items.get(0);
        assert item.cost().equals(BigDecimal.valueOf(24));
    }

    @Test
    public void testOffPeakToPeakCallIsChargedCorrectly() {

    }

    @Test
    public void testThirteenHourCallChargedCorrectRate() {
        // Test that a call lasting 13 hours is charged correctly for peak time
    }

    @Test
    public void testSimpleMultipleCallsChargedCorrectly() {
         // Test customers making several peak or several off peak calls are
         // correctly charged
    }

    @Test
    public void testComplexMultipleCallsChargedCorrectly() {
        // Test that customers making several peak and off peak calls in one session
        // are charged correctly
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
