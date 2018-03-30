package gameoflive;

import java.util.List;

/**
 *
 * @author diyanov-a
 */
public class LiveRules {

    public static boolean isLive(List<Boolean> room, boolean current) {
        boolean result = false;
        if (current) {
            int liveNeighborsCounter = 0;
            for (boolean temp : room) {
                if (temp) {
                    liveNeighborsCounter++;
                }
            }
            if (liveNeighborsCounter == 3 || liveNeighborsCounter == 2) {
                result = true;
            } else {
                result = false;
            }
        }
        if (!current) {
            int liveNeighborsCounter = 0;
            for (boolean temp : room) {
                if (temp) {
                    liveNeighborsCounter++;
                }
            }
            if (liveNeighborsCounter == 3) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }
}
