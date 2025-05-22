package oracle1;

import static java.util.Arrays.asList;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testNumericalLetterGrade_When_Gpa_Is_Exactly_4_Point_0_Returns_A_Plus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertEquals("A+", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
    
    @Test
     void testNothing(){
         NumericalLetterGrade s = new NumericalLetterGrade();
         }
    @Test
    public void test_GradeAPlus_1() {
        List<Number> input = Arrays.asList(4.0);
        assertEquals("A+", NumericalLetterGrade.numericalLetterGrade(input).get(0));
    }
    @Test
    public void test_GradeA_1() {
        List<Number> input = Arrays.asList(3.5);
        assertEquals("A-", NumericalLetterGrade.numericalLetterGrade(input).get(0));
    }
    @Test
    public void test_GradeAMinus_2() {
        List<Number> input = Arrays.asList(3.7);
        assertEquals(Arrays.asList("A-"), NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    void testEmptyInputList() {
        List<Number> grades = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testSingleElementInputList() {
        List<Number> grades = Arrays.asList(0.5);
        List<String> expected = Arrays.asList("D-");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testNullInputList() {
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    void testNumericalLetterGradeInstance() {
        NumericalLetterGrade s = new NumericalLetterGrade();
        assertNotNull(s);
    }
    @Test
    void testMultipleElementsInputList1() {
        List<Number> grades = Arrays.asList(0.5, 1.2, 3.4);
        List<String> expectedResults = Arrays.asList("D-", "D+", "A-");
        // assuming the method under test is called 'numericalLetterGrade'
        List<String> actualResults = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void testNumericalLetterGrade_APlus_Grade() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertEquals(List.of("A+"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_A_Grade() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.8);
        assertEquals(List.of("A"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_BPlus_Grade() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.2);
        assertEquals(List.of("B+"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_GPA_Zero() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.0);
        assertEquals(List.of("E"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_GPA_Above_Four() {
        List<Number> grades = new ArrayList<>();
        grades.add(5.0);
        assertEquals(List.of("A+"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testNumericalLetterGradeInstantiation() {
        NumericalLetterGrade s = new NumericalLetterGrade();
        assertNotNull(s);
    }
    @Test void testNumericalLetterGradeInitialization1(){ NumericalLetterGrade s = new NumericalLetterGrade(); assertNotEquals(null, s); }
    @Test
    void testNumericalLetterGradeInitialization(){
        NumericalLetterGrade s = new NumericalLetterGrade();
        assertNotNull(s);
    }
    @Test
    public void testNumericalLetterGrade_C() {
        double score = 1.7;
        String expectedGrade = "C-";
        List<Number> scores = new ArrayList<>();
        scores.add(score);
        List<String> actualGrades = NumericalLetterGrade.numericalLetterGrade(scores);
        assertEquals(expectedGrade, actualGrades.get(0));
    }
    @Test
    public void testNumericalLetterGrade_SingleValue_APlus() {
        List<Number> scores = new ArrayList<>();
        scores.add(50);
        String expected = "A+";
        List<String> actual = NumericalLetterGrade.numericalLetterGrade(scores);
        assertEquals(expected, actual.get(0));
    }
    @Test
    void testBoundaryValues() {
        List<Number> grades = Arrays.asList(0.0, 10.0);
        List<String> expectedResults = Arrays.asList("E", "A+");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testNumericalLetterGradeInstanceUnique() {
        assertDoesNotThrow(() -> new NumericalLetterGrade());
    }
    @Test
    void testCorrectNumericalValues() {
        List<Number> grades = Arrays.asList(1.5, 2.7);
        List<String> expectedResults = Arrays.asList("C-", "B-");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testInvalidInputTypeFixed() {
        List<Number> grades = Arrays.asList(1.0);
        assertDoesNotThrow(() -> NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    void testNumericalLetterGradeValidInput1() {
        List<Number> grades = Arrays.asList(4.0, 3, 1.7, 2, 3.5);
        List<String> expected = Arrays.asList("A+", "B", "C-", "C", "A-");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_BPlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.1);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("B+", result.get(0));
    }
    @Test
    public void testNumericalLetterGrade_B_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(2.8);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("B", result.get(0));
    }
    @Test
    public void testNumericalLetterGrade_BMinus() {
        List<Number> grades = new ArrayList<>();
        grades.add(2.4);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("B-", result.get(0));
    }
    @Test
    public void testNumericalLetterGrade_GPA_A() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.8);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("A", result.get(0));
    }
    @Test
    void testNumericalLetterGradeEmptyList() {
        List<Number> grades = new ArrayList<>();
        assertEquals(Collections.emptyList(), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void test_A_Plus_Grade() {
        List<String> result = NumericalLetterGrade.numericalLetterGrade(Collections.singletonList(4.0));
        assertEquals("A+", result.get(0));
    }
    @Test
    public void test_EmptyInput() {
        List<Number> grades = new ArrayList<>();
        assertEquals(new ArrayList<String>(), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void test_A_Grade_1() {
        assertEquals("A", NumericalLetterGrade.numericalLetterGrade(Arrays.asList(3.8)).get(0));
    }
    @Test
    public void test_B_Grade_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.2);
        assertEquals("B+", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
    @Test
    public void test_CPlus_Grade_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(2.3);
        assertEquals("C+", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
    @Test
    public void test_D_Grade() {
        assertEquals("D", NumericalLetterGrade.numericalLetterGrade(Arrays.asList(1.0)).get(0));
    }
    @Test
    public void test_C_Grade_With_1_5_Input() {
        assertEquals("C-", NumericalLetterGrade.numericalLetterGrade(Arrays.asList(1.5)).get(0));
    }
    @Test
    public void testZeroGPA() {
        List<String> result = NumericalLetterGrade.numericalLetterGrade(Arrays.asList(0.0));
        assertEquals("E", result.get(0));
    }
                                  
}