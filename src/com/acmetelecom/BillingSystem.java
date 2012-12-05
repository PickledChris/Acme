package com.acmetelecom;

import java.util.*;

public class BillingSystem {

    private final CustomerDatasource customerSource;
    private final TelecomCallManager callManager;
    private final CallCostCalculator callCostCalculator;
    private final TelecomBillGenerator billGenerator;

    public BillingSystem(CustomerDatasource customerSource, CallCostCalculator callCostCalculator, 
    		               TelecomCallManager callManager, TelecomBillGenerator billGenerator) {
        this.customerSource = customerSource;
        this.callManager = callManager;
        this.callCostCalculator = callCostCalculator;
        this.billGenerator = billGenerator;
    }

    public void createCustomerBills() {
        List<TelecomCustomer> customers = this.customerSource.getCustomers();
        for (TelecomCustomer customer : customers) {
            createBillFor(customer);
        }
        callManager.clearLog();
    }

    private void createBillFor(TelecomCustomer customer) {
        Collection<Call> calls = this.callManager.getCallsFor(customer.getPhoneNumber());
        List<LineItem> items = callCostCalculator.calculateCallCosts(customer, calls);
        billGenerator.send(customer, items);
    }

}
