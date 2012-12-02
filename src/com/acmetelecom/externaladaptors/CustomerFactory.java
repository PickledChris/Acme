package com.acmetelecom.externaladaptors;

import com.acmetelecom.customer.Customer;

public class CustomerFactory {
	
	public Customer createCustomer(String customerName, String phoneNumber, String pricePlan) {
		return new Customer(customerName, phoneNumber, pricePlan);
	}

}