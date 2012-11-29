package com.acmetelecom;

import com.acmetelecom.customer.Customer;

import java.util.List;

public class BillGenerator {

    private Printer printer;

    public BillGenerator(Printer printer) {
        this.printer = printer;
    }

    public void send(Customer customer, List<BillingSystem.LineItem> calls, String totalBill) {

        this.printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (BillingSystem.LineItem call : calls) {
            this.printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        this.printer.printTotal(totalBill);
    }

}
