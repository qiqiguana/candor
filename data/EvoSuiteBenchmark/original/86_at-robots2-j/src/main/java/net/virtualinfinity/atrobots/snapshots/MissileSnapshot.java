package net.virtualinfinity.atrobots.snapshots;

import net.virtualinfinity.atrobots.measures.Duration;

/**
 * @author Daniel Pitts
 */
public class MissileSnapshot extends ArenaObjectSnapshot {
    private final boolean overburn;
    private final Duration age;

    public MissileSnapshot(boolean overburn, Duration age) {
        this.overburn = overburn;
        this.age = age;
    }

    public void visit(SnapshotVisitor visitor) {
        visitor.acceptMissile(this);
    }

    public boolean isOverburn() {
        return overburn;
    }

    public Duration getAge() {
        return age;
    }
}
