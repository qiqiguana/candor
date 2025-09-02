package original;

class Solution0461 {
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}