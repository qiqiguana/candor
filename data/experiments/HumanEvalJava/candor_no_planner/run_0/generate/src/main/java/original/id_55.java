package original;


class Fib {
    /**
     * Return n-th Fibonacci number.
     *
     * > fib(10)
     * 55
     * > fib(1)
     * 1
     * > fib(8)
     * 21
     * > fib(11)
     * 89
     * > fib(12)
     * 144
     */
    public static int fib(int n) {
        int a = 0;
        int b = 1;
        int c = 1;
        for (int i = 0; i < n - 1; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }


}