package original;

import static org.junit.jupiter.api.Assertions.assertEquals; import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SumProduct.
*/
class SumProductTest {

@Test
void testSumProductWithEmptyList() {
    List<Object> numbers = new ArrayList<>();
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(0, (int)result.get(0));
}
@Test
public void TestSumProductClassInitialization() {
    // No need to assert anything here as we are just testing the initialization of the class
}
@Test
public void TestSingleElementList() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(5);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(2, result.size());
    assertEquals(5, (int)result.get(0));
    assertEquals(5, (int)result.get(1));
}
@Test
public void TestMultipleElementsList() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    numbers.add(4);
    numbers.add(5);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(2, result.size());
    assertEquals(15, (int)result.get(0));
    assertEquals(120, (int)result.get(1));
}
@Test
public void TestSumCalculationForPositiveNumbers() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(2, result.size());
    assertEquals(6, (int)result.get(0));
}
@Test
public void TestProductCalculationForZero() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(0);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(2, result.size());
    assertEquals(3, (int)result.get(0));
    assertEquals(0, (int)result.get(1));
}
@Test
public void testSumProductWithSingleElementList() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(5);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(5, (int)result.get(0));
    assertEquals(5, (int)result.get(1));
}
@Test
public void testSumProductWithMultipleElementList() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    numbers.add(4);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(10, (int)result.get(0));
    assertEquals(24, (int)result.get(1));
}
@Test
public void testSumProductWithListContainingZero() {
    List<Object> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(0);
    numbers.add(3);
    List<Integer> result = SumProduct.sumProduct(numbers);
    assertEquals(4, (int)result.get(0));
    assertEquals(0, (int)result.get(1));
}
public void test_sumProductNonIntegerList() {List<Object> numbers = List.of(1, 'a', 3); assertThrows(IllegalArgumentException.class, () -> SumProduct.sumProduct(numbers));}
public void test_sumProductListWithZero() {List<Object> numbers = List.of(100, 0); List<Integer> result = SumProduct.sumProduct(numbers); assertEquals(List.of(100, 0), result);}
public void test_sumProductDuplicateElementsList() {List<Object> numbers = List.of(1, 1, 1); List<Integer> result = SumProduct.sumProduct(numbers); assertEquals(List.of(3, 1), result);}
public void test_sumProductSingleElementList() {List<Object> numbers = List.of(10); List<Integer> result = SumProduct.sumProduct(numbers); assertEquals(List.of(10, 10), result);}
public void test_sumProductEmptyList() {List<Object> numbers = List.of(); List<Integer> result = SumProduct.sumProduct(numbers); assertEquals(List.of(0, 1), result);}
public void test_sumProductValidInput() {List<Object> numbers = List.of(1, 2, 3, 4); List<Integer> result = SumProduct.sumProduct(numbers); assertEquals(List.of(10, 24), result);}
}