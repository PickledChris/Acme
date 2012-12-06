package com.acmetelecom.billingsystem;


import java.util.List;

public interface BillGenerator {

	public void send(TelecomCustomer customer, List<LineItem> calls);

}