package com.acmetelecom.billingsystem;


import java.util.*;

public class BillingSystem {

    private final CustomerDatasource customerSource;
    private final CallManager callManager;
    private final CallCostCalculator callCostCalculator;
    private final BillGenerator billGenerator;

    public BillingSystem(CustomerDatasource customerSource, CallCostCalculator callCostCalculator, 
    		               CallManager callManager, BillGenerator billGenerator) {
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
