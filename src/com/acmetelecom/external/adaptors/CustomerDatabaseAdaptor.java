package com.acmetelecom.external.adaptors;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.external.adaptors.CustomerAdaptor;
import com.acmetelecom.billingsystem.CustomerDatasource;
import com.acmetelecom.billingsystem.TelecomCustomer;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;

public class CustomerDatabaseAdaptor implements CustomerDatasource {
	private final CustomerDatabase database;
	
	public CustomerDatabaseAdaptor(CustomerDatabase database) {
		this.database = database;
	}

	@Override
	public List<TelecomCustomer> getCustomers() {
		List<TelecomCustomer> customers = new ArrayList<TelecomCustomer>();
		for (Customer customer : database.getCustomers()) {
			customers.add(new CustomerAdaptor(customer));
		}
		return customers;
	}
	
	

}
