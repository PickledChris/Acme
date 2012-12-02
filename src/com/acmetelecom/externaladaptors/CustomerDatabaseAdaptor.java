package com.acmetelecom.externaladaptors;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.CustomerDatasource;
import com.acmetelecom.TelecomCustomer;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;

public class CustomerDatabaseAdaptor implements CustomerDatasource {
	private CustomerDatabase database;
	
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
