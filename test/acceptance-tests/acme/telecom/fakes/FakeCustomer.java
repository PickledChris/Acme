package acme.telecom.fakes;

import com.acmetelecom.TelecomCustomer;

public class FakeCustomer implements TelecomCustomer {

    private String name;
    private String phoneNumber;
    private String tariff;

    public FakeCustomer(String name, String phoneNumber, String tariff) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.tariff = tariff;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getFullName() {
        return name;
    }

    @Override
    public String getPricePlan() {
       return tariff;
    }
}
