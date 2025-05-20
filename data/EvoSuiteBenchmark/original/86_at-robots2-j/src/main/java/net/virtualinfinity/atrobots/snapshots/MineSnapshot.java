package net.virtualinfinity.atrobots.snapshots;


/**
 * @author Daniel Pitts
 */
public class MineSnapshot extends ArenaObjectSnapshot {
    private double triggerRadius;

    public void setTriggerRadius(double triggerRadius) {
        this.triggerRadius = triggerRadius;
    }

    public void visit(SnapshotVisitor visitor) {
        visitor.acceptMine(this);
    }

    public double getTriggerRadius() {
        return triggerRadius;
    }
}
