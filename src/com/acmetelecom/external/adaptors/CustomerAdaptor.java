package com.acmetelecom.external.adaptors;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;
import com.acmetelecom.customer.Customer;

public class CustomerAdaptor implements TelecomCustomer {
	private final Customer customer;

	public CustomerAdaptor(Customer customer) {
		this.customer = customer;
	}

	public String getFullName() {
		return customer.getFullName();
	}

	public String getPhoneNumber() {
		return customer.getPhoneNumber();
	}

	public String getPricePlan() {
		return customer.getPricePlan();
	}


}
