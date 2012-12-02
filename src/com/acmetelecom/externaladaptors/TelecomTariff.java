package com.acmetelecom.externaladaptors;

import java.math.BigDecimal;

public interface TelecomTariff {

	public BigDecimal peakRate();

	public BigDecimal offPeakRate();

}