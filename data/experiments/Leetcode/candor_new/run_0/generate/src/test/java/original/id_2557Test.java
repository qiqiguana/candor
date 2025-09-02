package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2557.
*/
class Solution2557Test {
	@Test
	void testMaxCount() {
		Solution2557 solution = new Solution2557();
		int[] banned = {1, 3};
		int n = 5;
		long maxSum = 15;
		assertEquals(3, solution.maxCount(banned, n, maxSum));
	}
 @Test
 public void testMaxCountExample1() {
     Solution2557 s = new Solution2557();
     int[] banned = {1, 4, 6};
     int n = 6;
     long maxSum = 4;
     int expected = 1;
     int actual = s.maxCount(banned, n, maxSum);
     assertEquals(expected, actual);
 }
 @Test
 public void testMaxCountLowN() {
     Solution2557 solution = new Solution2557();
     int[] banned = {4, 3, 5, 6};
     int n = 7;
     long maxSum = 0;
     int expected = 0;
     int actual = solution.maxCount(banned, n, maxSum);
     assertEquals(expected, actual);
 }
}