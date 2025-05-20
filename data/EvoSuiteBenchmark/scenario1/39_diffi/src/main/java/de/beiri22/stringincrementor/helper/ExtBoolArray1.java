package de.beiri22.stringincrementor.helper;

/**
 * @author Rico
 */
public final class ExtBoolArray {

    public int left() {
        int result = 0;
        for (int i = 0; i < werte.length; i++) {
            if (!werte[i]) {
                result++;
            }
        }
        return result;
    }
}
