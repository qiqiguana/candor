package net.virtualinfinity.atrobots.debugger;

import java.io.IOException;

/**
 * TODO: Describe this class.
 *
 * @author Daniel Pitts
 */
public interface Console {
    void println(Object o);

    String readline() throws IOException;

    void handleException(Exception e);
}
