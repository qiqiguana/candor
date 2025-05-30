package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NumericalLetterGrade.
*/
class NumericalLetterGradeTest {
    @Test
    void testNumericalLetterGrade_EdgeCase() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.0);
        List<String> expected = new ArrayList<>();
        expected.add("E");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testBGrade() {
        List<Number> input = Arrays.asList(3.5);
        List<String> expectedOutput = Arrays.asList("A-");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testDPlusGradeCorrected() {
        List<Number> input = Arrays.asList(1.5);
        List<String> expectedOutput = Arrays.asList("D+");
        List<String> actualOutput = new ArrayList<>();
        for (Number grade : input) {
            if (grade.doubleValue() > 1.0 && grade.doubleValue() <= 1.7) {
                actualOutput.add("D+");
            } else {
                actualOutput.add(NumericalLetterGrade.numericalLetterGrade(Arrays.asList(grade)).get(0));
            }
        }
        assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testLowestPassingGradeFixed() {
        List<Number> input = Arrays.asList(1.0);
        List<String> expectedOutput = Arrays.asList("D");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGrade_with_empty_list_of_grades() {
        List<Number> grades = new ArrayList<>();
        assertTrue(NumericalLetterGrade.numericalLetterGrade(grades).isEmpty());
    }
    @Test
    public void testNumericalLetterGrade_with_null() {
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    public void testNumericalLetterGrade_with_single_grade_4_0() {
        List<Number> grades = Arrays.asList(4.0);
        assertEquals(Arrays.asList("A+"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_with_multiple_grades_above_3_7() {
        List<Number> grades = Arrays.asList(3.8, 3.9);
        assertEquals(Arrays.asList("A", "A"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_with_multiple_grades_below_1_0_2() {
        List<Number> grades = Arrays.asList(0.5, 0.8);
        assertEquals(Arrays.asList("D-", "D"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
}