package com.acmetelecom;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;

import java.util.Collection;
import java.util.List;

public interface CallCostCalculator {

    /**
     * Calculate the cost of each call made by a given customer
     *
     * @param customer TelecomCustomer representing the customer
     * @param calls Collection of Call objects representing calls made
     * @return List of LineItems containing call information
     */
	public List<LineItem> calculateCallCosts(TelecomCustomer customer,
			Collection<Call> calls);
}