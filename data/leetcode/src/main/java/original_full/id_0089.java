package original;

import java.util.ArrayList;
import java.util.List;
class Solution0089 {
    public List<Integer> grayCode(int n) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < 1 << n; ++i) {
            ans.add(i ^ (i >> 1));
        }
        return ans;
    }
}