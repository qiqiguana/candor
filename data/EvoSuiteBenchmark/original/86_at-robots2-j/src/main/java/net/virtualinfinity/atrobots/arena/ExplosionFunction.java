package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.arenaobjects.DamageInflicter;
import net.virtualinfinity.atrobots.measures.Vector;

/**
 * @author Daniel Pitts
 */
public abstract class ExplosionFunction {
    public void inflictDamage(DamageInflicter cause, TangibleArenaObject robot) {
        final double amount = getDamageAmount(robot);
        if (amount > 0) {
            robot.inflictDamage(cause, amount);
        }
    }

    public abstract double getDamageAmount(ArenaObject object);

    public abstract Vector getCenter();

    public abstract double getRadius();
}
