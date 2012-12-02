package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.externaladaptors.TariffAdaptor;

public interface TelecomTariffLibrary {

	public TelecomTariff getTariffForCustomer(Customer customer);

}