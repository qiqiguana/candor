package de.huxhorn.lilith.logback.classic;

import de.huxhorn.lilith.data.logging.Message;

public class NDC {

    public static Message[] getContextStack() {
        return ndcAdapter.getContextStack();
    }
}
