package acme.telecom.fakes;

import com.acmetelecom.billingsystem.TelecomTariff;

import java.math.BigDecimal;

public class FakeTariff implements TelecomTariff {

    private BigDecimal peakRate;
    private BigDecimal offPeakRate;

    public FakeTariff(BigDecimal peakRate, BigDecimal offPeakRate) {
        this.peakRate = peakRate;
        this.offPeakRate = offPeakRate;
    }

    @Override
    public BigDecimal peakRate() {
        return peakRate;
    }

    @Override
    public BigDecimal offPeakRate() {
        return offPeakRate;
    }
}
