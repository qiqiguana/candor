package ipac;

import java.text.*;
import java.util.*;
import java.math.*;

public class BinaryCalculate {

    /**
     *  Subtracts two binary numbers.
     *
     * @param num1 The starting binary number
     * @param num2 The binary number to be subtracted from the
     *                       starting binary
     * @return The difference of two binary numbers.
     */
    public String subBinary(String num1, String num2) {
        String diff = "";
        String twosComp = "";
        int size = num1.length() - 1;
        for (; size >= 0; size--) {
            if (num1.charAt(size) == '0')
                twosComp = "1" + twosComp;
            else
                twosComp = "0" + twosComp;
        }
        twosComp = "0" + twosComp;
        twosComp = addBinary(twosComp, "1");
        diff = addBinary(twosComp, num2);
        return diff.substring(1);
    }
}
