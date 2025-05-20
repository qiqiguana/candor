/*
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties.
 *
 * @author: Rasmus Ahlberg
 *
 */

package module;
import java.util.*;
import state.*;

public class BasicMessageRules implements MessageRuleSet {
    BasicMessageRules() {
    }

    public boolean checkRules(TextMessage textm, GameState state) {
	Player actor = state.player(textm.getActor());
	Player target = state.player(textm.getTarget());

	if (actor == null)
	    return false;
	// dead players don't have gangs
	if (actor.isDead() && textm.getMessageType() == TextMessage.GROUP)
	    return false;
	if (textm.getMessageType() == TextMessage.PRIVATE) {
	    // not allowed to message yourself
	    if (actor == target)
		return false;
	    // target not allowed to be null for priv message.
	    if (target == null)
		return false;
	    // make sure target is on the same side of river Styx.
	    if (target.isDead() != actor.isDead())
		return false;
	}

	return true;
    }
}
