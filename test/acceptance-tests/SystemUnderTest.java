import acme.telecom.fakes.FakeCustomerDatabase;
import acme.telecom.fakes.FakeTariffLibrary;
import acme.telecom.fakes.SimplePrinter;
import com.acmetelecom.billcalculator.PeakPeriodDatasource;
import com.acmetelecom.billcalculator.TariffCallCostCalculator;
import com.acmetelecom.billcalculator.peak.PeakPeriodManager;
import com.acmetelecom.billgenerator.TelecomBillGenerator;
import com.acmetelecom.billingsystem.BillGenerator;
import com.acmetelecom.billingsystem.BillingSystem;
import com.acmetelecom.billingsystem.CallCostCalculator;
import com.acmetelecom.billingsystem.TelecomCallManager;
import com.acmetelecom.callmanager.SingleReceiverCallManager;

public class SystemUnderTest {

    public static final FakeTariffLibrary tariffLibrary = new FakeTariffLibrary();
    public static final FakeCustomerDatabase customerDatabase = new FakeCustomerDatabase();
    public static final SimplePrinter simplePrinter = new SimplePrinter();
    public static final PeakPeriodDatasource peakPeriodManager = new PeakPeriodManager();
    public static final CallCostCalculator costCalculator = new TariffCallCostCalculator(tariffLibrary, peakPeriodManager);
    public static final BillGenerator billGenerator = new TelecomBillGenerator(simplePrinter);
    public static BillingSystem billingSystem;
    public static TelecomCallManager callManager = new SingleReceiverCallManager();

    public static void reset() {
        simplePrinter.clearOutput();
        billingSystem = new BillingSystem(customerDatabase, costCalculator, callManager, billGenerator);
    }

}
