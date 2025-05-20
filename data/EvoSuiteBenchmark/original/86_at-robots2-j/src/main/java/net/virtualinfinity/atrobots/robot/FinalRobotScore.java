package net.virtualinfinity.atrobots.robot;

/**
 * A constant (thread safe) robot score.
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public final class FinalRobotScore implements RobotScore {
    final int totalKills;
    final int totalDeaths;
    final int totalTies;
    final int totalWins;
    final double totalDamageInflicted;

    private FinalRobotScore(RobotScore score) {
        this.totalKills = score.getTotalKills();
        this.totalDeaths = score.getTotalDeaths();
        this.totalTies = score.getTotalTies();
        this.totalWins = score.getTotalWins();
        this.totalDamageInflicted = score.getTotalDamageInflicted();
    }

    public static RobotScore copyOf(RobotScore score) {
        if (score == null) {
            throw new NullPointerException();
        }
        if (score instanceof FinalRobotScore) {
            return score;
        }
        return new FinalRobotScore(score);
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalTies() {
        return totalTies;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public double getTotalDamageInflicted() {
        return totalDamageInflicted;
    }

    @Override
    public String toString() {
        return "Final Score: (" + totalWins + "," + totalTies + "," + totalKills + "," + totalDeaths + "," + totalDamageInflicted + ')';
    }
}
