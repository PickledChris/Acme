package com.acmetelecom.externaladaptors;

import com.acmetelecom.TelecomTariff;
import com.acmetelecom.TelecomTariffLibrary;
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
	public TelecomTariff getTariffForCustomer(Customer customer) {
         return new TariffAdaptor(this.tariffLibrary.tarriffFor(customer));
    }

}
