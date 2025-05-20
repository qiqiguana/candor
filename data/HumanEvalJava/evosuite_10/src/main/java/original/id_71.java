package original;

class TriangleArea1 {
    /**
     * * Given the lengths of the three sides of a triangle. Return the area of
     * the triangle rounded to 2 decimal points if the three sides form a valid triangle.
     * Otherwise return -1
     * Three sides make a valid triangle when the sum of any two sides is greater
     * than the third side.
     * Example:
     * triangle_area(3, 4, 5) == 6.00
     * triangle_area(1, 2, 10) == -1
     *
     * > triangleArea(3, 4, 5)
     * 6.0
     * > triangleArea(1, 2, 10)
     * -1
     * > triangleArea(4, 8, 5)
     * 8.18
     * > triangleArea(2, 2, 2)
     * 1.73
     * > triangleArea(1, 2, 3)
     * -1
     * > triangleArea(10, 5, 7)
     * 16.25
     * > triangleArea(2, 6, 3)
     * -1
     * > triangleArea(1, 1, 1)
     * 0.43
     * > triangleArea(2, 2, 10)
     * -1
     */
    public static Number triangleArea(int a, int b, int c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            return -1;
        }
        double p = (a + b + c) / 2.0;
        return Math.round(Math.sqrt(p * (p - a) * (p - b) * (p - c)) * 100.0) / 100.0;
    }


}