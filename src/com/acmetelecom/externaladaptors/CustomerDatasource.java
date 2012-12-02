package com.acmetelecom.externaladaptors;

import java.util.List;

import com.acmetelecom.customer.Customer;

public interface CustomerDatasource {

	public List<Customer> getCustomers();

}
