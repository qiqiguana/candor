package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.arena.Arena;
import net.virtualinfinity.atrobots.arena.RoundState;
import net.virtualinfinity.atrobots.arena.RoundTimer;
import net.virtualinfinity.atrobots.atsetup.AtRobotInterrupt;
import net.virtualinfinity.atrobots.computer.InterruptHandler;
import net.virtualinfinity.atrobots.computer.MemoryCell;
import net.virtualinfinity.atrobots.computer.Registers;
import net.virtualinfinity.atrobots.interrupts.*;
import net.virtualinfinity.atrobots.robot.Robot;
import net.virtualinfinity.atrobots.util.MapWithDefaultValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static net.virtualinfinity.atrobots.atsetup.AtRobotInterrupt.*;

/**
 * Creates built-in interrupt handlers for the standard AT-Robots set up.
 *
 * @author Daniel Pitts
 */
public class AtRobotInterruptFactory {

    private InterruptHandler createResetMetersInterrupt(Robot robot) {
        return new ResetMetersInterrupt(getRegisters(robot).getMeters(), robot.getOdometer());
    }

    private InterruptHandler createGetRobotStatisticsInterrupt(Robot robot) {
        return robot.createGetRobotStatisticsInterrupt(getDxCell(robot), getExCell(robot), getFxCell(robot));
    }

    private MemoryCell getDxCell(Robot robot) {
        return getRegisters(robot).getDx();
    }

    private InterruptHandler createResetQueueInterrupt(Robot robot) {
        return new ZeroMemoryCellsInterrupt(getRegisters(robot).getCommunicationQueueHead(),
                getRegisters(robot).getCommunicationQueueTail());
    }

    private InterruptHandler createGetQueueSizeInterrupt(Robot robot) {
        return new GetQueueSizeInterrupt(robot.getComputer().getCommQueue(), getFxCell(robot));
    }

    private InterruptHandler createRecieveInterrupt(Robot robot) {
        return new ReceiveInterrupt(robot.getComputer().getCommQueue(), getFxCell(robot));
    }

    private InterruptHandler createTransmitInterrupt(Robot robot) {
        return new TransmitInterrupt(robot.getTransceiver(), getAxCell(robot));
    }

    private InterruptHandler createResetCollisionCountInterrupt(Robot robot) {
        return new ZeroMemoryCellsInterrupt(getRegisters(robot).getCollisionCount());
    }

    private InterruptHandler createGetCollisionsInterrupt(Robot robot) {
        return new CopyMemoryCellsInterrupt(getRegisters(robot).getCollisionCount(), getFxCell(robot));
    }

    private InterruptHandler createGetRobotInfoInterrupt(Robot robot) {
        return robot.createGetRobotInfoInterruptHandler(getDxCell(robot), getExCell(robot), getFxCell(robot));
    }

    private InterruptHandler createGetGameInfoInterrupt(Robot robot, Arena arena, RoundState roundState) {
        return new GetGameInfoInterrupt(getDxCell(robot), getExCell(robot), getFxCell(robot), roundState.getTotalRounds(), roundState.getRoundNumber(), arena);
    }

    private InterruptHandler createGetTargetInfoInterrupt(Robot robot) {
        return new CopyMemoryCellsInterrupt(getRegisters(robot).getTargetHeading(), getExCell(robot),
                getRegisters(robot).getTargetThrottle(), getFxCell(robot));
    }

    private InterruptHandler createGetTargetIdInterrupt(Robot robot) {
        return new CopyMemoryCellsInterrupt(getRegisters(robot).getTargetId(), getFxCell(robot));
    }

    private InterruptHandler createFindAngleInterrupt(Robot robot) {
        return new FindAngleInterrupt(getExCell(robot), getFxCell(robot), getAxCell(robot), robot);
    }

    private MemoryCell getAxCell(Robot robot) {
        return getRegisters(robot).getAx();
    }

    private InterruptHandler createGetTimerInterrupt(Robot robot, RoundTimer roundTimer) {
        return new GetTimerInterrupt(getExCell(robot), getFxCell(robot), roundTimer);
    }

    private InterruptHandler createGetTransponderIdInterrupt(Robot robot) {
        return new GetIdInterrupt(robot.getTransponder(), getFxCell(robot));
    }

    private InterruptHandler createOverburnInterrupt(Robot robot) {
        return new OverburnInterrupt(robot, getAxCell(robot));
    }

    private InterruptHandler createSetKeepshiftInterrupt(Robot robot) {
        return new SetKeepshiftInterrupt(robot.getTurret(), getAxCell(robot));
    }

    private InterruptHandler createLocateInterrupt(Robot robot) {
        return new LocateInterrupt(robot,
                getExCell(robot), getFxCell(robot));
    }

    private MemoryCell getFxCell(Robot robot) {
        return getRegisters(robot).getFx();
    }

    private MemoryCell getExCell(Robot robot) {
        return getRegisters(robot).getEx();
    }

    private Registers getRegisters(Robot robot) {
        return robot.getComputer().getRegisters();
    }

    private InterruptHandler createResetInterrupt(Robot robot) {
        return new ResetInterrupt(robot.getComputer());
    }

    private InterruptHandler createDestructInterrupt(Destructable destructable) {
        return new DestructInterrupt(destructable);
    }

    InvalidInterrupt createInvalidInterrupt(Robot robot) {
        return new InvalidInterrupt(robot.getComputer());
    }

    public Map<Integer, InterruptHandler> createInterruptTable(Robot robot, Arena arena, RoundState roundState) {
        Map<Integer, InterruptHandler> interrupts = new HashMap<Integer, InterruptHandler>();

        mapHandler(interrupts, DESTRUCT, createDestructInterrupt(robot));
        mapHandler(interrupts, RESET, createResetInterrupt(robot).costs(10));
        mapHandler(interrupts, LOCATE, createLocateInterrupt(robot).costs(5));
        mapHandler(interrupts, KEEPSHIFT, createSetKeepshiftInterrupt(robot).costs(2));
        mapHandler(interrupts, OVERBURN, createOverburnInterrupt(robot).costs(1));
        mapHandler(interrupts, ID, createGetTransponderIdInterrupt(robot).costs(2));
        mapHandler(interrupts, TIMER, createGetTimerInterrupt(robot, arena.getRoundTimer()).costs(2));
        mapHandler(interrupts, ANGLE, createFindAngleInterrupt(robot).costs(32));
        mapHandler(interrupts, TARGETID, createGetTargetIdInterrupt(robot).costs(1));
        mapHandler(interrupts, TARGETINFO, createGetTargetInfoInterrupt(robot).costs(2));
        mapHandler(interrupts, GAMEINFO, createGetGameInfoInterrupt(robot, arena, roundState).costs(4));
        mapHandler(interrupts, ROBOTINFO, createGetRobotInfoInterrupt(robot).costs(5));
        mapHandler(interrupts, COLLISIONS, createGetCollisionsInterrupt(robot).costs(1));
        mapHandler(interrupts, RESETCOLCNT, createResetCollisionCountInterrupt(robot).costs(1));
        mapHandler(interrupts, TRANSMIT, createTransmitInterrupt(robot).costs(1));
        mapHandler(interrupts, RECEIVE, createRecieveInterrupt(robot).costs(1));
        mapHandler(interrupts, DATAREADY, createGetQueueSizeInterrupt(robot).costs(1));
        mapHandler(interrupts, CLEARCOM, createResetQueueInterrupt(robot).costs(1));
        mapHandler(interrupts, KILLS, createGetRobotStatisticsInterrupt(robot).costs(3));
        mapHandler(interrupts, CLEARMETERS, createResetMetersInterrupt(robot).costs(1));
        connectHandlers(robot, interrupts.values());
        return new MapWithDefaultValue<Integer, InterruptHandler>(interrupts, createInvalidInterrupt(robot));
    }

    private void mapHandler(Map<Integer, InterruptHandler> interrupts, AtRobotInterrupt interrupt, InterruptHandler handler) {
        interrupts.put(interrupt.interruptNumber, handler);
    }

    private void connectHandlers(Robot robot, Collection<InterruptHandler> interruptHandlers) {
        for (InterruptHandler handler : interruptHandlers) {
            handler.setCycleSource(robot.getComputer());
        }
    }
}
