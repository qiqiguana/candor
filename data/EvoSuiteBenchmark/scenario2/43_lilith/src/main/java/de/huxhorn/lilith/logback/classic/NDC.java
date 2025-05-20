package de.huxhorn.lilith.logback.classic;

import de.huxhorn.lilith.data.logging.Message;

public class NDC {

    /**
     * Returns an array containing all messages of the stack.
     * <p/>
     * The messages from the NDC stack should not be used in application logic.
     *
     * @return an array containing all messages of the stack.
     */
    public static Message[] getContextStack() {
        return ndcAdapter.getContextStack();
    }
}
