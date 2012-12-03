package com.acmetelecom.externaladaptors;

import com.acmetelecom.customer.Customer;

/**
 * Created with IntelliJ IDEA.
 * User: Chris
 * Date: 03/12/12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerFactory {
    Customer createCustomer(String customerName, String phoneNumber, String pricePlan);
}
