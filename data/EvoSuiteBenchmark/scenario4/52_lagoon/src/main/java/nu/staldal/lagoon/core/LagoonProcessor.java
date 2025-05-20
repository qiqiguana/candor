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

    private static final boolean DEBUG = false;

    private final String targetURL;

    private final FileStorage targetLocation;

    private File repositoryDir;

    private File tempDir;

    private File classDir;

    private File sourceRootDir;

    private java.net.URL[] classLoaderURLs;

    private ClassLoader repositoryClassLoader;

    private final Hashtable classDict;

    private final Hashtable paramDict;

    private final Hashtable filestorageDict;

    private Sitemap sitemap;

    PrintWriter log;

    PrintWriter err;

    /**
     * Constructs and initializes a LagoonProcessor.
     *
     * @param targetURL  where to put the generated files,
     *                   must be an absolute URL or a local file path
     * @param sitemapTree  the Sitemap as an XTree
     * @param sourceDir  where the source files are
     * @param password  password to access the target storage, or
     *                  <code>null</code> if not nessesary.
     * @param log  where to write progress messages.
     * @param err  where to write error messages.
     */
    public LagoonProcessor(String targetURL, Element sitemapTree, File sourceDir, String password, PrintWriter log, PrintWriter err) throws IOException, LagoonException, AuthenticationException, AuthenticationMissingException {
    }

    /**
     * Get the Sitemap.
     *
     * @return the Sitemap.
     */
    Sitemap getSitemap();

    /**
     * Get the target location.
     *
     * @return the target location.
     */
    FileStorage getTargetLocation();

    /**
     * Perform the building of the website.
     * May be invoked multiple times.
     * Synchronous, returns when the building is complete.
     *
     * @param force force a rebuild of all files, otherwise dependency
     *        checking is used to check which files that needs rebuilding.
     *
     * @return true if successful, false if any non-fatal error occured
     * @throws IOException  if any fatal error occur
     */
    public boolean build(boolean force) throws IOException;

    /**
     * Dispose this object and release any resources it holds.
     * This causes the FileStorage to be closed.
     */
    public void destroy() throws IOException;

    public File getTempDir();

    public InputStream readFileFromRepository(String key);

    InputStream readFileFromRepository(String dir, String key);

    public OutputStream storeFileInRepository(String key) throws IOException;

    OutputStream storeFileInRepository(String dir, String key) throws IOException;

    public Class loadClassFromRepository(String className) throws ClassNotFoundException;

    public OutputStream storeClassInRepository(String className) throws IOException;

    public void deleteClassInRepository(String className) throws IOException;

    public void reloadClasses();

    public Object getObjectFromRepository(String key) throws IOException;

    Object getObjectFromRepository(String dir, String key) throws IOException;

    public boolean putObjectIntoRepository(String key, Object obj) throws IOException;

    boolean putObjectIntoRepository(String dir, String key, Object obj) throws IOException;

    /**
     * Create a new producer.
     *
     * @param cat  the producer category (format, transform, source,
     *             read, parse or process).
     * @param type the producer type, use "" for default.
     *
     * @return  a new Producer
     *          or <code>null</code> if it cannot be found.
     */
    Producer createProducer(String cat, String type) throws LagoonException;

    /**
     * Create a new file storage
     *
     * @param url  the URL
     *
     * @return  a new FileStorage
     *          or <code>null</code> if it cannot be found.
     */
    FileStorage createFileStorage(String url) throws LagoonException;

    public boolean canCheckFileHasBeenUpdated(String url);

    public File getSourceRootDir();

    public String getFileURLRelativeTo(String url, String base);

    public String getProperty(String key);
}
