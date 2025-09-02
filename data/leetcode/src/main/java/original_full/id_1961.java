package original;

class Solution1961 {
    public boolean isPrefixString(String s, String[] words) {
        StringBuilder t = new StringBuilder();
        for (var w : words) {
            t.append(w);
            if (t.length() > s.length()) {
                return false;
            }
            if (t.length() == s.length()) {
                return s.equals(t.toString());
            }
        }
        return false;
    }
}