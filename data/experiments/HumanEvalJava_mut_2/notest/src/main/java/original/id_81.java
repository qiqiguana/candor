/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class NumericalLetterGrade {
    NumericalLetterGrade() {
    }

    public static List<String> numericalLetterGrade(List<Number> grades) {
        ArrayList<String> result = new ArrayList<String>();
        for (Number grade : grades) {
            if (grade.doubleValue() >= 4.0) {
                result.add("A+");
                continue;
            }
            if (grade.doubleValue() > 3.7) {
                result.add("A");
                continue;
            }
            if (grade.doubleValue() > 3.3) {
                result.add("A-");
                continue;
            }
            if (grade.doubleValue() > 3.0) {
                result.add("B+");
                continue;
            }
            if (grade.doubleValue() > 2.7) {
                result.add("B");
                continue;
            }
            if (grade.doubleValue() > 2.3) {
                result.add("B-");
                continue;
            }
            if (grade.doubleValue() > 2.0) {
                result.add("C+");
                continue;
            }
            if (grade.doubleValue() > 1.7) {
                result.add("C");
                continue;
            }
            if (grade.doubleValue() > 1.3) {
                result.add("C-");
                continue;
            }
            if (grade.doubleValue() >= 1.0) {
                result.add("D+");
                continue;
            }
            if (grade.doubleValue() > 0.7) {
                result.add("D");
                continue;
            }
            if (grade.doubleValue() > 0.0) {
                result.add("D-");
                continue;
            }
            result.add("E");
        }
        return result;
    }
}
