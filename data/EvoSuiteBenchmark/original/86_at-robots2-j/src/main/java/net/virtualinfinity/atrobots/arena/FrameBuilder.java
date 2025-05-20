package net.virtualinfinity.atrobots.arena;

import net.virtualinfinity.atrobots.snapshots.ArenaObjectSnapshot;
import net.virtualinfinity.atrobots.snapshots.RobotSnapshot;

import java.util.ArrayList;

/**
 * @author Daniel Pitts
 */
public class FrameBuilder extends SimulationFrameBuffer {

    private SimulationFrame frameToBuild;

    public void beginFrame(boolean roundOver) {
        frameToBuild = new SimulationFrame(new ArrayList<ArenaObjectSnapshot>(), new ArrayList<RobotSnapshot>(), roundOver);
    }

    public void addObject(ArenaObjectSnapshot snapshot) {
        frameToBuild.add(snapshot);
    }


    public void endFrame() {
        setFrame(frameToBuild);
    }

}
