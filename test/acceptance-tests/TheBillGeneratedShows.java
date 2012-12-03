import fit.RowFixture;

import java.util.ArrayList;
import java.util.List;

public class TheBillGeneratedShows extends RowFixture {
    public static class BillLine {
        public int Line;
        public String Text;

        public BillLine(int line, String text) {
            Line = line;
            Text = text;
        }
    }

    @Override
    public Object[] query() throws Exception {
        SystemUnderTest.billingSystem.createCustomerBills();
        List<BillLine> billLines = new ArrayList<BillLine>();
        for (String line : SystemUnderTest.simplePrinter.getOutput()) {
            billLines.add(new BillLine(billLines.size() + 1, line));
        }
        return billLines.toArray();
    }

    @Override
    public Class<?> getTargetClass() {
        return BillLine.class;
    }

}
