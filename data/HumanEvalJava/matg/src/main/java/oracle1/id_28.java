package oracle1;


import java.util.List;


class Concatenate {
    /**
     * Concatenate list of strings into a single string
     * Examples:
     * > concatenate([])
     * ""
     * > concatenate(["a", "b", "c"])
     * "abc"
     * > concatenate([])
     * ""
     * > concatenate(["x", "y", "z"])
     * "xyz"
     * > concatenate(["x", "y", "z", "w", "k"])
     * "xyzwk"
     */
    public static String concatenate(List<Object> strings) {
        String result = "";
        for (Object string : strings) {
            result += string.toString();
        }
        return result;
    }


}