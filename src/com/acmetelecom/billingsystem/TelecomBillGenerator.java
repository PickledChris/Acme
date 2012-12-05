package com.acmetelecom.billingsystem;


import java.util.List;

public interface TelecomBillGenerator {

	public void send(TelecomCustomer customer, List<LineItem> calls);

}