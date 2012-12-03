import acme.telecom.fakes.FakeCustomer;
import fit.ColumnFixture;
import fit.Parse;

public class GivenTheFollowingCustomers extends ColumnFixture {
    public String Name;
    public String PhoneNumber;
    public String Tariff;

    @Override
    public void doRows(Parse rows) {
        SystemUnderTest.customerDatabase.reset();
        super.doRows(rows);
    }

    @Override
    public void reset() throws Exception {
        Name = null;
        PhoneNumber = null;
        Tariff = null;
    }

    @Override
    public void execute() throws Exception {
        SystemUnderTest.customerDatabase.addCustomer(new FakeCustomer(Name, PhoneNumber, Tariff));
    }
}
