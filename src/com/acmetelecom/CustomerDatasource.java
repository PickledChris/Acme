package com.acmetelecom;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;

import java.util.List;

public interface CustomerDatasource {

	public List<TelecomCustomer> getCustomers();

}
