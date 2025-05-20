package net.virtualinfinity.atrobots.json;

import net.virtualinfinity.atrobots.measures.*;
import net.virtualinfinity.atrobots.snapshots.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class JsonToSnapshots {
    enum Types {
        missile {
            @Override
            public MissileSnapshot convert(JsonToSnapshots converter, JSONObject json) {
                return converter.getMissileSnapshot(json);
            }
        },
        mine {
            @Override
            public MineSnapshot convert(JsonToSnapshots converter, JSONObject json) throws JSONException {
                return converter.getMineSnapshot(json);
            }
        },
        robot {
            @Override
            public RobotSnapshot convert(JsonToSnapshots converter, JSONObject json) throws JSONException {
                return converter.getRobotSnapshot(json);
            }
        },
        scan {
            @Override
            public ScanSnapshot convert(JsonToSnapshots converter, JSONObject json) throws JSONException {
                return converter.getScanSnapshot(json);
            }
        },
        explosion {
            @Override
            public ExplosionSnapshot convert(JsonToSnapshots converter, JSONObject json) {
                return converter.getExplosionSnapshot(json);
            }
        },
        unknown {
            @Override
            public UnknownSnapshot convert(JsonToSnapshots converter, JSONObject json) {
                return new UnknownSnapshot(json.optString("type", ""));
            }
        };


        public abstract ArenaObjectSnapshot convert(JsonToSnapshots converter, JSONObject json) throws JSONException;
    }

    private ExplosionSnapshot getExplosionSnapshot(JSONObject json) {
        return new ExplosionSnapshot(json.optDouble("radius", 5.0), Duration.fromCycles(json.optInt("age", 0)));
    }

    public Collection<ArenaObjectSnapshot> getSnapshots(JSONArray array) {
        final List<ArenaObjectSnapshot> snapshots = new ArrayList<ArenaObjectSnapshot>(array.length());
        for (int i = 0, length = array.length(); i < length; ++i) {
            try {
                final JSONObject json = array.getJSONObject(i);
                Types types = Types.unknown;
                final String type = json.getString("type");
                for (Types match : Types.values()) {
                    if (match.name().equals(type)) {
                        types = match;
                        break;
                    }
                }
                final ArenaObjectSnapshot snapshot = types.convert(this, json);
                snapshot.setPositionVector(Vector.createCartesian(json.getDouble("x"), json.getDouble("y")));
                snapshot.setVelocityVector(Vector.createCartesian(json.optDouble("deltaX", 0), json.optDouble("deltaY", 0)));
                snapshot.setDead(json.optBoolean("dead", false));
                snapshots.add(snapshot);

            } catch (JSONException e) {
                // TODO: Report problem?
            }
        }
        return snapshots;
    }

    private RobotSnapshot getRobotSnapshot(JSONObject json) throws JSONException {
        final RobotSnapshot robotSnapshot = new RobotSnapshot();
        robotSnapshot.setId(json.getInt("id"));
        robotSnapshot.setName(json.optString("name"));
        robotSnapshot.setArmor(json.optLong("armor"));
        robotSnapshot.setTemperature(Temperature.fromLogScale(json.optLong("heat")));
        robotSnapshot.setHeading(AbsoluteAngle.fromRadians(json.optDouble("heading")));
        robotSnapshot.setTurretHeading(AbsoluteAngle.fromRadians(json.optDouble("turret")));
        robotSnapshot.setLastMessage(json.optString("message"));
        robotSnapshot.setRoundKills(json.getInt("roundKills"));
        robotSnapshot.setTotalKills(json.getInt("gameKills"));
        robotSnapshot.setTotalWins(json.getInt("gameWins"));
        robotSnapshot.setTotalTies(json.getInt("gameTies"));
        robotSnapshot.setTotalDeaths(json.getInt("gameDeaths"));
        robotSnapshot.setActiveShield("activated".equals(json.getString("shields")));
        robotSnapshot.setOverburn("activated".equals(json.getString("overburn")));
        return robotSnapshot;
    }

    private MineSnapshot getMineSnapshot(JSONObject json) throws JSONException {
        final MineSnapshot mineSnapshot = new MineSnapshot();
        mineSnapshot.setTriggerRadius(json.getDouble("triggerRadius"));
        return mineSnapshot;
    }

    private MissileSnapshot getMissileSnapshot(JSONObject json) {
        return new MissileSnapshot("activated".equals(json.optString("overburn", "inactive")), Duration.fromCycles(json.optInt("age")));
    }

    private ScanSnapshot getScanSnapshot(JSONObject json) throws JSONException {
        final JSONObject sector = json.getJSONObject("sector");
        final boolean successful = json.optBoolean("successful");
        final JSONObject accuracy = json.optJSONObject("accuracy");
        final boolean accuracyAvailable = accuracy != null;
        return new ScanSnapshot(getAngleBracket(sector), sector.getDouble("radius"), successful, getTargetVector(json, successful), accuracyAvailable, accuracyAvailable ? accuracy.optInt("value") : 0);
    }

    private Vector getTargetVector(JSONObject json, boolean successful) throws JSONException {
        return successful ? Vector.createCartesian(json.getDouble("targetX"), json.getDouble("targetY")) : null;
    }

    private AngleBracket getAngleBracket(JSONObject sector) throws JSONException {
        if ("circle".equals(sector.getString("shape"))) {
            return AngleBracket.all();
        }
        return AngleBracket.clockwiseFrom(
                AbsoluteAngle.fromRadians(sector.getDouble("counterClockwiseBound")),
                RelativeAngle.fromRadians(sector.getDouble("rangeSize")));
    }

}
