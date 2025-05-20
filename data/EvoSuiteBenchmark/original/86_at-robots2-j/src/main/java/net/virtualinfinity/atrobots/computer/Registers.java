package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.atsetup.AtRobotRegister;

import static net.virtualinfinity.atrobots.atsetup.AtRobotRegister.*;
import static net.virtualinfinity.atrobots.atsetup.AtRobotRegisterAddresses.*;

/**
 * Holds pointers to all the registers of the AT-Robot virtual machine.
 *
 * @author Daniel Pitts
 */
public class Registers {
    private final Flags flags;
    private final MemoryCell ax;
    private final MemoryCell bx;
    private final MemoryCell cx;
    private final MemoryCell dx;
    private final MemoryCell ex;
    private final MemoryCell fx;
    private final MemoryCell sp;
    private final MemoryCell desiredSpeed;
    private final MemoryCell desiredHeading;
    private final MemoryCell turretOffset;
    private final MemoryCell accuracy;
    private final MemoryCell swap;
    private final MemoryCell targetId;
    private final MemoryCell targetHeading;
    private final MemoryCell targetThrottle;
    private final MemoryCell collisionCount;
    private final MemoryCell meters;
    private final MemoryCell communicationQueueHead;
    private final MemoryCell communicationQueueTail;
    private final MemoryCell targetVelocity;

    public Registers(Memory memory) {
        flags = new Flags(getCell(memory, FLAGS));
        ax = getCell(memory, AX);
        bx = getCell(memory, BX);
        cx = getCell(memory, CX);
        dx = getCell(memory, DX);
        ex = getCell(memory, EX);
        fx = getCell(memory, FX);
        sp = getCell(memory, SP);
        desiredSpeed = getCell(memory, DESIRED_SPEED_ADDRESS);
        desiredHeading = getCell(memory, DESIRED_HEADING_ADDRESS);
        turretOffset = getCell(memory, TURRET_OFFSET_ADDRESS);
        accuracy = getCell(memory, ACCURACY_ADDRESS);
        swap = getCell(memory, SWAP_ADDRESS);
        targetId = getCell(memory, TARGET_ID_ADDRESS);
        targetHeading = getCell(memory, TARGET_HEADING_ADDRESS);
        targetThrottle = getCell(memory, TARGET_THROTTLE_ADDRESS);
        collisionCount = getCell(memory, COLLISION_COUNT_ADDRESS);
        meters = getCell(memory, METERS_ADDRESS);
        communicationQueueHead = getCell(memory, COMMUNICATION_QUEUE_HEAD_ADDRESS);
        communicationQueueTail = getCell(memory, COMMUNICATION_QUEUE_TAIL_ADDRESS);
        targetVelocity = getCell(memory, TARGET_VELOCITY_ADDRESS);
    }

    private static MemoryCell getCell(Memory memory, int address) {
        return memory.getCell(address);
    }

    private static MemoryCell getCell(Memory memory, AtRobotRegister register) {
        return getCell(memory, register.address);
    }

    public MemoryCell getStackPointerCell() {
        return sp;
    }

    public Flags getFlags() {
        return flags;
    }

    public MemoryCell getAx() {
        return ax;
    }

    public MemoryCell getBx() {
        return bx;
    }

    public MemoryCell getCx() {
        return cx;
    }

    public MemoryCell getDx() {
        return dx;
    }

    public MemoryCell getEx() {
        return ex;
    }

    public MemoryCell getFx() {
        return fx;
    }

    public MemoryCell getSp() {
        return sp;
    }

    public MemoryCell getDesiredSpeed() {
        return desiredSpeed;
    }

    public MemoryCell getDesiredHeading() {
        return desiredHeading;
    }

    public MemoryCell getTurretOffset() {
        return turretOffset;
    }

    public MemoryCell getAccuracy() {
        return accuracy;
    }

    public MemoryCell getSwap() {
        return swap;
    }

    public MemoryCell getTargetId() {
        return targetId;
    }

    public MemoryCell getTargetHeading() {
        return targetHeading;
    }

    public MemoryCell getTargetThrottle() {
        return targetThrottle;
    }

    public MemoryCell getCollisionCount() {
        return collisionCount;
    }

    public MemoryCell getMeters() {
        return meters;
    }

    public MemoryCell getCommunicationQueueHead() {
        return communicationQueueHead;
    }

    public MemoryCell getCommunicationQueueTail() {
        return communicationQueueTail;
    }

    public MemoryCell getTargetVelocity() {
        return targetVelocity;
    }

    public String toString() {
        return "AX=" + getAx().signed()
                + ", BX=" + getBx().signed()
                + ", CX=" + getCx().signed()
                + ", DX=" + getDx().signed()
                + ", EX=" + getEx().signed()
                + ", FX=" + getFx().signed()
                + ", SP=" + getSp().signed()
                + ", FL=" + getFlags();
    }
}