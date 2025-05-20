/* $Id: Packable.java,v 1.3 2004/04/27 19:26:22 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package state;

/**
 * Interface for all objects that are packable, i.e. objects that are
 * to be sent over the network.
 */
public interface Packable {

    /* Note: these must fall within the range [-128, 127] since they are
       sent over the network as on byte. */

    public static int UNDEFINED    = 0;
    public static int TEXT_MESSAGE = 1;
    public static int ACTION       = 2;
    public static int GAME_EVENT   = 3;
    public static int GAME_STATE   = 4;
    public static int PLAYER	   = 5;

    // public int type = UNDEFINED;
    
    /**
     * Returns an unambigious byte-array representation of this
     * object.
     *
     * @return The resulting byte array.
     */
    abstract public byte[] pack();

    /**
     * Unpacks an array previously created with pack() and puts in
     * this object.
     *
     * @param data The byte array to be unpacked.
     */
    abstract public void unpack(byte[] data);

    /**
     * Returns the type of the packable.
     *
     * @return The resulting byte array.
     */
    abstract public int type();
}
