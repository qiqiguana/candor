package original;


import java.util.ArrayList;
import java.util.List;


class NumericalLetterGrade {
    /**
     * It is the last week of the semester and the teacher has to give the grades
     * to students. The teacher has been making her own algorithm for grading.
     * The only problem is, she has lost the code she used for grading.
     * She has given you a list of GPAs for some students and you have to write
     * a function that can output a list of letter grades using the following table:
     * GPA       |    Letter grade
     * 4.0                A+
     * > 3.7                A
     * > 3.3                A-
     * > 3.0                B+
     * > 2.7                B
     * > 2.3                B-
     * > 2.0                C+
     * > 1.7                C
     * > 1.3                C-
     * > 1.0                D+
     * > 0.7                D
     * > 0.0                D-
     * 0.0                E
     *
     *
     * Example:
     * numericalLetterGrade([4.0, 3, 1.7, 2, 3.5]) ==> ['A+', 'B', 'C-', 'C', 'A-']
     *
     * > numericalLetterGrade([4.0, 3, 1.7, 2, 3.5])
     * ["A+", "B", "C-", "C", "A-"]
     * > numericalLetterGrade([1.2])
     * ["D+"]
     * > numericalLetterGrade([0.5])
     * ["D-"]
     * > numericalLetterGrade([0.0])
     * ["E"]
     * > numericalLetterGrade([1, 0.3, 1.5, 2.8, 3.3])
     * ["D", "D-", "C-", "B", "B+"]
     * > numericalLetterGrade([0, 0.7])
     * ["E", "D-"]
     */
    public static List<String> numericalLetterGrade(List<Number> grades) {
        List<String> result = new ArrayList<String>();
        for (Number grade : grades) {
            if (grade.doubleValue() >= 4.0) {
                result.add("A+");
            } else if (grade.doubleValue() > 3.7) {
                result.add("A");
            } else if (grade.doubleValue() > 3.3) {
                result.add("A-");
            } else if (grade.doubleValue() > 3.0) {
                result.add("B+");
            } else if (grade.doubleValue() > 2.7) {
                result.add("B");
            } else if (grade.doubleValue() > 2.3) {
                result.add("B-");
            } else if (grade.doubleValue() > 2.0) {
                result.add("C+");
            } else if (grade.doubleValue() > 1.7) {
                result.add("C");
            } else if (grade.doubleValue() > 1.3) {
                result.add("C-");
            } else if (grade.doubleValue() > 1.0) {
                result.add("D+");
            } else if (grade.doubleValue() > 0.7) {
                result.add("D");
            } else if (grade.doubleValue() > 0.0) {
                result.add("D-");
            } else {
                result.add("E");
            }
        }
        return result;
    }


}