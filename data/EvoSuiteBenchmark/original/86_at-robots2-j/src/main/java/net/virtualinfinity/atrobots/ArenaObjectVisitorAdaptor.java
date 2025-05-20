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
public class ArenaObjectVisitorAdaptor implements ArenaObjectVisitor {
    public void visit(Robot robot) {
    }

    public void visit(Missile missile) {
    }

    public void visit(Mine mine) {
    }

    public void visit(Explosion explosion) {
    }

    public void visit(Scan scan) {
    }
}
