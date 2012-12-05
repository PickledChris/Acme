package com.acmetelecom;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;

import java.util.List;

public interface TelecomBillGenerator {

	public void send(TelecomCustomer customer, List<LineItem> calls);

}