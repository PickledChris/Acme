package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.customer.TariffLibrary;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
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

        BillingSystem billingSystem = new BillingSystem(mockDatabase, this.mockTariffLibrary, mockPrinter);

        billingSystem.callInitiated(caller, callee);
        Thread.sleep(5000);
        billingSystem.callCompleted(caller, callee);

        billingSystem.createCustomerBills();

        verify(mockPrinter).printItem(any(String.class), eq(callee), eq("0:05"), any(String.class));
    }

}
