package original;


import java.util.List;


class BelowZero {
    /**
     * You're given a list of deposit and withdrawal operations on a bank account that starts with
     * zero balance. Your task is to detect if at any point the balance of account falls below zero, and
     * at that point function should return True. Otherwise, it should return False.
     *
     * > belowZero([1, 2, 3])
     * false
     * > belowZero([1, 2, -4, 5])
     * true
     * > belowZero([])
     * false
     * > belowZero([1, 2, -3, 1, 2, -3])
     * false
     * > belowZero([1, 2, -4, 5, 6])
     * true
     * > belowZero([1, -1, 2, -2, 5, -5, 4, -4])
     * false
     * > belowZero([1, -1, 2, -2, 5, -5, 4, -5])
     * true
     * > belowZero([1, -2, 2, -2, 5, -5, 4, -4])
     * true
     */
    public static Boolean belowZero(List<Object> operations) {
        Double balance = 0.0;
        for (Object operation : operations) {
            if (operation instanceof Double) {
                balance += (Double) operation;
            } else if (operation instanceof Integer) {
                balance += (Integer) operation;
            } else {
                throw new IllegalArgumentException("Invalid operation type");
            }
            if (balance < 0) {
                return true;
            }
        }
        return false;
    }


}