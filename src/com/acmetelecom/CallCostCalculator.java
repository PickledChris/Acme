package com.acmetelecom;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;

import java.util.Collection;
import java.util.List;

public interface CallCostCalculator {

	public List<LineItem> calculateCallCosts(TelecomCustomer customer,
			Collection<Call> calls);
}