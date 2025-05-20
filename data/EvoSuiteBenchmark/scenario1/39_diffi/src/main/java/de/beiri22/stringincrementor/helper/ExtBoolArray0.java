package de.beiri22.stringincrementor.helper;

/**
 * @author Rico
 */
public final class ExtBoolArray {

    public boolean isFalse(int a, int b) {
        for (int i = a; i <= b; i++) {
            if (werte[i]) {
                return false;
            }
        }
        return true;
    }
}
