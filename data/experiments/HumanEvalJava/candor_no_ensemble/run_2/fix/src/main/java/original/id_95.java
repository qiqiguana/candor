package original;

import java.util.Map;


class CheckDictCase {
    /**
     * * Given a dictionary, return True if all keys are strings in lower
     * case or all keys are strings in upper case, else return False.
     * The function should return False is the given dictionary is empty.
     * Examples:
     * check_dict_case({"a":"apple", "b":"banana"}) should return True.
     * check_dict_case({"a":"apple", "A":"banana", "B":"banana"}) should return False.
     * check_dict_case({"a":"apple", 8:"banana", "a":"apple"}) should return False.
     * check_dict_case({"Name":"John", "Age":"36", "City":"Houston"}) should return False.
     * check_dict_case({"STATE":"NC", "ZIP":"12345" }) should return True.
     *
     * > checkDictCase({"p": "pineapple", "b": "banana"})
     * true
     * > checkDictCase({"p": "pineapple", "A": "banana", "B": "banana"})
     * false
     * > checkDictCase({"p": "pineapple", 5: "banana", "a": "apple"})
     * false
     * > checkDictCase({"Name": "John", "Age": "36", "City": "Houston"})
     * false
     * > checkDictCase({"STATE": "NC", "ZIP": "12345"})
     * true
     * > checkDictCase({"fruit": "Orange", "taste": "Sweet"})
     * true
     * > checkDictCase({})
     * false
     */
    public static Boolean checkDictCase(Object dict) {
        if (dict instanceof Map) {
            Map m = (Map) dict;
            if (m.size() == 0) {
                return false;
            }
            boolean allLower = true;
            boolean allUpper = true;
            for (Object k : m.keySet()) {
                if (k instanceof String) {
                    String s = (String) k;
                    if (!s.equals(s.toLowerCase())) {
                        allLower = false;
                    }
                    if (!s.equals(s.toUpperCase())) {
                        allUpper = false;
                    }
                } else {
                    return false;
                }
            }
            return allLower || allUpper;
        }
        return false;
    }


}