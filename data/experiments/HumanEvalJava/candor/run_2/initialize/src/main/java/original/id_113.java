package original;


import java.util.ArrayList;
import java.util.List;


class OddCount {
    /**
     * Given a list of strings, where each string consists of only digits, return a list.
     * Each element i of the output should be "the number of odd elements in the
     * string i of the input." where all the i's should be replaced by the number
     * of odd digits in the i'th string of the input.
     *
     * >>> oddCount(['1234567'])
     * ["the number of odd elements 4n the str4ng 4 of the 4nput."]
     * >>> oddCount(['3',"11111111"])
     * ["the number of odd elements 1n the str1ng 1 of the 1nput.",
     * "the number of odd elements 8n the str8ng 8 of the 8nput."]
     *
     * > oddCount(["1234567"])
     * ["the number of odd elements 4n the str4ng 4 of the 4nput."]
     * > oddCount(["3", "11111111"])
     * ["the number of odd elements 1n the str1ng 1 of the 1nput.", "the number of odd elements 8n the str8ng 8 of the 8nput."]
     * > oddCount(["271", "137", "314"])
     * ["the number of odd elements 2n the str2ng 2 of the 2nput.", "the number of odd elements 3n the str3ng 3 of the 3nput.", "the number of odd elements 2n the str2ng 2 of the 2nput."]
     */
    public static List<String> oddCount(List<String> lst) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < lst.size(); i++) {
            int count = 0;
            for (int j = 0; j < lst.get(i).length(); j++) {
                if (Integer.parseInt(lst.get(i).substring(j, j + 1)) % 2 != 0) {
                    count++;
                }
            }
            result.add("the number of odd elements " + count + "n the str" + count + "ng " + count + " of the " + count + "nput.");
        }
        return result;
    }


}