package com.acmetelecom.external.adaptors;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;
import com.acmetelecom.external.interfacecopies.TelecomTariffLibrary;
import com.acmetelecom.external.interfacecopies.TelecomTariff;
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
