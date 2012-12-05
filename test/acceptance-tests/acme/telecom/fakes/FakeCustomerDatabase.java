package acme.telecom.fakes;

import com.acmetelecom.billingsystem.CustomerDatasource;
import com.acmetelecom.billingsystem.TelecomCustomer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeCustomerDatabase implements CustomerDatasource {

    private Map<String, TelecomCustomer> customers = new HashMap<String, TelecomCustomer>();

    public void addCustomer(TelecomCustomer customer) {
        customers.put(customer.getPhoneNumber(), customer);
    }

    public void reset() {
        this.customers.clear();
    }

    @Override
    public List<TelecomCustomer> getCustomers() {
        List<TelecomCustomer> customerList = new ArrayList<TelecomCustomer>();
        for (TelecomCustomer customer : this.customers.values()) {
            customerList.add(customer);
        }
        return customerList;
    }
}
