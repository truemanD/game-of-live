
import gameoflive.GameOfLiveException;
import gameoflive.PlayingField;
import gameoflive.PlayingFieldIface;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 *
 * @author diyanov-a
 */
public class PlayingFieldTest {

    PlayingFieldIface pf;

    @Test
    public void testsOrdering() throws InterruptedException, GameOfLiveException {
        getInstance3NotNull();
        setAllTrueValue();
        gotNextDayDeadAll();
        setFullCrossTrueValue();
        gotNextDayDeadAll();
        setRowTrueValue(0);
        gotNextDayLiveAll();
        gotNextDayDeadAll();
        setRowTrueValue(1);
    }

    public void getInstance3NotNull() {
        System.out.println("getInstance3NotNull");
        pf = PlayingField.getInstance(3);
        Assert.assertNotNull(pf);
        System.out.println(pf);
    }

    public void setAllTrueValue() {
        System.out.println("setAllTrueValue");
        pf = PlayingField.getInstance();
        List<List<Boolean>> field = pf.getField();
        for (int i = 0; i < field.size(); i++) {
            List<Boolean> row = field.get(i);
            for (int j = 0; j < row.size(); j++) {
                row.set(j, Boolean.TRUE);
            }
            field.set(i, row);
        }
        List<List<Boolean>> currentfield = pf.getField();
        System.out.println(pf);
        String etalon = "[[true, true, true], [true, true, true], [true, true, true]]";
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

    public void gotNextDayDeadAll() throws InterruptedException, GameOfLiveException {
        System.out.println("gotNextDayDeadAll");
        pf = PlayingField.getInstance();
        System.out.println(pf.goNextDay());
        List<List<Boolean>> currentfield = pf.getField();
        String etalon = "[[false, false, false], [false, false, false], [false, false, false]]";
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

    public void gotNextDayLiveAll() throws InterruptedException, GameOfLiveException {
        System.out.println("gotNextDayLiveAll");
        pf = PlayingField.getInstance();
        System.out.println(pf.goNextDay());
        List<List<Boolean>> currentfield = pf.getField();
        String etalon = "[[true, true, true], [true, true, true], [true, true, true]]";
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

    public void setFullCrossTrueValue() {
        System.out.println("setFullCrossTrueValue");
        pf = PlayingField.getInstance();
        List<List<Boolean>> field = pf.getField();
        for (int i = 0; i < field.size(); i++) {
            List<Boolean> row = field.get(i);
            if (i == 1) {
                row.set(0, true);
                row.set(1, true);
                row.set(2, true);
            }
            if (i == 0 || i == 2) {
                row.set(0, false);
                row.set(1, true);
                row.set(2, false);
            }
            field.set(i, row);
        }
        List<List<Boolean>> currentfield = pf.getField();
        System.out.println(pf);
        String etalon = "[[false, true, false], [true, true, true], [false, true, false]]";
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

    public void setRowTrueValue(int rowNumber) {
        System.out.println("setRowTrueValue");
        pf = PlayingField.getInstance();
        List<List<Boolean>> field = pf.getField();
        for (int i = 0; i < field.size(); i++) {
            List<Boolean> row = field.get(i);
            if (i == rowNumber) {
                row.set(0, true);
                row.set(1, true);
                row.set(2, true);
            } else {
                row.set(0, false);
                row.set(1, false);
                row.set(2, false);
            }
            field.set(i, row);
        }
        List<List<Boolean>> currentfield = pf.getField();
        System.out.println(pf);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < field.size(); i++) {

            if (i == rowNumber) {
                sb.append("[true, ");
                sb.append("true, ");
                sb.append("true]");
            } else {
                sb.append("[false, ");
                sb.append("false, ");
                sb.append("false]");
            }
            if (i < field.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        String etalon = sb.toString();
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

    public void setColTrueValue(int rowNumber) {
        System.out.println("setColTrueValue");
        pf = PlayingField.getInstance();
        List<List<Boolean>> field = pf.getField();
        for (int i = 0; i < field.size(); i++) {
            List<Boolean> row = field.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (j == rowNumber) {
                    row.set(j, true);
                } else {
                    row.set(j, false);
                }
            }
            field.set(i, row);
        }
        List<List<Boolean>> currentfield = pf.getField();
        System.out.println(pf);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < field.size(); i++) {
            List<Boolean> row = field.get(i);
            sb.append("[");
            for (int j = 0; j < row.size(); j++) {
                if (j == rowNumber) {
                    sb.append("true");
                } else {
                    sb.append("false");
                }
                if (j < row.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < field.size() - 1) {
                sb.append(", ");
            }

        }
        sb.append("]");
        String etalon = sb.toString();
        Assert.assertTrue(etalon.equals(currentfield.toString()));
    }

}
