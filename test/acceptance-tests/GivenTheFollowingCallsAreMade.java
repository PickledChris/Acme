import fit.ColumnFixture;
import fit.Parse;
import fitlibrary.specify.SequenceFixtureFlowUnderTest;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GivenTheFollowingCallsAreMade extends ColumnFixture {

    public String Caller;
    public String Callee;
    public String StartTime;
    public String EndTime;

    @Override
    public void doRows(Parse rows) {
        SystemUnderTest.callManager.clearLog();
        super.doRows(rows);
    }

    @Override
    public void reset() throws Exception {
        Caller = null;
        Callee = null;
        StartTime = null;
        EndTime = null;
    }

    @Override
    public void execute() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy HH:mm:ss");
        Long startTime = dateTimeFormatter.parseDateTime(StartTime).toDate().getTime();
        Long endTime = dateTimeFormatter.parseDateTime(EndTime).toDate().getTime();
        SystemUnderTest.callManager.callInitiatedAt(Caller, Callee, startTime);
        SystemUnderTest.callManager.callCompletedAt(Caller, Callee, endTime);
    }
}
