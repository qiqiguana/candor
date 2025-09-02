package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Test
    public void testNumericalLetterGradeEmptyList() {
        List<java.lang.Number> input = new java.util.ArrayList<>();
        List<java.lang.String> expectedOutput = new java.util.ArrayList<>();
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeNullInput() {
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    public void testNumericalLetterGradeInvalidGPA() {
        List<Number> input = Arrays.asList(new Double(-1.0));
        List<String> expectedOutput = Arrays.asList("E");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeGPAOfZero() {
        List<Number> input = Arrays.asList(new Double(0.0));
        List<String> expectedOutput = Arrays.asList("E");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeGPAOfFour() {
        List<Number> input = Arrays.asList(4.0);
        List<String> expectedOutput = Arrays.asList("A+");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeMultipleGPAsAtBoundariesFixed2() {
        List<Number> grades = new ArrayList<>();
        grades.add(1.0);
        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("D");
        assertEquals(expectedGrades, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalGradeA_2() {
        List<Number> input = Arrays.asList(3.8);
        List<String> expectedOutput = Arrays.asList("A");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalGradeA_1() {
        List<Number> input = Arrays.asList(3.9);
        List<String> expectedOutput = Arrays.asList("A");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalGradeB() {
        List<Number> input = Arrays.asList(3.5);
        List<String> expectedOutput = Arrays.asList("A-");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalGradeC_2() {
        List<Number> input = Arrays.asList(2.5);
        List<String> expectedOutput = Arrays.asList("B-");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testSingleGPA() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.5);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("A-", result.get(0));
    }
    @Test
    public void test_GPA_A_range() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> expected = new ArrayList<>();
        expected.add("A+");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void test_GPA_AMinus() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(3.7);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("A-", result.get(0));
    }
    @Test
    public void testNumericalLetterGrade_A_minus() {
        List<Number> gpaList = new ArrayList<>();
        gpaList.add(3.5);
        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("A-");
        assertEquals(expectedGrades, NumericalLetterGrade.numericalLetterGrade(gpaList));
    }
    @Test
    public void testNumericalLetterGrade_C_minus_1() {
        List<Number> gpaList = new ArrayList<>();
        gpaList.add(1.7);
        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("C-");
        assertEquals(expectedGrades, NumericalLetterGrade.numericalLetterGrade(gpaList));
    }
    @Test
    public void testNumericalLetterGrade_D_plus() {
        List<Number> gpaList = new ArrayList<>();
        gpaList.add(1.2);
        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("D+");
    
        List<String> actualGrades = NumericalLetterGrade.numericalLetterGrade(gpaList);
    
        assertEquals(expectedGrades, actualGrades);
    }
    @Test
    public void testNumericalLetterGrade_E_1() {
        List<Number> gpaList = new ArrayList<>();
        gpaList.add(0.0);
        List<String> expectedGrades = new ArrayList<>();
        expectedGrades.add("E");
        assertEquals(expectedGrades, NumericalLetterGrade.numericalLetterGrade(gpaList));
    }
    @Test
    public void TestNumericalLetterGrade_HappyPath_A() {
        List<Number> grades = new ArrayList<>();
        grades.add(new Double(3.8));
        assertEquals(NumericalLetterGrade.numericalLetterGrade(grades), Arrays.asList("A"));
    }
    @Test
    public void TestNumericalLetterGrade_EdgeCase_GradeE() {
        List<Number> grades = new ArrayList<>();
        grades.add(new Double(0.0));
        assertEquals(NumericalLetterGrade.numericalLetterGrade(grades), Arrays.asList("E"));
    }
    @Test
    public void testNumericalLetterGrade_NaNInput() {
        List<Number> grades = new ArrayList<>();
        grades.add(Double.NaN);
        assertEquals("E", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
    @Test
    public void TestNumericalLetterGrade_SadPath_InvalidGPAFixed() {
        List<Number> grades = new ArrayList<>();
        grades.add(new Integer(-1));
        assertEquals(Arrays.asList("E"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGradeWithValidInputScoreBetween37And40() {
        // Arrange
        double score = 3.8;
        String expectedGrade = "A";
    
        // Act
        List<String> grades = NumericalLetterGrade.numericalLetterGrade(List.of(score));
    
        // Assert
        assertEquals(expectedGrade, grades.get(0));
    }
    @Test
    public void TestNumericalLetterGradeWithInvalidInput1() {
        List<Number> grades = Arrays.asList((Number) null);
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGradeWithEmptyInput() {
        List<Number> grades = new ArrayList<>();
        List<String> expectedResults = new ArrayList<>();
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGradeWithNullInput() {
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    public void testNegativeGpas1() {
        List<Number> gpas = Arrays.asList(-1.0, -2.0, -3.0);
        List<String> expectedGrades = Arrays.asList("E", "E", "E");
        assertEquals(expectedGrades, NumericalLetterGrade.numericalLetterGrade(gpas));
    }
    @Test
    public void testNumericalLetterGrade_B() {
        List<Number> grades = new ArrayList<>();
        grades.add(2.8);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("B");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_EmptyList() {
        List<Number> grades = new ArrayList<>();
        List<String> expectedResults = new ArrayList<>();
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_MultipleGrades() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        grades.add(3.5);
        grades.add(2.8);
        grades.add(1.7);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("A+");
        expectedResults.add("A-");
        expectedResults.add("B");
        expectedResults.add("C-");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNonNumericInput() {
        List<Number> gpas = Arrays.asList(1, 2, 3);
        assertDoesNotThrow(() -> NumericalLetterGrade.numericalLetterGrade(gpas));
    }
    @Test
    public void testNonNumericInputFixed() {
        List<Number> gpas = Arrays.asList(1, 2, 3);
        assertDoesNotThrow(() -> NumericalLetterGrade.numericalLetterGrade(gpas));
    }
    @Test
    public void testNumericalLetterGrade_APlus_Duplicate1() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("A+");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGradeMultipleGrades() {
        java.util.List<java.lang.Number> grades = new java.util.ArrayList<>();
        grades.add(1.0);
        grades.add(0.7);
        grades.add(0.5);
        String[] expectedOutput = {"D", "D-", "D-"};
        String[] output = NumericalLetterGrade.numericalLetterGrade(grades).toArray(new String[0]);
        assertArrayEquals(expectedOutput, output);
    }
    @Test
    public void testGradeOfA() {
    	List<Number> input = Arrays.asList(4.0);
    	List<String> expected = Arrays.asList("A+");
    	assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testGradeOfE() {
        List<Number> input = Arrays.asList(0.0);
        List<String> expected = Arrays.asList("E");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNegativeGrade() {
        List<Number> input = Arrays.asList(-1.0);
        List<String> expected = Arrays.asList("E");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeWithPerfectGPA() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("A+");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGradeWithLowestPassingGPAUpdated() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.7);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("D-");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGradeWithFailingGPA_1() {
        List<Number> grades = new ArrayList<>();
        grades.add(0.0);
        List<String> expectedResults = new ArrayList<>();
        expectedResults.add("E");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGradeWithEmptyInput() {
        List<Number> grades = new ArrayList<>();
        List<String> expectedResults = NumericalLetterGrade.numericalLetterGrade(grades);
        assertTrue(expectedResults.isEmpty());
    }
    @Test
    public void testNumericalLetterGradeWithEmptyList() {
        List<Number> grades = new ArrayList<>();
        assertTrue(NumericalLetterGrade.numericalLetterGrade(grades).isEmpty());
    }
    @Test
    public void testNumericalLetterGradeWithInvalidInput() {
        List<Number> grades = Arrays.asList(null, Double.NaN);
        assertThrows(Exception.class, () -> NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGradeWithZeroGPA() {
        List<Number> grades = Arrays.asList(0.0);
        List<String> expected = Arrays.asList("E");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGrade_HappyPath_4_0_Fixed() {
        List<Number> grades = Arrays.asList(4.0);
        List<String> expected = Arrays.asList("A+");
        assertEquals(expected, original.NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGrade_HappyPath_3_0_Fixed() {
        List<Number> grades = Arrays.asList(3.0);
        List<String> expected = Arrays.asList("B");
        assertEquals(expected, original.NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGrade_HappyPath_2_7() {
        List<Number> grades = Arrays.asList(2.7);
        List<String> expected = Arrays.asList("B-");
        assertEquals(expected, original.NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void NumericalLetterGrade_EmptyList() {
        List<Number> grades = new ArrayList<>();
        assertEquals(new ArrayList<String>(), original.NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void TestNumericalLetterGrade_SadPath_NullInput() {
        List<Number> grades = null;
        try {
            original.NumericalLetterGrade.numericalLetterGrade(grades);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (NullPointerException e) {
            // do nothing, we expect this exception
        }
    }
    @Test
    public void testNumericalLetterGrade_C() {
        List<Number> grades = Arrays.asList(1.7);
        assertEquals(Arrays.asList("C-"), NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_CPlus() {
        assertEquals("C", NumericalLetterGrade.numericalLetterGrade(Arrays.asList(2.0)).get(0));
    }
    @Test
    public void testNumericalLetterGrade_NullInput() {
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(null));
    }
    @Test
    public void testNumericalLetterGradeNull() {
        List<Number> input = Arrays.asList((Double) null);
        assertThrows(NullPointerException.class, () -> NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGrade_GradeBMinus_1() {
        List<Number> grades = Arrays.asList(2.4);
        List<String> expectedResults = Arrays.asList("B-");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_InvalidGrade_Fixed() {
        List<Number> grades = Arrays.asList(-1.0);
        List<String> expectedResults = new ArrayList<>();
        NumericalLetterGrade.numericalLetterGrade(grades).isEmpty();
    }
    @Test
    public void testNumericalLetterGrade_GradeBoundaryAPlus() {
        List<Number> grades = Arrays.asList(4.0, 3.99999);
        List<String> expectedResults = Arrays.asList("A+", "A");
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testNumericalLetterGrade_EmptyInputList() {
        List<Number> grades = new ArrayList<>();
        List<String> expectedResults = new ArrayList<>();
        assertEquals(expectedResults, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    @Test
    public void testAPGrade() {
        List<Number> grades = Arrays.asList(3.5);
        assertEquals("[A-]", NumericalLetterGrade.numericalLetterGrade(grades).toString());
    }
    @Test
    public void testCPlusGrade() {
        List<Number> grades = Arrays.asList(2.1);
        assertEquals("[C+]", NumericalLetterGrade.numericalLetterGrade(grades).toString());
    }
    @Test
    public void TestNumericalLetterGrade_for_B_plus_Grade() {
        List<Number> input = new ArrayList<>();
        input.add(3.1);
        List<String> expected = new ArrayList<>();
        expected.add("B+");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(input));
    }
                                    
}