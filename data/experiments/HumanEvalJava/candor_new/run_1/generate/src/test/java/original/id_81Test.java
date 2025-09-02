package original;

import java.util.Collections;
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
    void testNumericalLetterGrade_WhenGpaIs40_ReturnAPlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertEquals("A+", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
    
    @Test
        public void testNothing(){
            NumericalLetterGrade s = new NumericalLetterGrade();
            }
    @Test
    public void testNumericalLetterGradeForCPlus() {
        List<Number> input = Arrays.asList(2.3);
        List<String> expectedOutput = Collections.singletonList("C+");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeForDPlus() {
        List<Number> input = Arrays.asList(1.3);
        List<String> expectedOutput = Collections.singletonList("D+");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGradeForF1Fixed() {
        List<Number> input = Arrays.asList(0.0);
        List<String> expectedOutput = Collections.singletonList("E");
        assertEquals(expectedOutput, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void Test_A_Grade_1() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(3.8);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("A", result.get(0));
    }
    @Test
    public void Test_A_Minus_Grade_2() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(3.4);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("A-", result.get(0));
    }
    @Test
    public void Test_B_Plus_Grade_3() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(3.1);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("B+", result.get(0));
    }
    @Test
    public void Test_B_Grade_4() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(2.8);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("B", result.get(0));
    }
    @Test
    public void Test_C_Minus_Grade_5() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(1.4);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("C-", result.get(0));
    }
    @Test
    public void Test_D_Grade_6() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(0.8);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("D", result.get(0));
    }
    @Test
    public void Test_D_Minus_Grade_7() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(0.1);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("D-", result.get(0));
    }
    @Test
    public void testNumericalLetterGrade_B_Grade() {
    	List<Number> input = new ArrayList<>();
    	input.add(2.4);
    	List<String> expected_result = new ArrayList<>();
    	expected_result.add("B-");
    	assertEquals(expected_result, NumericalLetterGrade.numericalLetterGrade(input));
    }
    @Test
    public void testNumericalLetterGrade_C_Grade() {
    	List<Number> input = new ArrayList<>();
    	input.add(1.8);
    	List<String> expected_result = new ArrayList<>();
    	expected_result.add("C");
    	assertEquals(expected_result, NumericalLetterGrade.numericalLetterGrade(input));
    }
                                    
}