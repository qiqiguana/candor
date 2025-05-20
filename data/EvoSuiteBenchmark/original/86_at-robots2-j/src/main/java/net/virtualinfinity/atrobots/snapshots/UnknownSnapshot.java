package net.virtualinfinity.atrobots.snapshots;

/**
 * For handling unknown snapshots sent by the server.
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class UnknownSnapshot extends ArenaObjectSnapshot {
    private final String type;

    public UnknownSnapshot(String type) {
        this.type = type;
    }

    @Override
    public void visit(SnapshotVisitor visitor) {
        visitor.acceptUnknown(this);
    }
}
