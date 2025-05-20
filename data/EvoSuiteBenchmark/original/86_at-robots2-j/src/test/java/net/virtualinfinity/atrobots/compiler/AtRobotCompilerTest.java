package net.virtualinfinity.atrobots.compiler;

import net.virtualinfinity.atrobots.AbstractCompilerTest;
import net.virtualinfinity.atrobots.atsetup.AtRobotInstruction;
import net.virtualinfinity.atrobots.atsetup.AtRobotRegister;
import net.virtualinfinity.atrobots.computer.MemoryArray;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * @author Daniel Pitts
 */
public class AtRobotCompilerTest extends AbstractCompilerTest {

    public void testNumberLabel() throws IOException {
        source.println(":3");
        compile();
        assertFalse(compilerOutput.hasErrors());
        assertInstructionEquals(0, 3, 0, 0, 2);
    }

    public void testDuplicateLabels() throws IOException {
        source.println("!test");
        source.println("!test");
        source.println("NOP");
        compile();
        assertTrue(compilerOutput.hasErrors());
        assertTrue(compilerOutput.getErrors().getMessages().get(0).contains("!test"));
    }

    public void testDuplicateVariables() throws IOException {
        source.println("#def Foo");
        source.println("#def FOO");
        source.println("NOP");
        compile();
        assertTrue(compilerOutput.hasErrors());
        assertTrue(compilerOutput.getErrors().getMessages().get(0).contains("foo"));
    }

    public void testVariableShadowsOpcode() throws IOException {
        source.println("#def MOV");
        source.println("NOP");
        compile();
        assertTrue(compilerOutput.hasErrors());
        assertTrue(compilerOutput.getErrors().getMessages().get(0).contains("mov"));
    }


    private void assertInstructionEquals(int instructionPointer, int instruction, int op0, int op1, int microcode) {
        final int address = instructionPointer * 4;
        assertEquals(instruction, getMemoryArray().get(address));
        assertEquals(op0, getMemoryArray().get(1 + address));
        assertEquals(op1, getMemoryArray().get(2 + address));
        assertEquals(microcode, getMemoryArray().get(3 + address));
    }

    public void testTokenTranslation() throws IOException {
        source.println("MOV ax, bx");
        compile();
        assertInstructionEquals(0, AtRobotInstruction.MOV.value,
                AtRobotRegister.AX.address,
                AtRobotRegister.BX.address,
                0x110);
        assertFalse(compilerOutput.hasErrors());
    }

    public void testUnresolvedMicrocodeReplacement() throws IOException {
        source.println("@0 [0], !test");
        source.println("!test");
        source.println("@0 !test2, [@0]");
        source.println("!test2");
        source.println("@0 !test3, !test3");
        source.println("!test3");
        source.println("0");
        compile();
        assertInstructionEquals(0, 0, 0, 1, 0x481);
        assertInstructionEquals(1, 0, 2, 0, 0x941);
        assertInstructionEquals(2, 0, 3, 3, 0x441);
        assertFalse(compilerOutput.hasErrors());
    }

    public void testNormalLabel() throws IOException {
        source.println("!Test");
        source.println("jmp !Test");
        compile();
        assertFalse(compilerOutput.hasErrors());
        assertEquals(0, getMemoryArray().get(1));
        assertEquals(0x40, getMemoryArray().get(3));
    }

    public void testCompileAll() {
        final File[] files = new File("original").listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".at2");
            }
        });
        Errors errors = new Errors();
        AtRobotCompiler compiler = new AtRobotCompiler();
        for (File file : files) {
            try {
                System.out.println("Loading " + file);
                compilerOutput = compiler.compile(file);
                final Errors result = compilerOutput.getErrors();
                if (result.hasErrors()) {
                    errors.info("Errors in " + file.getName());
                    errors.addAll(result);
                }
            } catch (IOException e1) {
                errors.info("Errors in " + file.getName());
                errors.info(e1.getMessage());
            } catch (Throwable t) {
                errors.info("Compiler error in " + file.getName());
                errors.info(t.getMessage());
                t.printStackTrace();
            }
        }
        errors.dumpErrors();
        assertFalse(errors.hasErrors());
    }


    private MemoryArray getMemoryArray() {
        return compilerOutput.getProgram().createProgramMemory();
    }

}
