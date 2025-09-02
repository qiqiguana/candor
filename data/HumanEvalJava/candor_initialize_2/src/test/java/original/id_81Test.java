package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NumericalLetterGrade.
*/
class NumericalLetterGradeTest {
    @Test
    void testNumericalLetterGrade_4Point0() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> expected = new ArrayList<>();
        expected.add("A+");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_PerfectGPA() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertEquals(Arrays.asList("A+"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_HighGPA_2() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.8);
        assertEquals(Arrays.asList("A"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_EdgeCases_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.01);
        grades.add(3.99);
        assertEquals(Arrays.asList("D-", "A"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_ValidInput() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertDoesNotThrow(() -> NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testEmptyList() {
    List<Number> grades = new ArrayList<>();
    List<String> expectedResults = new ArrayList<>();
    assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNullInput() {
    assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    public void testGPA_A_Plus_Corrected() {
        java.util.List<java.lang.Number> gpa = new java.util.ArrayList<>();
        gpa.add(4.0);
        java.util.List<String> expectedGrade = new java.util.ArrayList<>();
        expectedGrade.add("A+");
        java.util.List<String> actualGrade = original.NumericalLetterGrade.numericalLetterGrade(gpa);
        org.junit.jupiter.api.Assertions.assertEquals(expectedGrade, actualGrade);
    }
}