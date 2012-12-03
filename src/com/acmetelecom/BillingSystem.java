package com.acmetelecom;

import java.math.BigDecimal;
import java.util.*;

public class BillingSystem {

    private CustomerDatasource customerSource;
    private Printer printer;
    private CallManager callManager;
    private final CallCostCalculator callCostCalculator;

    public BillingSystem(CustomerDatasource customerSource, TelecomTariffLibrary tariffLibrary,
                         PeakPeriodDatasource peakManager, Printer printer, CallManager callManager) {
        this.customerSource = customerSource;
        this.printer = printer;
        this.callManager = callManager;
        this.callCostCalculator = new CallCostCalculator(tariffLibrary, peakManager);
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
        BigDecimal totalBill = new BigDecimal(0);
        for (LineItem item : items) {
             totalBill = totalBill.add(item.cost());
        }

        new BillGenerator(this.printer).send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }

}
