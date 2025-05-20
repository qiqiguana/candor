package net.virtualinfinity.atrobots.hardware.missiles;

import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.arena.Heading;
import net.virtualinfinity.atrobots.arena.Position;
import net.virtualinfinity.atrobots.arenaobjects.DamageInflicter;
import net.virtualinfinity.atrobots.hardware.HasOverburner;
import net.virtualinfinity.atrobots.hardware.heatsinks.HeatSinks;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;
import net.virtualinfinity.atrobots.measures.RelativeAngle;
import net.virtualinfinity.atrobots.measures.Temperature;
import net.virtualinfinity.atrobots.ports.PortHandler;

/**
 * @author Daniel Pitts
 */
public class MissileLauncher {
    private Heading heading;
    private double power;
    private Position position;
    private HeatSinks heatSinks;
    private HasOverburner overburner;
    private Arena arena;
    private DamageInflicter damageInflicter;

    public MissileLauncher() {
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PortHandler getActuator() {
        return new PortHandler() {
            public void write(short value) {
                consumeCycles(3);
                fireMissile(RelativeAngle.fromBygrees(Math.max(-4, Math.min(value, 4))));
            }
        };
    }

    private void fireMissile(RelativeAngle shift) {
        getArena().addCollidable(createMissile(shift));
        getHeatSinks().warm(getFiringTempurature());
    }

    private AbsoluteAngle getMissileHeading(RelativeAngle shift) {
        return heading.getAngle().counterClockwise(shift);
    }

    private Temperature getFiringTempurature() {
        return Temperature.fromLogScale(overburner.isOverburn() ? 20 : 30);
    }

    private Missile createMissile(RelativeAngle shift) {
        return new Missile(damageInflicter, position, getMissileHeading(shift), getPower(), overburner.isOverburn());
    }

    private double getPower() {
        return overburner.isOverburn() ? power * 1.30 : power;
    }

    public HeatSinks getHeatSinks() {
        return heatSinks;
    }

    public void setHeatSinks(HeatSinks heatSinks) {
        this.heatSinks = heatSinks;
    }

    public DamageInflicter getDamageInflicter() {
        return this.damageInflicter;
    }

    public void setDamageInflicter(DamageInflicter damageInflicter) {
        this.damageInflicter = damageInflicter;
    }

    public HasOverburner getOverburner() {
        return overburner;
    }

    public void setOverburner(HasOverburner overburner) {
        this.overburner = overburner;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
