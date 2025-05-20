package jigl.util;

public final class MathUtil {
	private MathUtil() {}
	
	/*
	 * See http://en.wikipedia.org/wiki/Power_of_two#Fast_algorithm_to_check_if_a_positive_number_is_a_power_of_two
	 */
	public static boolean powerOf2(final int x) {
		if(x == 0) return false;
		return (x & (x - 1)) == 0;
	}
}
