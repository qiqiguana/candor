package net.virtualinfinity.atrobots.computer;

/**
 * An instruction which causes the instruction pointer to be changed.
 *
 * @author Daniel Pitts
 */
public class JumpInstruction extends Instruction {
    protected JumpInstruction(int baseExecutionCost) {
        super(baseExecutionCost);
    }

    protected void perform(Computer computer) {
        computer.jump();
    }
}
