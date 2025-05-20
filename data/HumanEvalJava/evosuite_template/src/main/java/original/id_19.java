package original;


import java.util.Arrays;


class SortNumbers {
    /**
     * Input is a space-delimited string of numbers from 'zero' to 'nine'.
     * Valid choices are 'zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight' and 'nine'.
     * Return the string with numbers sorted from smallest to largest
     *
     * > sortNumbers("three one five")
     * "one three five"
     * > sortNumbers("")
     * ""
     * > sortNumbers("three")
     * "three"
     * > sortNumbers("three five nine")
     * "three five nine"
     * > sortNumbers("five zero four seven nine eight")
     * "zero four five seven eight nine"
     * > sortNumbers("six five four three two one zero")
     * "zero one two three four five six"
     */
    public static String sortNumbers(String numbers) {
        String[] numbersArray = numbers.split(" ");

        int[] numbersInt = new int[numbersArray.length];
        for (int i = 0; i < numbersArray.length; i++) {

            if (numbersArray[i].equals("zero")) {
                numbersInt[i] = 0;
            } else if (numbersArray[i].equals("one")) {
                numbersInt[i] = 1;
            } else if (numbersArray[i].equals("two")) {
                numbersInt[i] = 2;
            } else if (numbersArray[i].equals("three")) {
                numbersInt[i] = 3;
            } else if (numbersArray[i].equals("four")) {
                numbersInt[i] = 4;
            } else if (numbersArray[i].equals("five")) {
                numbersInt[i] = 5;
            } else if (numbersArray[i].equals("six")) {
                numbersInt[i] = 6;
            } else if (numbersArray[i].equals("seven")) {
                numbersInt[i] = 7;
            } else if (numbersArray[i].equals("eight")) {
                numbersInt[i] = 8;
            } else if (numbersArray[i].equals("nine")) {
                numbersInt[i] = 9;
            } else {
                numbersInt[i] = -1;
            }
        }
        Arrays.sort(numbersInt);
        String result = "";
        for (int i = 0; i < numbersInt.length; i++) {
            if (numbersInt[i] == 0) {
                result += "zero ";
            } else if (numbersInt[i] == 1) {
                result += "one ";
            } else if (numbersInt[i] == 2) {
                result += "two ";
            } else if (numbersInt[i] == 3) {
                result += "three ";
            } else if (numbersInt[i] == 4) {
                result += "four ";
            } else if (numbersInt[i] == 5) {
                result += "five ";
            } else if (numbersInt[i] == 6) {
                result += "six ";
            } else if (numbersInt[i] == 7) {
                result += "seven ";
            } else if (numbersInt[i] == 8) {
                result += "eight ";
            } else if (numbersInt[i] == 9) {
                result += "nine ";
            }
        }
        return result.trim();
    }


}