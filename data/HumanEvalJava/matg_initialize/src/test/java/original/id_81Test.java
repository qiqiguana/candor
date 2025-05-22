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
}