package com.acmetelecom;

import static com.acmetelecom.TelecomMockFactory.createFakeCall;
import static com.acmetelecom.TelecomMockFactory.createFakeCustomer;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.billingsystem.BillGenerator;
import com.acmetelecom.billingsystem.BillingSystem;
import com.acmetelecom.billingsystem.Call;
import com.acmetelecom.billingsystem.CallCostCalculator;
import com.acmetelecom.billingsystem.CustomerDatasource;
import com.acmetelecom.billingsystem.LineItem;
import com.acmetelecom.billingsystem.TelecomCallManager;
import com.acmetelecom.billingsystem.TelecomCustomer;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Set of tests for the Billing System. Uses Mocking to mock the billing data.
 */
public class BillingSystemTests {

    private CallCostCalculator callCalculator;
    private CustomerDatasource customerSource;
    private BillGenerator billGenerator;
    private TelecomCallManager callManager;
    

    @BeforeMethod
    public void setUpMockComponents() {
    	this.customerSource = mock(CustomerDatasource.class);
        this.billGenerator = mock(BillGenerator.class);
        this.callManager = mock(TelecomCallManager.class);
        this.callCalculator = mock(CallCostCalculator.class);
    }
   

    @Test
    public void testThatCallIsMade() throws InterruptedException {
    	TelecomCustomer manCustomer = createFakeCustomer("Man", "1", "Expensive");
    	List<TelecomCustomer> customers = asList(
    			manCustomer);
    	when(customerSource.getCustomers()).thenReturn(customers);
    	List<Call> calls = asList(
    			createFakeCall("1", new DateTime(), new DateTime()),
    			createFakeCall("1", new DateTime(), new DateTime()));
    	when(callManager.getCallsFor("1")).thenReturn(calls);
    	List<LineItem> lineItems = new ArrayList<LineItem>();
    	for (Call call : calls) {
    		lineItems.add(new LineItem(call, new BigDecimal(0)));
    	}
    	when(callCalculator.calculateCallCosts(manCustomer, calls)).thenReturn(lineItems);
        BillingSystem billingSystem = new BillingSystem(customerSource, callCalculator, callManager, billGenerator);

        billingSystem.createCustomerBills();
        verify(customerSource).getCustomers();
        verify(callManager).getCallsFor(manCustomer.getPhoneNumber());
        verify(callCalculator).calculateCallCosts(manCustomer, calls);
        verify(billGenerator).send(manCustomer, lineItems);
    }

}
