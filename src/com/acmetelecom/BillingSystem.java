package com.acmetelecom;

import java.math.BigDecimal;
import java.util.*;

public class BillingSystem {

    private List<CallEvent> callLog = new ArrayList<CallEvent>();
    private CustomerDatasource customerSource;
    private Printer printer;
    private final CallCostCalculator callCostCalculator;

    public BillingSystem(CustomerDatasource customerSource, TelecomTariffLibrary tariffLibrary, Printer printer) {
        this.customerSource = customerSource;
        this.printer = printer;
        this.callCostCalculator = new CallCostCalculator(tariffLibrary);
    }

    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee));
    }

    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee));
    }

    public void createCustomerBills() {
        List<TelecomCustomer> customers = this.customerSource.getCustomers();
        for (TelecomCustomer customer : customers) {
            createBillFor(customer);
        }
        callLog.clear();
    }

    private void createBillFor(TelecomCustomer customer) {
        List<CallEvent> customerEvents = new ArrayList<CallEvent>();
        for (CallEvent callEvent : callLog) {
            if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
                customerEvents.add(callEvent);
            }
        }

        List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : customerEvents) {
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }


        List<LineItem> items = callCostCalculator.calculateCallCosts(customer, calls);
        BigDecimal totalBill = new BigDecimal(0);
        for (LineItem item : items) {
             totalBill = totalBill.add(item.cost());
        }

        new BillGenerator(this.printer).send(customer, items, MoneyFormatter.penceToPounds(totalBill));
    }

}
