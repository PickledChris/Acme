package com.acmetelecom.externaladaptors;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.TariffLibrary;

/**
 * User: Andy
 * Date: 30/11/12
 */
public class TariffLibraryManager {

    private TariffLibrary tariffLibrary;

    public TariffLibraryManager(TariffLibrary tariffLibrary) {
        this.tariffLibrary = tariffLibrary;
    }

    public TariffAdaptor getTariffForCustomer(Customer customer) {
         return new TariffAdaptor(this.tariffLibrary.tarriffFor(customer));
    }

}
