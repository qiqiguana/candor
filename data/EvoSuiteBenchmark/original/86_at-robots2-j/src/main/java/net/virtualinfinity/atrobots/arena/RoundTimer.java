package net.virtualinfinity.atrobots.arena;

import net.virtualinfinity.atrobots.measures.Duration;

/**
 * An object which keeps track of the number of cycles in a round.
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class RoundTimer {
    private Duration time = Duration.ZERO_CYCLE;

    public Duration getTime() {
        return time;
    }

    public void increment(Duration change) {
        time = time.plus(change);
    }
}
