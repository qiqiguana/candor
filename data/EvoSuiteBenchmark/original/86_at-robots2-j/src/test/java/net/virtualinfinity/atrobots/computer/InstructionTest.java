package net.virtualinfinity.atrobots.computer;

import net.virtualinfinity.atrobots.AbstractCompilerTest;

/**
 * @author Daniel Pitts
 */
public class InstructionTest extends AbstractCompilerTest {
    public void testNOP() {
        executeFirst("NOP");
        assertEquals(-1, computer.getCycles());
    }

    public void testADD() {
        assertBinaryOperation(5, 9, "ADD", 5 + 9);
    }

    public void testSUB1() {
        assertBinaryOperation(5, 9, "SUB", 5 - 9);
    }

    public void testSUB2() {
        assertBinaryOperation(0, -1, "SUB", 0 - -1);
    }

    public void testOR() {
        assertBinaryOperation(5, 9, "OR", 5 | 9);
    }

    public void testAND() {
        assertBinaryOperation(5, 9, "AND", 5 & 9);
    }

    public void testXOR() {
        assertBinaryOperation(5, 9, "XOR", 5 ^ 9);
    }

    public void testNOT() {
        assertBinaryOperation(9, 0, "NOT", ~9);
    }

    public void testNEG() {
        assertBinaryOperation(9, 0, "NEG", -9);
    }

    public void testMPY() {
        assertBinaryOperation(5, 9, "MPY", 5 * 9);
    }

    public void testDIV() {
        assertBinaryOperation(16, 3, "DIV", 16 / 3);
    }

    public void testMOD() {
        assertBinaryOperation(16, 3, "MOD", 16 % 3);
    }

    public void testSHL() {
        assertBinaryOperation(16, 3, "SHL", 16 << 3);
    }

    public void testSHR() {
        assertBinaryOperation(-16, 3, "SHR", (-16 & 0xFFFF) >> 3);
    }

    public void testSAR1() {
        assertBinaryOperation(-16, 3, "SAR", -16 >> 3);
    }

    public void testSAR2() {
        assertBinaryOperation(16, 3, "SAR", 16 >> 3);
    }

    public void testROR() {
        assertBinaryOperation(0x8001, 1, "ROR", (short) 0xC000);
    }

    public void testROL() {
        assertBinaryOperation(0x8001, 1, "ROL", (short) 0x0003);
    }

    public void testPOP() {
        compile("POP AX");
        computer.getStack().push((short) 3);
        executeInstruction();
        assertAX(3);
    }

    public void testPUSH() {
        executeFirst("PUSH 3");
        assertStackPointer(1);
        assertStackPop(3);
    }

    public void testCALL() {
        executeFirst("CALL !bob\nNOP\n!bob\n NOP");
        assertInstructionPointer(2);
        assertStackPointer(1);
        assertStackPop(1);
    }

    public void testJMP_label() {
        executeFirst("JMP !bob\nnop\nnop\n!bob\nnop");
        assertInstructionPointer(3);
    }

    public void testJMP_number() {
        executeFirst("JMP 7\n:1\nnop\n:7");
        assertInstructionPointer(3);
    }

    public void testJLS() {
        assertConditionalJump(0, 1, "JLS", 0, 0);
    }

    public void testJGR() {
        assertConditionalJump(1, 0, "JGR", 0, 0);
    }

    public void testJE() {
        assertConditionalJump(0, 0, "JE", 1, 0);
    }

    public void testJZ() {
        assertConditionalJump(0, 0, "JZ", 1, 1);
    }

    public void testJGE_equal() {
        assertConditionalJump(0, 0, "JGE", 0, 1);
    }

    public void testJGE_greater() {
        assertConditionalJump(1, 0, "JGE", 0, 1);
    }

    public void testJLE_equal() {
        assertConditionalJump(0, 0, "JlE", 2, 1);
    }

    public void testJLE_greater() {
        assertConditionalJump(0, 1, "JlE", 2, 1);
    }

    public void testJNE() {
        assertConditionalJump(0, 1, "JNE", 1, 1);
    }

    public void testJNZ() {
        assertConditionalJump(1, 1, "JNZ", 0, 0);
    }

    public void testSWAP() {
        addLine("MOV AX, 3");
        addLine("MOV CX, 5");
        addLine("SWAP AX CX");
        compile();
        executeInstruction();
        executeInstruction();
        executeInstruction();
        assertAX(5);
        assertCX(3);
    }

    private void addLine(String aba) {
        source.println(aba);
    }

    public void testDO() {
        executeFirst("DO 9");
        assertCX(9);
    }

    public void testMOV() {
        executeFirst("MOV AX, 9");
        assertAX(9);
    }

    public void testINC() {
        executeFirst("INC AX");
        assertAX(1);
    }

    public void testDEC() {
        executeFirst("DEC AX");
        assertAX(-1);
    }

    public void testCMP() {
        addLine("CMP 1, 2");
        addLine("CMP 2, 1");
        addLine("CMP 0,0");
        addLine("CMP 1,1");
        compile();

        executeInstruction();
        assertCompareInequal();
        assetCompareLess();

        executeInstruction();
        assertCompareInequal();
        assertCompareGreater();

        executeInstruction();
        assertFlagEqual();
        assertFlagZero();
        assertCompareNotLessAndNotGreater();

        executeInstruction();
        assertFlagEqual();
        assertFlagNotZero();
        assertCompareNotLessAndNotGreater();
    }

    public void testJTL() {
        executeFirst("JTL 3\nNOP\nNOP\nNOP\nNOP\nNOP\n");
        assertInstructionPointer(3);
    }

    public void testTEST() {
        addLine("test 1, 2");
        addLine("test 2, 1");
        addLine("test 0,0");
        addLine("test 1,1");
        addLine("test 1,5");
        compile();
        executeInstruction();
        assertCompareZeroNotEqual();
        assertCompareNotLessAndNotGreater();

        executeInstruction();
        assertCompareZeroNotEqual();
        assertCompareNotLessAndNotGreater();

        executeInstruction();
        assertFlagEqual();
        assertFlagZero();
        assertCompareNotLessAndNotGreater();

        executeInstruction();
        assertFlagEqual();
        assertFlagNotZero();
        assertCompareNotLessAndNotGreater();

        executeInstruction();
        assertCompareInequal();
        assertCompareNotLessAndNotGreater();
    }

    private void assertCompareZeroNotEqual() {
        assertFlagNotEqual();
        assertFlagZero();
    }

    private void assertCompareNotLessAndNotGreater() {
        assertFlagNotGreater();
        assertFlagNotLess();
    }

    private void assertCompareGreater() {
        assertFlagGreater();
        assertFlagNotLess();
    }

    private void assetCompareLess() {
        assertFlagNotGreater();
        assertFlagLess();
    }

    private void assertCompareInequal() {
        assertFlagNotEqual();
        assertFlagNotZero();
    }

    private void assertFlagZero() {
        assertTrue(computer.getFlags().isZero());
    }

    private void assertFlagEqual() {
        assertTrue(computer.getFlags().isEqual());
    }

    private void assertFlagNotLess() {
        assertFalse(computer.getFlags().isLess());
    }

    private void assertFlagGreater() {
        assertTrue(computer.getFlags().isGreater());
    }

    private void assertFlagLess() {
        assertTrue(computer.getFlags().isLess());
    }

    private void assertFlagNotZero() {
        assertFalse(computer.getFlags().isZero());
    }

    private void assertFlagNotGreater() {
        assertFalse(computer.getFlags().isGreater());
    }

    private void assertFlagNotEqual() {
        assertFalse(computer.getFlags().isEqual());
    }

    private void executeFirst(String program) {
        compile(program);
        executeInstruction();
    }

    private void assertStackPointer(int expectedStackPointer) {
        assertEquals(expectedStackPointer, computer.getRegisters().getSp().signed());
    }

    private void assertInstructionPointer(int expectedInstructionPointer) {
        assertEquals(expectedInstructionPointer, computer.getNextInstructionPointer());
    }

    private void assertStackPop(int stackPopValue) {
        assertEquals(stackPopValue, computer.getStack().pop());
    }

    private void compile(String instruction) {
        addLine(instruction);
        compile();
    }

    private void assertBinaryOperation(int v1, int v2, String op, int result) {
        compile("MOV AX, " + v1 + "\n" + op + " AX, " + v2);
        executeInstruction();
        executeInstruction();
        assertAX(v1 + op + v2, result);
    }

    private void assertAX(String message, int result) {
        assertEquals(message, result, computer.getRegisters().getAx().signed());
    }

    private void assertAX(int result) {
        assertEquals(result, computer.getRegisters().getAx().signed());
    }

    private void assertCX(int result) {
        assertEquals(result, computer.getRegisters().getCx().signed());
    }

    private void executeInstruction() {
        computer.executeInstruction();
    }

    private void assertConditionalJump(int v1, int v2, String jump, int v3, int v4) {
        addLine("!fail");
        addLine("CMP " + v1 + ", " + v2);
        addLine(jump + " !succeed");
        addLine("NOP");
        addLine("!succeed");
        addLine("CMP " + v3 + ", " + v4);
        addLine(jump + " !fail");
        addLine("NOP");
        compile();
        executeInstruction();
        executeInstruction();
        assertInstructionPointer(3);
        executeInstruction();
        executeInstruction();
        assertInstructionPointer(5);
    }
}
