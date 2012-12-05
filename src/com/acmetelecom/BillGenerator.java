package com.acmetelecom;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;

import java.math.BigDecimal;
import java.util.List;

public class BillGenerator implements TelecomBillGenerator {

    private Printer printer;

    public BillGenerator(Printer printer) {
        this.printer = printer;
    }

    /* (non-Javadoc)
	 * @see com.acmetelecom.TelecomBillGenerator#send(com.acmetelecom.external.interfacecopies.TelecomCustomer, java.util.List, java.lang.String)
	 */
    @Override
	public void send(TelecomCustomer customer, List<LineItem> calls) {
        BigDecimal totalBill = new BigDecimal(0);
        this.printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (LineItem call : calls) {
        	totalBill = totalBill.add(call.cost());
            this.printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        this.printer.printTotal(MoneyFormatter.penceToPounds(totalBill));
    }

}
