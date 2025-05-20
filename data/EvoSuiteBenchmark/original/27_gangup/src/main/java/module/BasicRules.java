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

public class BasicRules implements RuleSet {
    BasicRules() {
    }

    public boolean checkRules(Action action, GameState state) {
	Player actor = state.player(action.getActor());
	Player target = state.player(action.getTarget());

	if (state.getGameState() == GameState.STATE_WAITING)
	    return false;

	if (actor == null || actor.isDead())
	    return false;
	if (action.getAction() != Action.ACTION_PART &&
	    action.getAction() != Action.ACTION_MOVE &&
	    (target == null || target.isDead())) {
	    return false;
	}

	switch (action.getAction()) {
	case Action.ACTION_PART:
	    if (actor.isBoss())
		return false;
	    break;
	case Action.ACTION_ATTACK:
	    if (state.getGameState() == GameState.STATE_WARMUP)
		return false;
	    if (actor == target)
		return false;
	    if (!actor.isBoss())
		return false;
	    if (!target.isBoss())
		return false;
	    break;
	case Action.ACTION_JOIN:
	    if (actor == target)
		return false;
	    if (!actor.isBoss())
		return false;
	    if (target.gangBoss() == actor)
		return false;
	    break;
	case Action.ACTION_JOIN_APPLY:
	    if (actor == target)
		return false;
	    if (!actor.isBoss())
		return false;
	    if (target.gangBoss() == actor)
		return false;
	    break;
	case Action.ACTION_JOIN_INVITE:
	    if (actor == target)
		return false;
	    if (!target.isBoss())
		return false;
	    if (actor.gangBoss() == target)
		return false;
	    break;
	case Action.ACTION_JOIN_AGREE:
	    if (actor == target)
		return false;
	    if (!actor.isBoss())
		return false;
	    if (!target.isJoinOK(actor,true))
		return false;
	    break;
	case Action.ACTION_JOIN_ALLOW:
	    if (actor == target)
		return false;
	    if (!target.isBoss())
		return false;
	    if (!target.isJoinOK(actor,false))
		return false;
	    break;
	case Action.ACTION_KICK:
	    if (actor == target)
		return false;
	    if (actor != target.boss)
		return false;
	    break;
	}

	return true;
    }
}
