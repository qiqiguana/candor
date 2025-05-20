package net.virtualinfinity.atrobots.atsetup;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapping between instruction symbol names and instruction codes for AT-Robot compilers.
 *
 * @author Daniel Pitts
 */
public enum AtRobotInstruction implements AtRobotSymbol {
    NOP(0),
    ADD(1),
    SUB(2),
    OR(3),
    AND(4),
    XOR(5),
    NOT(6),
    MPY(7),
    DIV(8),
    MOD(9),
    RET(10),
    CALL(11, "GSB"),
    JMP(12, "GOTO"),
    JLS(13, "JB"),
    JGR(14, "JA"),
    JNE(15),
    JEQ(16, "JE"),
    XCHG(17, "SWAP"),
    DO(18),
    LOOP(19),
    CMP(20),
    TEST(21),
    MOV(22, "SET"),
    LOC(23),
    GET(24),
    PUT(25),
    INT(26),
    IPO(27, "IN"),
    OPO(28, "OUT"),
    DEL(29, "DELAY"),
    PUSH(30),
    POP(31),
    ERR(32, "ERROR"),
    INC(33),
    DEC(34),
    SHL(35),
    SHR(36),
    ROL(37),
    ROR(38),
    JZ(39),
    JNZ(40),
    JGE(41, "JAE"),
    JLE(42, "JBE"),
    SAL(43),
    SAR(44),
    NEG(45),
    JTL(46),;

    private static AtRobotInstruction[] byValue = new AtRobotInstruction[values().length];

    static {
        for (AtRobotInstruction instruction : values()) {
            if (byValue[instruction.value] != null) {
                throw new IllegalStateException();
            }
            byValue[instruction.value] = instruction;
        }
    }

    /**
     * The constant value of this instruction
     */
    public final int value;

    /**
     * The symbol names which represent this instruction.
     */
    public final Collection<String> names;

    private AtRobotInstruction(int value) {
        this.value = value;
        this.names = Collections.singleton(name());
    }

    private AtRobotInstruction(int value, String alternate) {
        this.value = value;
        this.names = Collections.unmodifiableCollection(Arrays.asList(name(), alternate));
    }

    /**
     * Get the name of the instruction by the value.
     *
     * @param value the value
     * @return the name() of the corresponding instruction, or "&lt;unknown>" if there is none.
     */
    public static String nameOf(short value) {
        if (value >= 0 && value < byValue.length) {
            return byValue[value].name();
        }
        return "<unknown>";
    }


    public int getSymbolValue() {
        return value;
    }

    public Collection<String> getSymbolNames() {
        return names;
    }
}
