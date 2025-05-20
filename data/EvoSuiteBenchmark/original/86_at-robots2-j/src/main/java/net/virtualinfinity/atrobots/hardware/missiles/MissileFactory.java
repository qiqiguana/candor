package net.virtualinfinity.atrobots.hardware.missiles;

import net.virtualinfinity.atrobots.arena.Position;
import net.virtualinfinity.atrobots.measures.AbsoluteAngle;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface MissileFactory {
    Missile createMissile(AbsoluteAngle missileHeading, Position position, double power);
}
