package com.acmetelecom;

import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TariffCallCostCalculator implements CallCostCalculator {

    private final TelecomTariffLibrary tariffLibrary;
    private PeakPeriodDatasource peakManager;

    public TariffCallCostCalculator(TelecomTariffLibrary tariffLibrary, PeakPeriodDatasource peakManager) {
        this.tariffLibrary = tariffLibrary;
        this.peakManager = peakManager;
    }

    /* (non-Javadoc)
	 * @see com.acmetelecom.CallCostCalculator2#calculateCallCosts(com.acmetelecom.TelecomCustomer, java.util.Collection)
	 */
    @Override
	public List<LineItem> calculateCallCosts(TelecomCustomer customer, Collection<Call> calls) {
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            TelecomTariff tariff = this.tariffLibrary.getTariffForCustomer(customer);

            BigDecimal cost;

            LocalTime callStartTime = new LocalTime(call.startTime());
            LocalTime callEndTime = new LocalTime(call.endTime());
            if (peakManager.offPeak(callStartTime) && peakManager.offPeak(callEndTime)
                    && call.durationSeconds() < 12 * 60 * 60) {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
            } else {
                cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
            }

            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            items.add(new LineItem(call, callCost));
        }
        return items;
    }
}