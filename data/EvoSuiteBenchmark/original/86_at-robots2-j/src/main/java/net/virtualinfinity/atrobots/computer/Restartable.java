package net.virtualinfinity.atrobots.computer;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public interface Restartable extends ShutdownListener {
    boolean isShutDown();

    void startUp();
}
