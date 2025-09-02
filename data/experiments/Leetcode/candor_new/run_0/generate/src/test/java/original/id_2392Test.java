package original;

import java.util.ArrayDeque;

import java.util.Arrays;

import java.util.ArrayList;

import java.util.Deque;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2392.
*/
class Solution2392Test {

	@Test
	void testBuildArray_1() {
		// TODO: test code here
	}
 
 @Test
     public void testNothing(){
         Solution2392 s = new Solution2392();
         }
 @Test
 public void buildMatrix_test_empty_input_within_constraints_1() {
     int[][] res = new Solution2392().buildMatrix(2, new int[0][2], new int[0][2]);
     assertEquals(2, res.length);
     assertEquals(2, res[0].length);
 }
                                 

}