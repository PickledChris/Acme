package com.acmetelecom.billgenerator;

import com.acmetelecom.billingsystem.BillGenerator;
import com.acmetelecom.billingsystem.LineItem;
import com.acmetelecom.billingsystem.TelecomCustomer;

import java.math.BigDecimal;
import java.util.List;

public class TelecomBillGenerator implements BillGenerator {

    private final Printer printer;

    public TelecomBillGenerator(Printer printer) {
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
