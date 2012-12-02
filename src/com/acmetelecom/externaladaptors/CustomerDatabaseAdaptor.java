package com.acmetelecom.externaladaptors;

import java.util.List;

import com.acmetelecom.CustomerDatasource;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.CustomerDatabase;

public class CustomerDatabaseAdaptor implements CustomerDatasource {
	private CustomerDatabase database;
	
	public CustomerDatabaseAdaptor(CustomerDatabase database) {
		this.database = database;
	}

	@Override
	public List<Customer> getCustomers() {
		return database.getCustomers();
	}
	
	

}
