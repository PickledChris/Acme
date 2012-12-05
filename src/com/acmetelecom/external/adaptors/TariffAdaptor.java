package com.acmetelecom.external.adaptors;

import com.acmetelecom.external.interfacecopies.TelecomTariff;
import com.acmetelecom.customer.Tariff;

import java.math.BigDecimal;

/**
 * User: Andy
 * Date: 30/11/12
 */
public class TariffAdaptor implements TelecomTariff {

    private Tariff tariff;

    public TariffAdaptor(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
	public BigDecimal peakRate() {
        return tariff.peakRate();
    }

    @Override
	public BigDecimal offPeakRate() {
        return tariff.offPeakRate();
    }

}
