package net.virtualinfinity.atrobots.arena;


import net.virtualinfinity.atrobots.arenaobjects.ArenaObject;
import net.virtualinfinity.atrobots.measures.Vector;

/**
 * @author Daniel Pitts
 */
public class LinearDamageFunction extends ExplosionFunction {
    private final double multiplier;
    private final Position position;
    private final double damageAtCenter;

    public LinearDamageFunction(Position position, double multiplier, double damageAtCenter) {
        this.position = position;
        this.multiplier = multiplier;
        this.damageAtCenter = damageAtCenter;
    }

    public double getDamageAmount(ArenaObject object) {
        final double distance = object.getPosition().getVectorTo(position).getMagnitude();
        return Math.max(0, (damageAtCenter - distance) * multiplier);
    }

    public Vector getCenter() {
        return position.getVector();
    }

    public double getRadius() {
        return (damageAtCenter);
    }

}
