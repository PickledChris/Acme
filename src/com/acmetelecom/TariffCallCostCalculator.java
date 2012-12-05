package com.acmetelecom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.acmetelecom.external.interfacecopies.TelecomCustomer;
import com.acmetelecom.external.interfacecopies.TelecomTariff;
import com.acmetelecom.external.interfacecopies.TelecomTariffLibrary;
import org.joda.time.DateTime;

public class TariffCallCostCalculator implements CallCostCalculator {

    private final TelecomTariffLibrary tariffLibrary;
    private PeakPeriodDatasource peakManager;

    public TariffCallCostCalculator(TelecomTariffLibrary tariffLibrary, PeakPeriodDatasource peakManager) {
        this.tariffLibrary = tariffLibrary;
        this.peakManager = peakManager;
    }

    /* (non-Javadoc)
	 * @see com.acmetelecom.CallCostCalculator2#calculateCallCosts(com.acmetelecom.external.interfacecopies.TelecomCustomer, java.util.Collection)
	 */
    @Override
    public List<LineItem> calculateCallCosts(TelecomCustomer customer, Collection<Call> calls) {
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            TelecomTariff tariff = this.tariffLibrary.getTariffForCustomer(customer);

            DateTime callStartTime = new DateTime(call.startTime());
            DateTime callEndTime = new DateTime(call.endTime());
            long peakSeconds = peakManager.secondsInPeak(callStartTime, callEndTime);
            long offPeakSeconds = call.durationSeconds() - peakSeconds;
            BigDecimal offPeakCost = new BigDecimal(offPeakSeconds).multiply(tariff.offPeakRate());
            BigDecimal peakCost = new BigDecimal(peakSeconds).multiply(tariff.peakRate());
            BigDecimal cost = offPeakCost.add(peakCost);
            
            cost = cost.setScale(0, RoundingMode.HALF_UP);
            BigDecimal callCost = cost;
            items.add(new LineItem(call, callCost));
        }
        return items;
    }
}