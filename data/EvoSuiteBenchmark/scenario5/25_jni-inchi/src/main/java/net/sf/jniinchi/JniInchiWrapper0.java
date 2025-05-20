package net.sf.jniinchi;

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.deploy.NativeLibraryLoader;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>JNI Wrapper for International Chemical Identifier (InChI) C++ library.
 *
 * <p>This class is not intended to be used directly, but should be accessed
 * through subclasses that read data formats and load them into the InChI
 * data structures.
 *
 * <p>Subclasses should load data through the addAtom, addBond and addParity
 * methods. Once the molecule is fully loaded then the generateInchi method
 * should be called. Ideally this should all take place within the subclass's
 * constructor. The public get methods will all return null until this has
 * happened.
 *
 * <p>See <tt>inchi_api.h</tt>.
 *
 * @author Sam Adams
 */
public class JniInchiWrapper {

    /**
     * Calculates the InChIKey for an InChI string.
     *
     * @param inchi source InChI string
     * @return InChIKey output
     * @throws JniInchiException
     */
    public static JniInchiOutputKey getInchiKey(final String inchi) throws JniInchiException {
        if (inchi == null) {
            throw new IllegalArgumentException("Null InChI");
        }
        JniInchiWrapper wrapper = getWrapper();
        wrapper.getLock();
        try {
            return wrapper.GetINCHIKeyFromINCHI(inchi);
        } finally {
            lock.unlock();
        }
    }

    private static synchronized JniInchiWrapper getWrapper() throws LoadNativeLibraryException;

    private static synchronized void getLock() throws JniInchiException;

    private native JniInchiOutputKey GetINCHIKeyFromINCHI(String inchi);
}
