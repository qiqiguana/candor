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
    void testNumericalLetterGrade_APlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        List<String> result = NumericalLetterGrade.numericalLetterGrade(grades);
        assertEquals("A+", result.get(0));
    }
}