package acme.telecom.fakes;

import com.acmetelecom.TelecomCustomer;
import com.acmetelecom.TelecomTariff;
import com.acmetelecom.TelecomTariffLibrary;

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
