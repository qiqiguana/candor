package org.quickserver.net.server.impl;

import org.quickserver.net.server.*;
import org.quickserver.net.*;
import org.quickserver.util.*;
import org.quickserver.util.io.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import java.nio.*;
import java.nio.channels.*;
import javax.net.ssl.*;

public class NonBlockingClientHandler extends BasicClientHandler {

    /**
     * Returns the maximum count of thread allowed to run objects of this class at a time.
     *
     * @since 1.4.7
     */
    public static int getMaxThreadAccessCount();
}
