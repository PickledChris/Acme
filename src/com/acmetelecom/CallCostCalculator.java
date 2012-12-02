package com.acmetelecom;

import com.acmetelecom.customer.Customer;
import com.acmetelecom.externaladaptors.TariffLibraryManager;
import com.acmetelecom.externaladaptors.TelecomTariff;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CallCostCalculator {

    private final TariffLibraryManager tariffLibrary;

    public CallCostCalculator(TariffLibraryManager tariffLibrary) {
        this.tariffLibrary = tariffLibrary;
    }

    public List<LineItem> calculateCallCosts(Customer customer, List<Call> calls) {
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {

            TelecomTariff tariff = this.tariffLibrary.getTariffForCustomer(customer);

            BigDecimal cost;

            DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
            if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime())
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