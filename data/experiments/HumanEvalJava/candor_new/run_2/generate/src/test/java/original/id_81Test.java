package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NumericalLetterGrade.
*/
class NumericalLetterGradeTest {
    @Test
    void testNumericalLetterGrade_GradeAPlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> expected = new ArrayList<>();
        expected.add("A+");
        assertEquals(expected, NumericalLetterGrade.numericalLetterGrade(grades));
    }
    
    @Test
        public void testNothing(){
            NumericalLetterGrade s = new NumericalLetterGrade();
            }
    @Test
    public void testNumericalLetterGradeConversion() {
    	List<Number> grades = new ArrayList<>();
    	grades.add(3.5);
    	List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
    	assertEquals("A-", result.get(0));
    }
    @Test
    public void testLowerBoundCorrected() {
        List<String> result = NumericalLetterGrade.numericalLetterGrade(List.of(0.5));
        assertEquals("D-", result.get(0));
    }
    @Test
    public void testNumericalGrade30() {
        List<Number> grades = new ArrayList<>();
        grades.add(3.0);
        assertEquals("B", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
                                    
}