package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.atsetup.AtRobotInstruction;
import static net.virtualinfinity.atrobots.atsetup.AtRobotInstruction.*;

/**
 * Provides a mapping between instruction values and implementations.
 *
 * @author Daniel Pitts
 */
public class InstructionTable {
    private final Instruction invalidMicrocodeInstruction = new InvalidMicrocodeInstruction(1);
    private final Instruction[] instructions = new Instruction[47];
    private final Instruction numberedLabelInstruction = new NoOperationInstruction(0);
    private static final Instruction UNKNOWN_INSTRUCTION = new UnknownInstruction(1);

    {
        mapInstruction(NOP, new NoOperationInstruction(1));
        mapInstruction(ADD, new AddInstruction(1));
        mapInstruction(SUB, new SubtractInstruction(1));
        mapInstruction(OR, new BitwiseOrInstruction(1));
        mapInstruction(AND, new BitwiseAndInstruction(1));
        mapInstruction(XOR, new BitwiseExclusiveOrInstruction(1));
        mapInstruction(NOT, new BitwiseNegationInstruction(1));
        mapInstruction(MPY, new MultiplyInstruction(10));
        mapInstruction(DIV, new DivideInstruction(10));
        mapInstruction(MOD, new ModuloInstruction(10));
        mapInstruction(RET, new PopInstructionPointerInstruction(1));
        mapInstruction(CALL, new CallInstruction(1));
        mapInstruction(JMP, new JumpInstruction(1));
        mapInstruction(JLS, new JumpWhenLessInstruction(0));
        mapInstruction(JGR, new JumpWhenGreaterInstruction(0));
        mapInstruction(JNE, new JumpWhenNotEqualInstruction(0));
        mapInstruction(JEQ, new JumpWhenEqualInstruction(0));
        mapInstruction(XCHG, new SwapInstruction(3));
        mapInstruction(DO, new SetCXInstruction(1));
        mapInstruction(LOOP, new LoopInstruction(1));
        mapInstruction(CMP, new CompareInstruction(1));
        mapInstruction(TEST, new TestInstruction(2));
        mapInstruction(MOV, new MoveInstruction(1));
        mapInstruction(LOC, new AddressInstruction(2));
        mapInstruction(GET, new GetInstruction(2));
        mapInstruction(PUT, new PutInstruction(2));
        mapInstruction(INT, new CallInterruptInstruction(0));
        mapInstruction(IPO, new ReadPortInstruction(4));
        mapInstruction(OPO, new WritePortInstruction(4));
        mapInstruction(DEL, new DelayInstruction(0));
        mapInstruction(PUSH, new PushInstruction(1));
        mapInstruction(POP, new PopInstruction(1));
        mapInstruction(ERR, new ErrorInstruction(0));
        mapInstruction(INC, new IncrementInstruction(1));
        mapInstruction(DEC, new DecrementInstruction(1));
        mapInstruction(SHL, new BitShiftLeftInstruction(1));
        mapInstruction(SHR, new BitShiftRightInstruction(1));
        mapInstruction(ROL, new BitRotateLeftInstruction(1));
        mapInstruction(ROR, new BitRotateRightInstruction(1));
        mapInstruction(JZ, new JumpWhenZeroInstruction(0));
        mapInstruction(JNZ, new JumpWhenNotZeroInstruction(0));
        mapInstruction(JGE, new JumpWhenGreaterOrEqualInstruction(0));
        mapInstruction(JLE, new JumpWhenLessOrEqualInstruction(0));
        mapInstruction(SAL, new BitShiftLeftInstruction(1));
        mapInstruction(SAR, new SignedBitShiftRightInstruction(1));
        mapInstruction(NEG, new NegateInstruction(1));
        mapInstruction(JTL, new SetInstructionPointerInstruction(1));
    }

    private void mapInstruction(AtRobotInstruction instructionName, Instruction instructionHandler) {
        instructions[instructionName.value] = instructionHandler;
    }

    public Instruction getInvalidMicrocodeInstruction() {
        return invalidMicrocodeInstruction;
    }

    public Instruction getInstruction(short value) {
        return value >= 0 && value < instructions.length ? instructions[value] : UNKNOWN_INSTRUCTION;
    }

    public Instruction getNumberedLabelInstruction() {
        return numberedLabelInstruction;
    }
}

/*
NoOperation              1  NOP           NOP        Simply wastes a clock-cycle.
Add                      1  ADD           ADD V N    Adds      V+N, result stored in V
Subtract                 1  SUB           SUB V N    Subtracts V-N, result stored in V
BitwiseOr                1  OR            OR  V N    Bitwise OR,  V or  N, result stored in V
BitwiseAnd               1  AND           AND V N    Bitwise AND, V and N, result stored in V
BitwiseExclusiveOr       1  XOR           XOR V N    Bitwise XOR, V xor N, result stored in V
BitwiseNegation          1  NOT           NOT V      Bitwise NOT, not(V),  result stored in V
Multiply                10  MPY           MPY V N    Mulitplies V*N, result stored in V
Divide                  10  DIV           DIV V N    Divides    V/N, result stored in V (integer)
Modulo                  10  MOD           MOD V N    MOD's    V & N, result stored in V (modulus)
Return                   1  RET  RETURN   RET        Returns from a subroutine (pops the ip)
Call                     1  CALL GSB      CALL N     Calls subroutine at label #N (pushes ip)
Jump                     1  JMP  GOTO     JMP N      Jumps program (ip) to label #N
JumpWhenLess             0  JLS  JB       JLS N      Jumps to label N if last compare was <
JumpWhenGreater          0  JGR  JA       JGR N      Jumps to label N if last compare was >
JumpWhenNotEqual         0  JNE           JNE N      Jumps to label N if last compare was <>
JumpWhenEqual            0  JEQ  JE       JEQ N      Jumps to label N if last compare was =
Swap                     3  XCHG SWAP     XCHG V V   Exchanges the values of two variables
SetCX                    1  DO            DO N       Sets CX = N
Loop                     1  LOOP          LOOP N     Decrements CX, If CX>0 then Jumps to label N
Compare                  1  CMP           CMP N N    Compares two numbers, results in flags reg.
Test                     2  TEST          TEST N N   Ands two numbers, result not stored, flags set
Move                     1  MOV  SET      MOV V N    Sets V = N
Address                  2  LOC  ADDR     LOC V V    Sets first V = memory address of second V
Get                      2  GET           GET V N    Sets V = number from memory location N
Put                      2  PUT           PUT N1 N2  Sets memory location N2 = N1
CallInterrupt            0  INT           INT N      Executes interrupt number N
ReadPort                 4  IPO  IN       IPO N V    Inputs number from port N, result into V
WritePort                4  OPO  OUT      OPO N1 N2  Outputs N2 to port N1
Delay                    0  DEL  DELAY    DEL N      Equivelant to N NOPS.
Push                     1  PUSH          PUSH N     Puts N onto the stack (sp incremented)
Pop                      1  POP           POP  V     Removes a number from the stack, into V
Error                    0  ERR  ERROR    ERR N      Generate an error code, useful for debugging
Increment                1  INC           INC V      Increments V, (v=v+1)
Decrement                1  DEC           DEC V      Decrements V, (v=v-1)
BitShiftLeft             1  SHL           SHL V N    Bit-shifts  V left  N bit positions
BitShiftRight            1  SHR           SHR V N    Bit-shifts  V right N bit positions
BitRotateLeft            1  ROL           ROL V N    Bit-rotates V left  N bit positions
BitRotateRight           1  ROR           ROR V N    Bit-rotates V right N bit positions
JumpWhenZero             0  JZ            JZ  N      Jumps to label N if last compare was 0
JumpWhenNotZero          0  JNZ           JNZ N      Jumps to label N if last compare was not 0
JumpWhenNotLess          0  JAE  JGE      JAE N      Jumps to label N if last compare was >=
JumpWhenNotGreater       0  JBE  JLE      JBE N      Jumps to label N if last compare was <=
BitShiftLeft             1  SAL           SAL V N    Bit-Shifts, same as SHL
SignedBitShiftRight      1  SAR           SAR V N    Same as SHR, except preserves bit 15.
Negate                   1  NEG           NEG V      Negates V:   V = 0-V  (aka "two's compliment")
SetInstructionPointer    1  JTL           JTL N      Jumps to line N of compiled program.
 */
