package original;

import java.util.Arrays;
class Bitset {
    private char[] a;
    private char[] b;
    private int cnt;

    public Bitset(int size) {
        a = new char[size];
        b = new char[size];
        Arrays.fill(a, '0');
        Arrays.fill(b, '1');
    }

    public void fix(int idx) {
  /**
  A Bitset is a data structure that compactly stores bits. Implement the Bitset class: Bitset(int size) Initializes the Bitset with size bits, all of which are 0. void fix(int idx) Updates the value of the bit at the index idx to 1. If the value was already 1, no change occurs. void unfix(int idx) Updates the value of the bit at the index idx to 0. If the value was already 0, no change occurs. void flip() Flips the values of each bit in the Bitset. In other words, all bits with value 0 will now have value 1 and vice versa. boolean all() Checks if the value of each bit in the Bitset is 1. Returns true if it satisfies the condition, false otherwise. boolean one() Checks if there is at least one bit in the Bitset with value 1. Returns true if it satisfies the condition, false otherwise. int count() Returns the total number of bits in the Bitset which have value 1. String toString() Returns the current composition of the Bitset. Note that in the resultant string, the character at the ith index should coincide with the value at the ith bit of the Bitset. &nbsp; Example 1: Input [&quot;Bitset&quot;, &quot;fix&quot;, &quot;fix&quot;, &quot;flip&quot;, &quot;all&quot;, &quot;unfix&quot;, &quot;flip&quot;, &quot;one&quot;, &quot;unfix&quot;, &quot;count&quot;, &quot;toString&quot;] [[5], [3], [1], [], [], [0], [], [], [0], [], []] Output [null, null, null, null, false, null, null, true, null, 2, &quot;01010&quot;] Explanation Bitset bs = new Bitset(5); // bitset = &quot;00000&quot;. bs.fix(3); // the value at idx = 3 is updated to 1, so bitset = &quot;00010&quot;. bs.fix(1); // the value at idx = 1 is updated to 1, so bitset = &quot;01010&quot;. bs.flip(); // the value of each bit is flipped, so bitset = &quot;10101&quot;. bs.all(); // return False, as not all values of the bitset are 1. bs.unfix(0); // the value at idx = 0 is updated to 0, so bitset = &quot;00101&quot;. bs.flip(); // the value of each bit is flipped, so bitset = &quot;11010&quot;. bs.one(); // return True, as there is at least 1 index with value 1. bs.unfix(0); // the value at idx = 0 is updated to 0, so bitset = &quot;01010&quot;. bs.count(); // return 2, as there are 2 bits with value 1. bs.toString(); // return &quot;01010&quot;, which is the composition of bitset. &nbsp; Constraints: 1 &lt;= size &lt;= 105 0 &lt;= idx &lt;= size - 1 At most 105 calls will be made in total to fix, unfix, flip, all, one, count, and toString. At least one call will be made to all, one, count, or toString. At most 5 calls will be made to toString.
  */
        if (a[idx] == '0') {
            a[idx] = '1';
            ++cnt;
        }
        b[idx] = '0';
    }

    public void unfix(int idx) {
        if (a[idx] == '1') {
            a[idx] = '0';
            --cnt;
        }
        b[idx] = '1';
    }

    public void flip() {
        char[] t = a;
        a = b;
        b = t;
        cnt = a.length - cnt;
    }

    public boolean all() {
        return cnt == a.length;
    }

    public boolean one() {
        return cnt > 0;
    }

    public int count() {
        return cnt;
    }

    public String toString() {
        return String.valueOf(a);
    }
}

/**
 * Your Bitset object will be instantiated and called as such:
 * Bitset obj = new Bitset(size);
 * obj.fix(idx);
 * obj.unfix(idx);
 * obj.flip();
 * boolean param_4 = obj.all();
 * boolean param_5 = obj.one();
 * int param_6 = obj.count();
 * String param_7 = obj.toString();
 */