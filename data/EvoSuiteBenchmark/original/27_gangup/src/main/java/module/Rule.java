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

public interface Rule {
    public boolean checkRule(Action action, GameState state);
}
