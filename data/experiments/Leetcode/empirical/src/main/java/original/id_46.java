package original;

class Fib4 {
    /**
     * The Fib4 number sequence is a sequence similar to the Fibbonacci sequence that's defined as follows:
     * fib4(0) -> 0
     * fib4(1) -> 0
     * fib4(2) -> 2
     * fib4(3) -> 0
     * fib4(n) -> fib4(n-1) + fib4(n-2) + fib4(n-3) + fib4(n-4).
     * Please write a function to efficiently compute the n-th element of the fib4 number sequence.  Do not use recursion.
     *
     * > fib4(5)
     * 4
     * > fib4(6)
     * 8
     * > fib4(7)
     * 14
     * > fib4(5)
     * 4
     * > fib4(8)
     * 28
     * > fib4(10)
     * 104
     * > fib4(12)
     * 386
     */
    public static int fib4(int n) {
        if (n < 2) {
            return 0;
        }
        if (n == 2) {
            return 2;
        }
        int a = 0;
        int b = 0;
        int c = 2;
        int d = 0;
        int e = 0;
        for (int i = 4; i <= n; i++) {
            e = a + b + c + d;
            a = b;
            b = c;
            c = d;
            d = e;
        }
        return e;
    }


}