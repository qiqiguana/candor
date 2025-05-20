/* $Id: NetworkData.java,v 1.2 2004/03/31 21:12:03 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.2 $
 *
 */

package state;

/**
 * Wrapper for network messages (classes inplementing Packable) used
 * by the ServerNetworkModule. Provides a way of setting recipients
 * for a network message. 
 */

public class NetworkData {

    /**
     * The id:s of the recipients. If set to <i>null</i>, data is
     * considered to be a general message, i.e. all clients will
     * receive it.
     */
    int[] recipients;

    /** The network message. */
    Packable data;

    /**
     * Creates a new NetworkData instance. When this constructor is used,
     * an empty general message is created (i.e. both recipients and data
     * is set to null.)
     *
     */
    public NetworkData() {
	this(null, null);
    }

    /**
     * Creates a new NetworkData instance. When this constructor is
     * used, a general message is created (that is, recipients is set
     * to null.)
     *
     * @param data the network message.
     */
    public NetworkData(Packable data) {
	this(null, data);
    }

    /**
     * Creates a new NetworkData instance.
     *
     * @param recipients the id:s of the recipients.
     * @param data the network message.
     */
    public NetworkData(int[] recipients, Packable data) {
	this.recipients = recipients;
	this.data = data;
    }

    /**
     * Sets the data to the specified object.
     * @param data the data to set.
     */
    public void setData(Packable data) {
	this.data = data;
    }

    /**
     * Returns the data.
     * @return the data.
     */
    public Packable getData() {
	return data;
    }

    public int[] getRecipients() {
	return recipients;
    }

    public String toString() {
	String recs = "all";
	if (recipients != null) {
	    recs = "";
	    for (int r : recipients) {
		recs = recs + r;
	    }
	}
	return "NetworkData[Recipients[" +recs+ "]," +data+ "]";
    }
}
