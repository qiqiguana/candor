package net.virtualinfinity.atrobots.arenaobjects;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface DamageInflicter {
    void killedRobot();

    void inflictedDamage(double amount);
}
