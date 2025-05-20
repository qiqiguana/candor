package original;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void test_simplify_1() {
        String x = "1/5";
        String n = "5/1";
        assertTrue(Simplify.simplify(x, n));
    }
    @Test
    public void testSimplifyWithValidFractionsThatDoNotEvaluateToWholeNumbers() {
        boolean result = Simplify.simplify("1/3", "2/3");
        assertFalse(result);
    }
    @Test
    public void testSimplifyWithValidFractions() {
        boolean result = Simplify.simplify("2/3", "3/2");
        assertTrue(result);
    }
    @Test
    void testClassInitialization() {
        // Given
        Simplify simplify = new Simplify();
        // Then
        assertNotNull(simplify);
    }
    public class TestSimplify {
      @Test
      public void testSimplify() {
        boolean result = Simplify.simplify("1/5", "5/1");
        assertTrue(result);
      }
      @Test
      public void testSimplifyTrue() {
          boolean result = original.Simplify.simplify("1/5", "5/1");
          assertTrue(result);
      }
      @Test
      public void testSimplifyFalse() {
          boolean result = original.Simplify.simplify("1/6", "2/1");
          assertFalse(result);
      }
      @Test
      public void testSimplifyEdgeCase() {
          boolean result = original.Simplify.simplify("5/1", "3/1");
          assertTrue(result);
      }
      @Test
      public void testSimplifyInvalidInput() {
          assertThrows(NumberFormatException.class, () -> original.Simplify.simplify("a/b", "c/d"));
      }
    }
}