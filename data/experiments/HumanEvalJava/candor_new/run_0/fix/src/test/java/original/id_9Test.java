package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RollingMax.
*/
class RollingMaxTest {

    @Test
    void testRollingMax_WithListContainingOnlyOneElement_ReturnsListWithSameElement() {
        // Arrange
        List<Object> numbers = new ArrayList<>();
        numbers.add(10);
        List<Object> expected = new ArrayList<>();
        expected.add(10);
        
        // Act
        List<Object> result = RollingMax.rollingMax(numbers);
        
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            RollingMax s = new RollingMax();
            }
    @Test
    public void testRollingMaxWithNonIntegerValue() {
        List<java.lang.Object> numbers = new java.util.ArrayList<>();
        numbers.add("hello");
        ClassCastException exception = org.junit.jupiter.api.Assertions.assertThrows(ClassCastException.class, () -> original.RollingMax.rollingMax(numbers));
        org.junit.jupiter.api.Assertions.assertNotNull(exception.getMessage());
    }
    @Test
    public void testRollingMaxWithMultipleElementsList() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
    @Test
    public void testRollingMaxWithDecreasingSequence() {
        List<Object> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(4);
        numbers.add(3);
        numbers.add(2);
        numbers.add(1);
        List<Object> expected = new ArrayList<>();
        expected.add(5);
        expected.add(5);
        expected.add(5);
        expected.add(5);
        expected.add(5);
        assertEquals(expected, RollingMax.rollingMax(numbers));
    }
                                    
}