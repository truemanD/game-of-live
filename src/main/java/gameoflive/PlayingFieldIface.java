package gameoflive;

import java.util.List;

public interface PlayingFieldIface {

    PlayingFieldIface generateRandom();

    PlayingFieldIface goToDay(int day) throws GameOfLiveException;

    PlayingFieldIface goNextDay() throws InterruptedException, GameOfLiveException;

    List<List<Boolean>> getField();

    PlayingFieldIface setField(List<List<Boolean>> field);

    int getStepCounter();
}
