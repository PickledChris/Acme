package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import com.acmetelecom.externaladaptors.CustomerDatabaseAdaptor;
import com.acmetelecom.externaladaptors.TariffLibraryManager;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Set of tests for the Billing System. Uses Mocking to mock the billing data.
 */
public class BillingSystemTests {

    TariffLibrary mockTariffLibrary;

    @BeforeTest
    public void setUpTariffLibrary() {

        this.mockTariffLibrary = mock(TariffLibrary.class);
        when(mockTariffLibrary.tarriffFor(Mockito.any(Customer.class))).thenReturn(Tariff.Standard);
    }

    @Test
    public void testThatCallIsMade() throws InterruptedException {

        String caller = "00000000";
        String callee = "00000001";
        Customer bates = new Customer("Bates",caller,"PRICE_PLAN");
        Customer dave = new Customer("Dave",callee,"PRICE_PLAN");
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(bates);
        customers.add(dave);

        Printer mockPrinter = mock(Printer.class);

        CustomerDatabase mockDatabase = mock(CustomerDatabase.class);
        when(mockDatabase.getCustomers()).thenReturn(customers);

        long callLengthSeconds = 5L;
        Call call = new Call(mockCallEvent(caller, callee, 0, CallStart.class),
                mockCallEvent(caller, callee, callLengthSeconds * 1000, CallEnd.class));

        Collection<Call> callList = new ArrayList<Call>();
        callList.add(call);

        CallManager mockCallManager = mock(CallManager.class);
        when(mockCallManager.getCallsFor(caller)).thenReturn(callList);

        BillingSystem billingSystem = new BillingSystem(new CustomerDatabaseAdaptor(mockDatabase),
                new TariffLibraryManager(this.mockTariffLibrary),
                new PeakPeriodManager(),
                mockPrinter,
                mockCallManager);

        billingSystem.createCustomerBills();

        verify(mockPrinter).printItem(any(String.class), eq(callee), eq("0:0" + callLengthSeconds), any(String.class));
    }

    private <T extends CallEvent> T mockCallEvent(String caller, String callee, long time, Class<T> callType) {
        T event = mock(callType);
        when(event.getCallee()).thenReturn(callee);
        when(event.getCaller()).thenReturn(caller);
        when(event.time()).thenReturn(time);
        return event;
    }

}
