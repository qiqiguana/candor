package net.virtualinfinity.atrobots.tournament;

import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.game.GameResult;
import net.virtualinfinity.atrobots.game.RobotGameResult;
import net.virtualinfinity.atrobots.robot.RobotScore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class PairTournamentResults {
    private final Map<RobotFactory, RobotPairTournamentResult> scoreBoard = new HashMap<RobotFactory, RobotPairTournamentResult>();

    public synchronized void recordScores(GameResult finalResults) {
        if (finalResults.getResults().size() != 2) {
            throw new IllegalArgumentException("Expected a game result with two participants. Got " + finalResults.getResults().size() + " instead.");
        }
        recordScores(finalResults.getResults().get(0), finalResults.getResults().get(1).getEntrant());
        recordScores(finalResults.getResults().get(1), finalResults.getResults().get(0).getEntrant());
    }

    private void recordScores(RobotGameResult scorer, RobotFactory opponent) {
        RobotPairTournamentResult robotPairTournamentResult = scoreBoard.get(scorer.getEntrant());
        if (robotPairTournamentResult == null) {
            robotPairTournamentResult = new RobotPairTournamentResult();
            scoreBoard.put(scorer.getEntrant(), robotPairTournamentResult);
        }
        robotPairTournamentResult.recordScore(scorer, opponent);
    }

    public RobotScore getScore(RobotFactory scorer, RobotFactory opponent) {
        return scoreBoard.get(opponent).getResult(scorer);
    }

    private static class RobotPairTournamentResult {
        final Map<RobotFactory, RobotScore> result = new HashMap<RobotFactory, RobotScore>();

        public void recordScore(RobotGameResult scorer, RobotFactory opponent) {
            if (result.put(opponent, scorer.getFinalScore()) != null) {
                throw new IllegalArgumentException("Oh no! Battled the same robot twice.");
            }
        }

        public RobotScore getResult(RobotFactory scorer) {
            return result.get(scorer);
        }

        @Override
        public String toString() {
            return "RobotPairTournamentResult{result=" + result + '}';
        }
    }
}
