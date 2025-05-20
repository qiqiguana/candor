package net.virtualinfinity.atrobots.robot;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface RobotListener {
    void wonRound(Robot robot);

    void tiedRound(Robot robot);

    void killedRobot(Robot robot);

    void died(Robot robot);

    void inflictedDamage(Robot robot, double amount);
}
