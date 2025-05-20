package net.virtualinfinity.atrobots.arenaobjects;

import net.virtualinfinity.atrobots.arena.TangibleArenaObject;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public abstract class CollidableArenaObject extends ArenaObject {
    public abstract void checkCollision(TangibleArenaObject arenaObject);

}
