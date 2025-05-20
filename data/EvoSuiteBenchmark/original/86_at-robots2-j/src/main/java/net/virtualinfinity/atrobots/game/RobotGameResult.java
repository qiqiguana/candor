package net.virtualinfinity.atrobots.game;

import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.robot.RobotScore;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class RobotGameResult implements Comparable<RobotGameResult> {
    private final RobotFactory entrant;
    private final RobotScore finalScore;

    RobotGameResult(RobotFactory entrant, RobotScore finalScore) {
        this.entrant = entrant;
        this.finalScore = finalScore;
    }

    public RobotFactory getEntrant() {
        return entrant;
    }

    public RobotScore getFinalScore() {
        return finalScore;
    }

    public int compareTo(RobotGameResult o) {
        return RobotScore.RobotScoreComparator.compareScores(finalScore, o.finalScore);
    }

    @Override
    public String toString() {
        return "RobotGameResult{" +
                "entrant=" + entrant +
                ", finalScore=" + finalScore +
                '}';
    }
}
