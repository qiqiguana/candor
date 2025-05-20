package nu.staldal.lagoon.core;

import java.io.*;
import java.util.*;
import nu.staldal.xtree.*;
import nu.staldal.util.Utils;

/**
 * The main worker class of the Lagoon core.
 *
 * Initialized with the sitemap,
 * a source dir and a target storage URL.
 * Then building the website may be done several times,
 * until destroy() is invoked.
 *
 * This class is not thread-safe. The methods must not
 * be invoked concurrently from different threads.
 */
public class LagoonProcessor implements LagoonContext {

    /**
     * Perform the building of the website.
     * May be invoked multiple times.
     * Synchronous, returns when the building is complete.
     *
     * @param force force a rebuild of all files, otherwise dependency
     *        checking is used to check which files that needs rebuilding.
     * @return true if successful, false if any non-fatal error occured
     * @throws IOException if any fatal error occur
     */
    public boolean build(boolean force) throws IOException;
}
