package net.virtualinfinity.atrobots.robot;

public class RobotScoreKeeper implements RobotListener, RobotScore {
    volatile int totalTies;
    volatile int totalWins;
    volatile int totalKills;
    volatile int totalDeaths;
    volatile double totalDamageInflicted;

    /**
     * Get the total number of kills this entrant has earned during this game.
     *
     * @return the number of kills.
     */
    public int getTotalKills() {
        return totalKills;
    }

    /**
     * Get the total number of times the entrant has died this game.
     *
     * @return the number of deaths.
     */
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

    public void wonRound(Robot robot) {
        synchronized (this) {
            totalWins++;
        }
    }

    public void tiedRound(Robot robot) {
        synchronized (this) {
            totalTies++;
        }
    }

    public void killedRobot(Robot robot) {
        synchronized (this) {
            totalKills++;
        }
    }

    public void died(Robot robot) {
        synchronized (this) {
            totalDeaths++;
        }
    }

    public void inflictedDamage(Robot robot, double amount) {
        synchronized (this) {
            totalDamageInflicted += amount;
        }
    }

    @Override
    public String toString() {
        return "RobotScoreKeeper{" +
                "totalTies=" + totalTies +
                ", totalWins=" + totalWins +
                ", totalKills=" + totalKills +
                ", totalDeaths=" + totalDeaths +
                ", totalDamageInflicted=" + totalDamageInflicted +
                '}';
    }
}