package original;

import java.util.ArrayList;
import java.util.List;


class ParseMusic {
    /**
     * Input to this function is a string representing musical notes in a special ASCII format.
     * Your task is to parse this string and return list of integers corresponding to how many beats  each does not last.
     * Here is a legend:
     * 'o' - whole note, lasts four beats
     * 'o|' - half note, lasts two beats
     * '.|' - quarter note, lasts one beat
     *
     * > parseMusic("o o| .| o| o| .| .| .| .| o o")
     * [4, 2, 1, 2, 2, 1, 1, 1, 1, 4, 4]
     * > parseMusic("")
     * []
     * > parseMusic("o o o o")
     * [4, 4, 4, 4]
     * > parseMusic(".| .| .| .|")
     * [1, 1, 1, 1]
     * > parseMusic("o| o| .| .| o o o o")
     * [2, 2, 1, 1, 4, 4, 4, 4]
     * > parseMusic("o| .| o| .| o o| o o|")
     * [2, 1, 2, 1, 4, 2, 4, 2]
     */
    public static List<Object> parseMusic(String musicString) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < musicString.length(); i++) {
            if (musicString.charAt(i) == 'o') {
                if (i < musicString.length() - 1 && musicString.charAt(i + 1) == '|') {
                    result.add(2);
                    i++;
                } else {
                    result.add(4);
                }
            } else if (musicString.charAt(i) == '.') {
                result.add(1);
                i++;
            }
        }
        return result;
    }

}