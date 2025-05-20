package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.arena.Heading;
import net.virtualinfinity.atrobots.atsetup.AtRobotPort;
import net.virtualinfinity.atrobots.hardware.HasHeading;
import net.virtualinfinity.atrobots.hardware.transponder.Transponder;
import net.virtualinfinity.atrobots.measures.Heat;
import net.virtualinfinity.atrobots.measures.RelativeAngle;
import net.virtualinfinity.atrobots.ports.PortHandler;
import net.virtualinfinity.atrobots.ports.PortListener;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.util.MapWithDefaultValue;

import java.util.*;

import static net.virtualinfinity.atrobots.atsetup.AtRobotPort.*;

/**
 * Creates the port mapping for a standard AT-Robot configuration.
 *
 * @author Daniel Pitts
 */
public class AtRobotPortFactory {

    public static PortHandler getCompass(final Heading heading) {
        return new PortHandler() {
            public short read() {
                return (short) heading.getAngle().getBygrees();
            }
        };
    }

    public static PortHandler getRotationPort(final Heading heading) {
        return new PortHandler() {
            public void write(short value) {
                heading.rotate(RelativeAngle.fromBygrees(value));
            }
        };
    }

    public Map<Integer, PortHandler> createPortHandlers(Robot robot) {
        final Map<Integer, PortHandler> ports = new HashMap<Integer, PortHandler>();
        mapPort(ports, SPEDOMETER, robot.getThrottle().getSpedometer());
        mapPort(ports, HEAT, getTemperatureSensor(robot.getHeatSinks()));
        mapPort(ports, COMPASS, getCompass(robot));
        mapPort(ports, TURRET_OFS, robot.getTurretOffsetSensor());
        mapPort(ports, TURRET_ABS, getCompass(robot.getTurret()));
        mapPort(ports, DAMAGE, robot.getArmor().getSensor());
        mapPort(ports, SCAN, robot.getTurret().getScanner().getScanPort());
        mapPort(ports, ACCURACY, robot.getTurret().getScanner().getAccuracyPort());
        mapPort(ports, RADAR, robot.getRadar().getScanPort());
        mapPort(ports, RANDOM, createRandomNumberGenerator());
        mapPort(ports, THROTTLE, robot.getThrottle().getActuator());
        mapPort(ports, OFS_TURRET, getRotationPort(robot.getTurret().getHeading()));
        mapPort(ports, ABS_TURRET, robot.getAimTurretPort());
        mapPort(ports, STEERING, getRotationPort(robot.getDesiredHeading()));
        mapPort(ports, WEAP, robot.getTurret().getMissileLauncher().getActuator());
        mapPort(ports, SONAR, robot.getSonar().getScanPort());
        mapPort(ports, ARC, robot.getTurret().getScanner().getScanArcLatchPort());
        mapPort(ports, OVERBURN, robot.getOverburnLatchPort());
        mapPort(ports, TRANSPONDER, getTransponderLatchPort(robot.getTransponder()));
        mapPort(ports, SHUTDOWN, robot.getShutdownLevelLatchPort());
        mapPort(ports, CHANNEL, robot.getTransceiver().getChannelLatchPort());
        mapPort(ports, MINELAYER, robot.getMineLayer().getMineBayPort());
        mapPort(ports, MINETRIGGER, robot.getMineLayer().getPlacedMinePort());
        mapPort(ports, SHIELD, robot.getShield().getLatch());
        connectPortHandlers(ports.values(), robot.getComputer());
        return new MapWithDefaultValue<Integer, PortHandler>(Collections.unmodifiableMap(ports), robot.getComputer().createDefaultPortHandler());
    }

    private PortHandler getTransponderLatchPort(final Transponder transponder) {
        return new PortHandler() {
            public short read() {
                return (short) transponder.getId();
            }

            public void write(short value) {
                transponder.setId(value);
            }
        };
    }

    private PortHandler getTemperatureSensor(final Heat heat) {
        return new PortHandler() {
            public short read() {
                return (short) heat.getTemperature().getLogScale();
            }
        };
    }

    private PortHandler getCompass(HasHeading hasHeading) {
        return getCompass(hasHeading.getHeading());
    }

    private void connectPortHandlers(Collection<PortHandler> collection, PortListener portListener) {
        for (PortHandler handler : collection) {
            handler.setPortListener(portListener);
        }
    }

    private void mapPort(Map<Integer, PortHandler> ports, AtRobotPort port, PortHandler portHandler) {
        ports.put(port.portNumber, portHandler);
    }

    private PortHandler createRandomNumberGenerator() {
        return new PortHandler() {
            private final Random random = new Random();

            public short read() {
                return (short) random.nextInt();
            }
        };
    }
}
