package original;

import java.util.List;


class MeanAbsoluteDeviation {
    /**
     * For a given list of input numbers, calculate Mean Absolute Deviation
     * around the mean of this dataset.
     * Mean Absolute Deviation is the average absolute difference between each
     * element and a centerpoint (mean in this case):
     * MAD = average | x - x_mean |
     *
     * > meanAbsoluteDeviation([1.0, 2.0, 3.0])
     * 0.6666666666666666
     * > meanAbsoluteDeviation([1.0, 2.0, 3.0, 4.0])
     * 1.0
     * > meanAbsoluteDeviation([1.0, 2.0, 3.0, 4.0, 5.0])
     * 1.2
     */
    public static Double meanAbsoluteDeviation(List<Double> numbers) {
        Double mean = 0.0;
        for (Double number : numbers) {
            mean += number;
        }
        mean /= numbers.size();
        Double mad = 0.0;
        for (Double number : numbers) {
            mad += Math.abs(number - mean);
        }
        return mad / numbers.size();
    }


}