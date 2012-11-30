package com.acmetelecom;

import com.acmetelecom.customer.*;
import com.acmetelecom.externaladaptors.TariffLibraryManager;

import java.math.BigDecimal;
import java.util.*;

public class BillingSystem {

    private List<CallEvent> callLog = new ArrayList<CallEvent>();
    private CustomerDatabase database;
    private Printer printer;
    private final CallCostCalculator callCostCalculator;

    public BillingSystem(CustomerDatabase database, TariffLibraryManager tariffLibrary, Printer printer) {
        this.database = database;
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
        List<Customer> customers = this.database.getCustomers();
        for (Customer customer : customers) {
            createBillFor(customer);
        }
        callLog.clear();
    }

    private void createBillFor(Customer customer) {
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
