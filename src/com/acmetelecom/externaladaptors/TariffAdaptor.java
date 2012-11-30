package com.acmetelecom.externaladaptors;

import com.acmetelecom.customer.Tariff;

import java.math.BigDecimal;

/**
 * User: Andy
 * Date: 30/11/12
 */
public class TariffAdaptor {

    private Tariff tariff;

    public TariffAdaptor(Tariff tariff) {
        this.tariff = tariff;
    }

    public BigDecimal peakRate() {
        return tariff.peakRate();
    }

    public BigDecimal offPeakRate() {
        return tariff.offPeakRate();
    }

}
