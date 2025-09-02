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
    void testNumericalLetterGrade_WhenGpaIs40_ReturnAPlus() {
        List<Number> grades = new ArrayList<>();
        grades.add(4.0);
        assertEquals("A+", NumericalLetterGrade.numericalLetterGrade(grades).get(0));
    }
}