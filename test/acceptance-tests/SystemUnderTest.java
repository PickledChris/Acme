import acme.telecom.fakes.FakeCustomerDatabase;
import acme.telecom.fakes.FakeTariffLibrary;
import acme.telecom.fakes.SimplePrinter;
import com.acmetelecom.BillingSystem;
import com.acmetelecom.PeakPeriodDatasource;
import com.acmetelecom.PeakPeriodManager;

public class SystemUnderTest {

    public static final FakeTariffLibrary tariffLibrary = new FakeTariffLibrary();
    public static final FakeCustomerDatabase customerDatabase = new FakeCustomerDatabase();
    public static final SimplePrinter simplePrinter = new SimplePrinter();
    public static final PeakPeriodDatasource peakPeriodManager = new PeakPeriodManager();
    public static BillingSystem billingSystem;

    public static void reset() {
        billingSystem = new BillingSystem(customerDatabase, tariffLibrary, peakPeriodManager, simplePrinter);

    }

}
