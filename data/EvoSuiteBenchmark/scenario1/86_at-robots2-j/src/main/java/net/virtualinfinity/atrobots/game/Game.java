package net.virtualinfinity.atrobots.game;

import net.virtualinfinity.atrobots.arena.FrameBuilder;
import net.virtualinfinity.atrobots.arena.RoundState;
import net.virtualinfinity.atrobots.arena.SimulationObserver;
import net.virtualinfinity.atrobots.compiler.RobotFactory;
import net.virtualinfinity.atrobots.robot.FinalRobotScore;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.robot.RobotScore;
import net.virtualinfinity.atrobots.robot.RobotScoreKeeper;
import java.util.*;

/**
 * This class coordinates rounds, entrants, and the simulation frame buffer.
 *
 * @author Daniel Pitts
 */
public class Game implements RoundListener {

    public synchronized boolean stepRound() {
        if (round == null) {
            return false;
        }
        getRound().step();
        return round != null;
    }
}
