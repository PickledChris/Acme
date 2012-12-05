package acme.telecom.fakes;

import com.acmetelecom.external.interfacecopies.TelecomTariff;
import com.acmetelecom.external.interfacecopies.TelecomCustomer;
import com.acmetelecom.external.interfacecopies.TelecomTariffLibrary;

import java.util.HashMap;
import java.util.Map;

public class FakeTariffLibrary implements TelecomTariffLibrary {

    private Map<String, TelecomTariff> tariffMap = new HashMap<String, TelecomTariff>();

    public void addTariff(String name, TelecomTariff tariff) {
        this.tariffMap.put(name, tariff);
    }

    public void reset() {
        tariffMap.clear();
    }

    @Override
    public TelecomTariff getTariffForCustomer(TelecomCustomer customer) {
        return tariffMap.get(customer.getPricePlan());
    }

}
