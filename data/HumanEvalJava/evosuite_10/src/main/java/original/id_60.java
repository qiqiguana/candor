package original;

class SumToN {
    /**
     * sumToN is a function that sums numbers from 1 to n.
     *
     * > sumToN(5)
     * 15
     * > sumToN(10)
     * 55
     * > sumToN(1)
     * 1
     * > sumToN(6)
     * 21
     * > sumToN(11)
     * 66
     * > sumToN(30)
     * 465
     * > sumToN(100)
     * 5050
     */
    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }


}