package net.sourceforge.ext4j.taglib.tag;

import java.util.List;

public class MathUtils {

	public static double average(List<Long> pValues) {
		double sum = 0;
		for (long i : pValues) {
			if (i == Long.MAX_VALUE)
				break;
			sum += i;
		}
		return sum / (double) (pValues.size() - 1);
	}

}
