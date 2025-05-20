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
     * <p>Checks if the string represents valid InChI/standard InChI.</p>
     *
     * @param inchi source InChI
     * @param strict if <code>false</code>, just briefly check for proper layout (prefix, version, etc.) The result
     *               may not be strict.
     *               If <code>true</code>, try to perform InChI2InChI conversion and returns success if a resulting
     *               InChI string exactly match source. The result may be 'false alarm' due to imperfectness of
     */
    public static INCHI_STATUS checkInchi(final String inchi, final boolean strict) throws JniInchiException {
        if (inchi == null) {
            throw new IllegalArgumentException("Null InChI");
        }
        JniInchiWrapper wrapper = getWrapper();
        wrapper.getLock();
        try {
            int ret = wrapper.CheckINCHI(inchi, strict);
            INCHI_STATUS retStatus = INCHI_STATUS.getValue(ret);
            if (retStatus == null) {
                throw new JniInchiException("Unknown return status: " + ret);
            }
            return retStatus;
        } finally {
            lock.unlock();
        }
    }

    private static synchronized JniInchiWrapper getWrapper() throws LoadNativeLibraryException;

    private static synchronized void getLock() throws JniInchiException;

    private native int CheckINCHI(String inchi, boolean strict);
}
