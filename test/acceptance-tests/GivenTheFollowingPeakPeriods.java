import fit.ColumnFixture;
import fit.Parse;
import org.joda.time.LocalTime;

public class GivenTheFollowingPeakPeriods extends ColumnFixture {
    public String Start;
    public String Finish;

    @Override
    public void doRows(Parse rows) {
        SystemUnderTest.peakPeriodManager.removeAll();
        super.doRows(rows);
    }

    @Override
    public void reset() throws Exception {
        Start = null;
        Finish = null;
    }

    @Override
    public void execute() throws Exception {
        LocalTime start = LocalTime.parse(Start);
        LocalTime finish = LocalTime.parse(Finish);
        SystemUnderTest.peakPeriodManager.addPeakPeriod(start, finish);
    }
}
