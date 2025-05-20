/*
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties.
 *
 * @author: Rasmus Ahlberg
 *
 */

package module;

import state.*;

public interface RuleSet {

    /**
     * Checks if the action is a legal action according to this ruleset.
     *
     * @return <i>true</i> if the action is legal, otherwise <i>false</i>.
     */
    public boolean checkRules(Action action, GameState state);
}
