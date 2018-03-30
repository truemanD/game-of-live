package gameoflive;

import gameoflive.dao.BinaryPlayingField;
import gameoflive.dao.PlayingFieldDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diyanov-a
 */
public class PlayingField implements PlayingFieldIface {

    private List<List<Boolean>> field;
    private static PlayingField INSTANCE;
    private int fieldSize = 0;
    private int stepCounter = 0;
    private static PlayingFieldDAO dao;
    private List<List<Boolean>> veryOldField;
    private List<List<Boolean>> oldField;

    private PlayingField(int fieldSize) {
        this.fieldSize = fieldSize;
        this.field = new ArrayList<>(fieldSize);
        resetField();
        dao = BinaryPlayingField.getInstance(this.fieldSize);
    }

    public static PlayingFieldIface getInstance(int fieldSize) {
        if (INSTANCE == null) {
            INSTANCE = new PlayingField(fieldSize);
        }
        return INSTANCE;
    }

    public static PlayingFieldIface getInstance() {
        return getInstance(10);
    }

    private void resetField() {
        for (int i = 0; i < fieldSize; i++) {
            ArrayList row = new ArrayList(fieldSize);
            for (int j = 0; j < fieldSize; j++) {
                row.add(j, false);
            }
            field.add(row);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Day:" + stepCounter + ";\tFrame size:" + field.size() + ":" + field.get(0).size() + ";\r\n");
        for (List row : field) {
            for (Object value : row) {
                if ((Boolean)value) {
                    sb.append("0");
                }
                if (!(Boolean)value) {
                    sb.append("-");
                }
                sb.append(" ");
            }
            sb.append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    @Override
    public PlayingField generateRandom() {
        stepCounter++;
        for (List row : field) {
            for (int j = 0; j < row.size(); j++) {
                if ((Math.random() * 10) >= 5) {
                    row.set(j, true);
                } else {
                    row.set(j, false);
                }
            }
        }
        dao.saveCurrentDay(INSTANCE);
        return this;
    }

    @Override
    public PlayingField goToDay(int day) throws GameOfLiveException {
        stepCounter = day;
        INSTANCE.setField(dao.loadDay(day));
        return this;
    }

    @Override
    public PlayingField goNextDay() throws InterruptedException, GameOfLiveException {
        stepCounter++;
        List<List<Boolean>> tempField = new ArrayList<>();
        ArrayList rowUp, row, rowDown, tempRow;
        for (int i = 0; i < field.size(); i++) {
            if (i == 0) {
                rowUp = (ArrayList) field.get(field.size() - 1);
            } else {
                rowUp = (ArrayList) field.get(i - 1);
            }
            if (i == field.size() - 1) {
                rowDown = (ArrayList) field.get(0);
            } else {
                rowDown = (ArrayList) field.get(i + 1);
            }
            row = (ArrayList) field.get(i);
            tempRow = new ArrayList(fieldSize);
            boolean v1, v2, v3;
            boolean v4, current, v6;
            boolean v7, v8, v9;

            for (int j = 0; j < row.size(); j++) {
                List<Boolean> room = new ArrayList<>();
                if (j == 0) {
                    v1 = (boolean) (rowUp != null ? rowUp.get(field.size() - 1) : false);
                    v4 = (boolean) row.get(field.size() - 1);
                    v7 = (boolean) (rowDown != null ? rowDown.get(field.size() - 1) : false);
                } else {
                    v1 = (boolean) (rowUp != null ? rowUp.get(j - 1) : false);
                    v4 = (boolean) row.get(j - 1);
                    v7 = (boolean) (rowDown != null ? rowDown.get(j - 1) : false);
                }

                if (j == field.size() - 1) {
                    v3 = (boolean) (rowUp != null ? rowUp.get(0) : false);
                    v6 = (boolean) row.get(0);
                    v9 = (boolean) (rowDown != null ? rowDown.get(0) : false);
                } else {
                    v3 = (boolean) (rowUp != null ? rowUp.get(j + 1) : false);
                    v6 = (boolean) row.get(j + 1);
                    v9 = (boolean) (rowDown != null ? rowDown.get(j + 1) : false);
                }
                v2 = (boolean) (rowUp != null ? rowUp.get(j) : false);
                v8 = (boolean) (rowDown != null ? rowDown.get(j) : false);
                room.add(v1);
                room.add(v2);
                room.add(v3);
                room.add(v4);
                room.add(v6);
                room.add(v7);
                room.add(v8);
                room.add(v9);
                current = (boolean) row.get(j);
                boolean result = LiveRules.isLive(room, current);
                tempRow.add(result);
            }
            tempField.add(tempRow);
        }
        veryOldField = oldField;
        oldField = field;
        field = tempField;

        dao.saveCurrentDay(INSTANCE);
        if (stepCounter > 2 && (isSame(field))) {
            throw new GameOfLiveException("GAME OVER\n");
        }
        return this;
    }

    @Override
    public List<List<Boolean>> getField() {
        return field;
    }

    @Override
    public PlayingField setField(List<List<Boolean>> field) {
        this.field = field;
        return this;
    }

    private boolean isSame(List<List<Boolean>> tempField) {
        if (veryOldField != null && oldField != null) {
            for (int i = 0; i < tempField.size(); i++) {
                List<Boolean> veryOldRow = veryOldField.get(i);
                List<Boolean> oldRow = oldField.get(i);
                List<Boolean> tempRow = tempField.get(i);
                for (int j = 0; j < tempRow.size(); j++) {
                    if (!tempRow.get(j).equals(oldRow.get(j)) && !tempRow.get(j).equals(veryOldRow.get(j))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int getStepCounter() {
        return stepCounter;
    }

}
