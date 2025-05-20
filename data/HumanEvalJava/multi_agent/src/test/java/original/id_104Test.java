package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of UniqueDigits.
*/
class UniqueDigitsTest {

@Test
void uniqueDigits_should_Return_Sorted_List_When_No_Even_Digits() {
	List<Integer> input = new ArrayList<>();
	input.add(15);
	input.add(33);
	input.add(1422);
	input.add(1);
	List<Object> actual = UniqueDigits.uniqueDigits(input);
	assertArrayEquals(new Object[]{1, 15, 33}, actual.toArray());
}

}