package net.virtualinfinity.atrobots;

import net.virtualinfinity.atrobots.arena.Explosion;
import net.virtualinfinity.atrobots.arena.Scan;
import net.virtualinfinity.atrobots.hardware.mines.Mine;
import net.virtualinfinity.atrobots.hardware.missiles.Missile;
import net.virtualinfinity.atrobots.robot.Robot;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface ArenaObjectVisitor {
    void visit(Robot robot);

    void visit(Missile missile);

    void visit(Mine mine);

    void visit(Explosion explosion);

    void visit(Scan scan);
}
