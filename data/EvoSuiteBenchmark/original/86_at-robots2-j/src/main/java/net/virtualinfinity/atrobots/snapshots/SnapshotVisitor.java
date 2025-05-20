package net.virtualinfinity.atrobots.snapshots;

/**
 * @author Daniel Pitts
 */
public interface SnapshotVisitor {
    void acceptRobot(RobotSnapshot robotSnapshot);

    void acceptMissile(MissileSnapshot missileSnapshot);

    void acceptMine(MineSnapshot mineSnapshot);

    void acceptExplosion(ExplosionSnapshot explosionSnapshot);

    void acceptScan(ScanSnapshot scanSnapshot);

    void acceptUnknown(UnknownSnapshot unknownSnapshot);
}
