import fit.ColumnFixture;
import fit.Parse;

public class GivenTheFollowingCallsAreMade extends ColumnFixture {

    public String Caller;
    public String Callee;
    public String StartTime;
    public String EndTime;

    @Override
    public void doRows(Parse rows) {
        //SystemUnderTest.callList.clear();
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
        //SystemUnderTest.callList.add(~);
        SystemUnderTest.callManager.callInitiatedNow(Caller, Callee);
        Thread.sleep(5000);
        SystemUnderTest.callManager.callCompletedNow(Caller, Callee);
    }
}
