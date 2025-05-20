package net.virtualinfinity.atrobots.robot;

import java.util.Comparator;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface RobotScore {
    int getTotalKills();

    int getTotalDeaths();

    int getTotalTies();

    int getTotalWins();

    double getTotalDamageInflicted();

    static class RobotScoreComparator<T extends RobotScore> implements Comparator<T> {
        public int compare(T o1, T o2) {
            return compareScores(o1, o2);
        }

        public static int compareScores(RobotScore o1, RobotScore o2) {
            return compare(o1.getTotalWins(), o2.getTotalWins(), 0x100000)
                    + compare(o1.getTotalTies(), o2.getTotalTies(), 0x10000)
                    + compare(o1.getTotalKills(), o2.getTotalKills(), 0x1000)
                    + compare(o1.getTotalDeaths(), o2.getTotalDeaths(), -0x100)
                    + compare(o1.getTotalDamageInflicted(), o2.getTotalDamageInflicted(), 0x10);
        }

        private static int compare(int left, int right, int significance) {
            return left < right ? -significance : left == right ? 0 : significance;
        }

        private static int compare(double left, double right, int significance) {
            final int compare = Double.compare(left, right);
            return compare < 0 ? -significance : compare == 0 ? 0 : significance;
        }
    }
}
