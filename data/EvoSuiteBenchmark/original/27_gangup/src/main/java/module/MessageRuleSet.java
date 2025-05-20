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

public interface MessageRuleSet {

    /**
     * Checks if the message has a legal target according to this ruleset.
     *
     * @return <i>true</i> if the message is legal, otherwise <i>false</i>.
     */
    public boolean checkRules(TextMessage textm, GameState state);
}
