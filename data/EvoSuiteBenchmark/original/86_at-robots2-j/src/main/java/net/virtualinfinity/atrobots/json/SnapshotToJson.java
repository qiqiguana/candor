package net.virtualinfinity.atrobots.json;

import net.virtualinfinity.atrobots.measures.AngleBracket;
import net.virtualinfinity.atrobots.snapshots.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class SnapshotToJson implements SnapshotVisitor {
    private JSONArray snapshots = new JSONArray();

    public JSONObject getBasicJson(ArenaObjectSnapshot snapshot, String type) throws JSONException {
        return getBasicJson(snapshot, type, true);
    }

    public JSONObject getBasicJson(ArenaObjectSnapshot snapshot, String type, boolean includeVelocity) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("x", snapshot.getX());
        jsonObject.put("y", snapshot.getY());
        if (includeVelocity) {
            jsonObject.put("deltaX", snapshot.getVelocityX());
            jsonObject.put("deltaY", snapshot.getVelocityY());
        }
        jsonObject.put("status", snapshot.isDead() ? "dead" : "alive");

        return jsonObject;
    }

    public void acceptRobot(RobotSnapshot robotSnapshot) {
        try {
            snapshots.put(
                    getBasicJson(robotSnapshot, "robot")
                            .put("id", robotSnapshot.getId())
                            .put("name", robotSnapshot.getName())
                            .put("armor", Math.round(robotSnapshot.getArmor()))
                            .put("heat", Math.round(robotSnapshot.getTemperature().getLogScale()))
                            .put("heading", robotSnapshot.getHeading().getNormalizedRadians())
                            .put("turret", robotSnapshot.getTurretHeading().getNormalizedRadians())
                            .put("message", robotSnapshot.getLastMessage())
                            .put("roundKills", robotSnapshot.getRoundKills())
                            .put("gameKills", robotSnapshot.getTotalKills())
                            .put("gameWins", robotSnapshot.getTotalWins())
                            .put("gameTies", robotSnapshot.getTotalTies())
                            .put("gameDeaths", robotSnapshot.getTotalDeaths())
                            .put("shields", robotSnapshot.isActiveShield() ? "activated" : "inactive")
                            .put("overburn", robotSnapshot.isOverburn() ? "activated" : "inactive")
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptMissile(MissileSnapshot missileSnapshot) {
        try {
            snapshots.put(
                    getBasicJson(missileSnapshot, "missile")
                            .put("age", missileSnapshot.getAge().getCycles())
                            .put("overburn", missileSnapshot.isOverburn() ? "activated" : "inactive")
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptMine(MineSnapshot mineSnapshot) {
        try {
            snapshots.put(
                    getBasicJson(mineSnapshot, "mine", false)
                            .put("triggerRadius", mineSnapshot.getTriggerRadius())
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public void acceptExplosion(ExplosionSnapshot explosionSnapshot) {
        try {
            snapshots.put(
                    getBasicJson(explosionSnapshot, "explosion", false)
                            .put("age", explosionSnapshot.getAge().getCycles())
                            .put("initialRadius", explosionSnapshot.getRadius())
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptScan(ScanSnapshot scanSnapshot) {
        try {
            final JSONObject scan = getBasicJson(scanSnapshot, "scan", false)
                    .put("sector", getAngleBracket(scanSnapshot.getAngleBracket()).put("radius", scanSnapshot.getMaxDistance())
                    );
            scan.put("successful", scanSnapshot.isSuccessful());
            if (scanSnapshot.isSuccessful()) {
                scan.put("targetX", scanSnapshot.getMatchVector().getX());
                scan.put("targetY", scanSnapshot.getMatchVector().getY());
            }
            if (scanSnapshot.isAccuracyAvailable()) {
                scan.put("accuracy", new JSONObject()
                        .put("value", scanSnapshot.getAccuracy()))
                        .put("sector", getAngleBracket(scanSnapshot.getAccuracyBracket()));
            }
            snapshots.put(scan);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptUnknown(UnknownSnapshot unknownSnapshot) {
        try {
            snapshots.put(getBasicJson(unknownSnapshot, "unknown"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONObject getAngleBracket(AngleBracket angleBracket) throws JSONException {
        if (angleBracket.getCounterClockwiseBound() == null) {
            return new JSONObject().put("shape", "circle");
        }
        return new JSONObject()
                .put("shape", "wedge")
                .put("rangeSize", angleBracket.getRangeSize().getRadians())
                .put("counterClockwiseBound", angleBracket.getCounterClockwiseBound().getNormalizedRadians())
                ;
    }

    public JSONArray getSnapshots() {
        return snapshots;
    }
}
