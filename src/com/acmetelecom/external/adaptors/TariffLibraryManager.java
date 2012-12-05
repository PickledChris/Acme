package com.acmetelecom.external.adaptors;

import com.acmetelecom.billingsystem.TelecomCustomer;
import com.acmetelecom.billingsystem.TelecomTariff;
import com.acmetelecom.billingsystem.TelecomTariffLibrary;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.TariffLibrary;

/**
 * User: Andy
 * Date: 30/11/12
 */
public class TariffLibraryManager implements TelecomTariffLibrary {

    private TariffLibrary tariffLibrary;

    public TariffLibraryManager(TariffLibrary tariffLibrary) {
        this.tariffLibrary = tariffLibrary;
    }

    @Override
	public TelecomTariff getTariffForCustomer(TelecomCustomer customer) {
    	CustomerFactory factory = new CustomerFactory();
    	Customer externalCust = factory.createCustomer(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        return new TariffAdaptor(this.tariffLibrary.tarriffFor(externalCust));
    }


}
