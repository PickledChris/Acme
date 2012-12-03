import fit.RowFixture;

import java.util.ArrayList;
import java.util.List;

public class TheBillGeneratedShows extends RowFixture {
    public static class Row {
        public int Line;
        public String Text;

        public Row(int line, String text) {
            Line = line;
            Text = text;
        }
    }

    @Override
    public Object[] query() throws Exception {
        SystemUnderTest.billingSystem.createCustomerBills();
        List<Row> rows = new ArrayList<Row>();
        for (String line : SystemUnderTest.simplePrinter.getOutput()) {
            rows.add(new Row(rows.size() + 1, line));
        }
        return rows.toArray();
    }

    @Override
    public Class<?> getTargetClass() {
        return Row.class;
    }

}
