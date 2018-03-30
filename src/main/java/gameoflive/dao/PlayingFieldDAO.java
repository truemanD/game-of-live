package gameoflive.dao;

import gameoflive.PlayingFieldIface;

import java.util.List;

/**
 *
 * @author diyanov-a
 */
public interface PlayingFieldDAO {

    int saveCurrentDay(PlayingFieldIface pf);

    List<List<Boolean>> loadDay(int dayNumber);

}
