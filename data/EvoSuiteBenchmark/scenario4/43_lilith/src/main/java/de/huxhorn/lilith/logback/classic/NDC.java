package de.huxhorn.lilith.logback.classic;

import de.huxhorn.lilith.data.logging.Message;

public class NDC {

    private static final NDCAdapter ndcAdapter;

    static {
    }

    private NDC() {
    }

    public static void push(String message);

    public static void push(String messagePattern, Object[] arguments);

    /**
     * Pops the last message from the stack.
     * <p/>
     * This method does not return the popped message to discourage it's usage in application logic.
     */
    public static void pop();

    public static int getDepth();

    public static void setMaximumDepth(int maximumDepth);

    public static boolean isEmpty();

    public static void clear();

    /**
     * Returns an array containing all messages of the stack.
     * <p/>
     * The messages from the NDC stack should not be used in application logic.
     *
     * @return an array containing all messages of the stack.
     */
    public static Message[] getContextStack();
}
