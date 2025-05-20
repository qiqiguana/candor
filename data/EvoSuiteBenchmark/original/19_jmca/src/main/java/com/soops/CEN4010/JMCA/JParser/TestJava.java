package com.soops.CEN4010.JMCA.JParser;


public class TestJava {
    public TestJava() {
    }

    JavaParser Parser = null;
    boolean flag, anotherFlag, andYetAnotherFlag;
    int classLevelVariable;
    double anotherClassVariable;

    void method1(int param1, double param2, JavaParser P) {
        classLevelVariable = param1;

        switch (classLevelVariable) {

        case 1:
            anotherClassVariable = 1 + param2;
            break;
            case 2:
             anotherClassVariable = param2;
             break;
             default:
                 System.out.println("Default Value Used");
        }
    }

    void method2() {
        if (classLevelVariable >= 0) {
            int tmpVar = 5;
        } else if (classLevelVariable == 0) {
            int tmpVar = (int) anotherClassVariable;
        }
    }


}
