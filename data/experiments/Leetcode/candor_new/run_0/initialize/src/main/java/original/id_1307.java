package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
class Solution1307 {
    private boolean isAnyMapping(List<String> words, int row, int col, int bal,
  /**
  Given an equation, represented by words on the left side and the result on the right side. You need to check if the equation is solvable under the following rules: Each character is decoded as one digit (0 - 9). No two characters can map to the same digit. Each words[i] and result are decoded as one number without leading zeros. Sum of numbers on the left side (words) will equal to the number on the right side (result). Return true if the equation is solvable, otherwise return false. &nbsp; Example 1: Input: words = [&quot;SEND&quot;,&quot;MORE&quot;], result = &quot;MONEY&quot; Output: true Explanation: Map &#39;S&#39;-&gt; 9, &#39;E&#39;-&gt;5, &#39;N&#39;-&gt;6, &#39;D&#39;-&gt;7, &#39;M&#39;-&gt;1, &#39;O&#39;-&gt;0, &#39;R&#39;-&gt;8, &#39;Y&#39;-&gt;&#39;2&#39; Such that: &quot;SEND&quot; + &quot;MORE&quot; = &quot;MONEY&quot; , 9567 + 1085 = 10652 Example 2: Input: words = [&quot;SIX&quot;,&quot;SEVEN&quot;,&quot;SEVEN&quot;], result = &quot;TWENTY&quot; Output: true Explanation: Map &#39;S&#39;-&gt; 6, &#39;I&#39;-&gt;5, &#39;X&#39;-&gt;0, &#39;E&#39;-&gt;8, &#39;V&#39;-&gt;7, &#39;N&#39;-&gt;2, &#39;T&#39;-&gt;1, &#39;W&#39;-&gt;&#39;3&#39;, &#39;Y&#39;-&gt;4 Such that: &quot;SIX&quot; + &quot;SEVEN&quot; + &quot;SEVEN&quot; = &quot;TWENTY&quot; , 650 + 68782 + 68782 = 138214 Example 3: Input: words = [&quot;LEET&quot;,&quot;CODE&quot;], result = &quot;POINT&quot; Output: false Explanation: There is no possible mapping to satisfy the equation, so we return false. Note that two different characters cannot map to the same digit. &nbsp; Constraints: 2 &lt;= words.length &lt;= 5 1 &lt;= words[i].length, result.length &lt;= 7 words[i], result contain only uppercase English letters. The number of different characters used in the expression is at most 10.
  */
        HashMap<Character, Integer> letToDig, char[] digToLet, int totalRows, int totalCols) {
        // If traversed all columns.
        if (col == totalCols) {
            return bal == 0;
        }

        // At the end of a particular column.
        if (row == totalRows) {
            return (bal % 10 == 0
                && isAnyMapping(
                    words, 0, col + 1, bal / 10, letToDig, digToLet, totalRows, totalCols));
        }

        String w = words.get(row);

        // If the current string 'w' has no character in the ('col')th index.
        if (col >= w.length()) {
            return isAnyMapping(words, row + 1, col, bal, letToDig, digToLet, totalRows, totalCols);
        }

        // Take the current character in the variable letter.
        char letter = w.charAt(w.length() - 1 - col);

        // Create a variable 'sign' to check whether we have to add it or subtract it.
        int sign = (row < totalRows - 1) ? 1 : -1;

        // If we have a prior valid mapping, then use that mapping.
        // The second condition is for the leading zeros.
        if (letToDig.containsKey(letter)
            && (letToDig.get(letter) != 0 || (letToDig.get(letter) == 0 && w.length() == 1)
                || col != w.length() - 1)) {

            return isAnyMapping(words, row + 1, col, bal + sign * letToDig.get(letter), letToDig,
                digToLet, totalRows, totalCols);

        } else {
            // Choose a new mapping.
            for (int i = 0; i < 10; i++) {
                // If 'i'th mapping is valid then select it.
                if (digToLet[i] == '-'
                    && (i != 0 || (i == 0 && w.length() == 1) || col != w.length() - 1)) {
                    digToLet[i] = letter;
                    letToDig.put(letter, i);

                    // Call the function again with the new mapping.
                    if (isAnyMapping(words, row + 1, col, bal + sign * letToDig.get(letter),
                            letToDig, digToLet, totalRows, totalCols)) {
                        return true;
                    }

                    // Unselect the mapping.
                    digToLet[i] = '-';
                    letToDig.remove(letter);
                }
            }
        }

        // If nothing is correct then just return false.
        return false;
    }

    public boolean isSolvable(String[] wordsArr, String result) {
        // Add the string 'result' in the list 'words'.
        List<String> words = new ArrayList<>();
        for (String word : wordsArr) {
            words.add(word);
        }
        words.add(result);

        int totalRows = words.size();

        // Find the longest string in the list and set 'totalCols' with the size of that string.
        int totalCols = 0;
        for (String word : words) {
            if (totalCols < word.length()) {
                totalCols = word.length();
            }
        }

        // Create a HashMap for the letter to digit mapping.
        HashMap<Character, Integer> letToDig = new HashMap<>();

        // Create a char array for the digit to letter mapping.
        char[] digToLet = new char[10];
        for (int i = 0; i < 10; i++) {
            digToLet[i] = '-';
        }

        return isAnyMapping(words, 0, 0, 0, letToDig, digToLet, totalRows, totalCols);
    }
}