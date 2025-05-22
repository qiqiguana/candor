package original;

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
    void testNumericalLetterGrade_withListOfGPAs_returnsCorrectList() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        grades.add(3);
        grades.add(1.7);
        grades.add(2);
        grades.add(3.5);

        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("A+");
        expectedGrades.add("B");
        expectedGrades.add("C-");
        expectedGrades.add("C");
        expectedGrades.add("A-");

        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals(expectedGrades, result);
    }
    
    @Test
        public void testNothing(){
            NumericalLetterGrade s = new NumericalLetterGrade();
            }
    @Test
    public void testAGradeCorrectlyCalculated() {
        java.util.List<java.lang.Number> input = java.util.Arrays.asList(3.8);
        java.util.List<java.lang.String> expectedOutput = java.util.Arrays.asList("A");
        java.util.List<java.lang.String> actualOutput = NumericalLetterGrade.numericalLetterGrade(input);
        org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testNumericalLetterGrade_SingleGrade() {
        List<Number> grades = Arrays.asList(3.5);
        List<String> expectedLetterGrades = Arrays.asList("A-");
        assertEquals(expectedLetterGrades, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void test_NumericalLetterGrade_CorrectGradeFor90() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.0);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("B", result.get(0));
    }
    @Test
    public void test_numericalLetterGrade_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("A+", result.get(0));
    }
    @Test
    public void testNumericalLetterGradeWithAPlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        NumericalLetterGrade result = new NumericalLetterGrade();
        List<String> expected = new ArrayList<>();
        expected.add("A+");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_APlus() {
        // Arrange
        List<Number> scores = Arrays.asList(4.0);
        List<String> expectedGrades = Arrays.asList("A+");
        
        // Act
        List<String> actualGrades = NumericalLetterGrade.numericalLetterGrade(scores);
        
        // Assert
        assertEquals(expectedGrades, actualGrades);
    }
    @Test
    public void testNumericalLetterGrade_6() {
        List<Number> scores = Arrays.asList(-1.0);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(scores);
        assertEquals("E", result.get(0));
    }
    @Test
    public void testNumericalGradeToLetterGrade_HappyCase_NumericalGrade100_1() {
        //given
        List<String> expected = java.util.Collections.singletonList("A+");
        double numericalGrade=4.0;
        List<Number> grades = java.util.Collections.singletonList(numericalGrade);
        //when
        List<String> actual = original.NumericalLetterGrade.numericalLetterGrade(grades);
        //then
        org.junit.jupiter.api.Assertions.assertEquals(expected,actual);
    }
    @Test
    public void testNumericalGradeToLetterGrade_HappyCase_NumericalGrade79() {
        //given
        List<Number> grades = Arrays.asList(1.7);
        String expected = "C-";
        //when
        List<String> actual = NumericalLetterGrade.numericalLetterGrade(grades);
        //then
        assertEquals(expected, actual.get(0));
    }
    @Test
    public void testNumericalGradeAPlus() {
        NumericalLetterGrade.numericalLetterGrade(java.util.Arrays.asList(4.0)).contains("A+");
    }
    @Test
    public void testNumericalGradeToLetterGrade_LowGPA_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.5);
        List<String> expected = new ArrayList<>();
        expected.add("D-");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void NumericalGradeToLetterGrade_InvalidInput_Fixed_100() {
        //given
        String expected = "A+";
        double input = 4.0;
        //when
        List<String> result = NumericalLetterGrade.numericalLetterGrade(List.of(input));
        //then
        assertEquals(expected, result.get(0));
    }
    @Test
    public void test_numericalLetterGrade_single_grade_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.5);
        List<String> expected = new ArrayList<>();
        expected.add("D-");
        List<String> actual = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals(expected, actual);
    }
                                    
}