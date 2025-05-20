package ipac;

import java.text.*;
import java.util.*;
import java.math.*;

public class BinaryCalculate {

    public String addBinary(String num1, String num2) {
        boolean carry = false;
        int size1 = num1.length() - 1;
        int size2 = num2.length() - 1;
        String sum = "";
        if (size2 > size1) {
            String s = num1;
            num1 = num2;
            num2 = s;
            int n = size1;
            size1 = size2;
            size2 = n;
        }
        for (; size2 >= 0; size1--, size2--) {
            if (num1.charAt(size1) == '0' && num2.charAt(size2) == '0') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = false;
            } else if (num1.charAt(size1) == '1' && num2.charAt(size2) == '0') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            } else if (num1.charAt(size1) == '0' && num2.charAt(size2) == '1') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            } else if (num1.charAt(size1) == '1' && num2.charAt(size2) == '1') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = true;
            }
        }
        for (; size1 >= 0; size1--) {
            if (num1.charAt(size1) == '0') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = false;
            } else if (num1.charAt(size1) == '1') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            }
        }
        if (carry)
            sum = "1" + sum;
        return sum;
    }
}
