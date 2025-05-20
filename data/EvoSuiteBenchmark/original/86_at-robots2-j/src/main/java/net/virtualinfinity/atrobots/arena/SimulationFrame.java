package net.virtualinfinity.atrobots.arena;

import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;
import net.virtualinfinity.atrobots.snapshots.RobotSnapshot;
import net.virtualinfinity.atrobots.snapshots.SnapshotAdaptor;
import net.virtualinfinity.atrobots.snapshots.SnapshotVisitor;

import java.util.Collection;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class SimulationFrame {
    private final Collection<ArenaObjectSnapshot> allObjects;
    private final Collection<RobotSnapshot> robots;
    private final boolean roundOver;
    private SnapshotVisitor robotFilter = new SnapshotAdaptor() {
        @Override
        public void acceptRobot(RobotSnapshot robotSnapshot) {
            SimulationFrame.this.robots.add(robotSnapshot);
        }
    };

    public SimulationFrame(Collection<ArenaObjectSnapshot> allObjects, Collection<RobotSnapshot> robots, boolean roundOver) {
        this.allObjects = allObjects;
        this.robots = robots;
        this.roundOver = roundOver;
    }

    public void visitAll(SnapshotVisitor visitor) {
        for (ArenaObjectSnapshot snapshot : allObjects) {
            snapshot.visit(visitor);
        }
    }

    public void visitRobots(SnapshotVisitor visitor) {
        for (RobotSnapshot snapshot : robots) {
            snapshot.visit(visitor);
        }
    }

    void add(ArenaObjectSnapshot snapshot) {
        allObjects.add(snapshot);
        snapshot.visit(robotFilter);
    }

    public Collection<ArenaObjectSnapshot> getAllObjects() {
        return allObjects;
    }


    public boolean isRoundOver() {
        return roundOver;
    }
}
