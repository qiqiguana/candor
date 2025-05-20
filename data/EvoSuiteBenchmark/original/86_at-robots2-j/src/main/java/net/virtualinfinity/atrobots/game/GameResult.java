package net.virtualinfinity.atrobots.game;

import java.util.Collections;
import java.util.List;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class GameResult {
    private final List<RobotGameResult> results;

    GameResult(List<RobotGameResult> results) {
        this.results = Collections.unmodifiableList(results);
    }

    public List<RobotGameResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "results=" + results +
                '}';
    }
}
