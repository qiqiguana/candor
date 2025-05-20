package net.virtualinfinity.atrobots.hardware.armor;

import net.virtualinfinity.atrobots.ports.PortHandler;

/**
 * Represents the armor of a robot.
 *
 * @author Daniel Pitts
 */
public class Armor {
    private double pointsRemaining;
    private ArmorDepletionListener armorDepletionListener;

    /**
     * Construct armor with the specific number of points remaining.
     *
     * @param points the number points this armor starts with.
     */
    public Armor(double points) {
        this.pointsRemaining = points;
    }

    /**
     * Get a port handler which reads the number of remaining armor points.
     *
     * @return the Armor sensor PortHandler.
     */
    public PortHandler getSensor() {
        return new PortHandler() {
            public short read() {
                return (short) Math.round(getRemaining());
            }
        };
    }

    /**
     * Destroy this robot by inflicting the damage equal to the current remaining points.
     */
    public void destruct() {
        inflictDamage(getRemaining());
    }

    private void checkDead() {
        if (pointsRemaining <= 0) {
            armorDepletionListener.armorDepleted();
        }
    }

    /**
     * Get the remaining armor.
     *
     * @return the number of points remaining.
     */
    public double getRemaining() {
        return pointsRemaining;
    }

    /**
     * Inflict the specific amount of damage. Causes the robot to explode if the points drop to 0 or below.
     *
     * @param damageAmount the amount of damage to inflict.
     */
    public void inflictDamage(double damageAmount) {
        pointsRemaining -= damageAmount;
        checkDead();
    }

    public void setArmorDepletionListener(ArmorDepletionListener armorDepletionListener) {
        this.armorDepletionListener = armorDepletionListener;
    }
}
