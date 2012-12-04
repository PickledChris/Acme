import acme.telecom.fakes.FakeCustomerDatabase;
import acme.telecom.fakes.FakeTariffLibrary;
import acme.telecom.fakes.SimplePrinter;
import com.acmetelecom.*;

public class SystemUnderTest {

    public static final FakeTariffLibrary tariffLibrary = new FakeTariffLibrary();
    public static final FakeCustomerDatabase customerDatabase = new FakeCustomerDatabase();
    public static final SimplePrinter simplePrinter = new SimplePrinter();
    public static final PeakPeriodDatasource peakPeriodManager = new PeakPeriodManager();
    public static BillingSystem billingSystem;
    public static CallManager callManager = new CallManagerImpl();

    public static void reset() {
        simplePrinter.clearOutput();
        billingSystem = new BillingSystem(customerDatabase, tariffLibrary,
                peakPeriodManager, simplePrinter, callManager);
    }

}
