package acme.telecom.fakes;

import com.acmetelecom.billgenerator.Printer;

import java.util.*;

public class SimplePrinter implements Printer {

    Map<String, List<String>> customerMap = new HashMap<String, List<String>>();
    String currentPhoneNumber;

    public List<String> getOutput() {
        Collection<String> customerNames = customerMap.keySet();
        List<String> names = new ArrayList<String>();
        for(String name : customerNames) {
            names.add(name);
        }
        Collections.sort(names);

        List<String> output = new ArrayList<String>();
        for (String name : customerNames) {
            output.addAll(customerMap.get(name));
        }

        return output;
    }

    public void clearOutput() {
        this.customerMap.clear();
        this.currentPhoneNumber = null;
    }

    @Override
    public void printHeading(String name, String phoneNumber, String pricePlan) {
        this.currentPhoneNumber = phoneNumber;
        List<String> customerInfo = new ArrayList<String>();
        customerInfo.add(String.format("%s/%s - Price Plan: %s", name, phoneNumber, pricePlan));
        this.customerMap.put(phoneNumber, customerInfo);
    }

    @Override
    public void printItem(String time, String callee, String duration, String cost) {
        this.customerMap.get(this.currentPhoneNumber).add(
                String.format("%s: Call to %s lasting %s - cost %s", time, callee, duration, cost));
    }

    @Override
    public void printTotal(String total) {
        this.customerMap.get(this.currentPhoneNumber).add(String.format("Total bill: %s", total));
    }

}
