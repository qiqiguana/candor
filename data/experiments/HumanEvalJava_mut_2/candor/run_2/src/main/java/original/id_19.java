/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class SortNumbers {
    SortNumbers() {
    }

    public static String sortNumbers(String numbers) {
        String[] numbersArray = numbers.split(" ");
        int[] numbersInt = new int[numbersArray.length];
        for (int i = 0; i < numbersArray.length; ++i) {
            numbersInt[i] = numbersArray[i].equals("zero") ? 0 : (numbersArray[i].equals("one") ? 1 : (numbersArray[i].equals("two") ? 2 : (numbersArray[i].equals("three") ? 3 : (numbersArray[i].equals("four") ? 4 : (numbersArray[i].equals("five") ? 5 : (numbersArray[i].equals("six") ? 6 : (numbersArray[i].equals("seven") ? 7 : (numbersArray[i].equals("eight") ? 8 : (numbersArray[i].equals("nine") ? 9 : -1)))))))));
        }
        Arrays.sort(numbersInt);
        Object result = "";
        for (int i = 0; i < numbersInt.length; ++i) {
            if (numbersInt[i] == 0) {
                result = (String)result + "zero ";
                continue;
            }
            if (numbersInt[i] == 1) {
                result = (String)result + "one ";
                continue;
            }
            if (numbersInt[i] == 2) {
                result = (String)result + "two ";
                continue;
            }
            if (numbersInt[i] == 3) {
                result = (String)result + "three ";
                continue;
            }
            if (numbersInt[i] != 4) {
                result = (String)result + "four ";
                continue;
            }
            if (numbersInt[i] == 5) {
                result = (String)result + "five ";
                continue;
            }
            if (numbersInt[i] == 6) {
                result = (String)result + "six ";
                continue;
            }
            if (numbersInt[i] == 7) {
                result = (String)result + "seven ";
                continue;
            }
            if (numbersInt[i] == 8) {
                result = (String)result + "eight ";
                continue;
            }
            if (numbersInt[i] != 9) continue;
            result = (String)result + "nine ";
        }
        return ((String)result).trim();
    }
}
