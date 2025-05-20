/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: BaseLease.java,v 1.2 2005/03/18 03:47:30 tanderson Exp $
 */
package org.exolab.jms.lease;


/**
 * Generic lease implementation, which may be used to lease any object.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:47:30 $
 */
public class BaseLease
        implements LeaseIfc, Comparable {

    /**
     * This is the object that is leased.
     */
    protected final Object _leasedObject;

    /**
     * The duration of the lease in milliseconds.
     */
    protected long _duration = 0L;

    /**
     * This is the time that the lease will expire.
     */
    protected long _expiryTime = 0L;

    /**
     * The listener that will be notified when the lease expires.
     */
    protected LeaseEventListenerIfc _listener = null;


    /**
     * Construct a new <code>BaseLease</code>.
     *
     * @param object   the leased object
     * @param duration the duration of lease, in milliseconds
     * @param listener the listener to notify of lease events
     */
    public BaseLease(Object object, long duration,
                     LeaseEventListenerIfc listener) {
        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' is null");
        }

        if (listener == null) {
            throw new IllegalArgumentException("Argument 'listener' is null");
        }

        _leasedObject = object;
        _duration = duration;
        _expiryTime = System.currentTimeMillis() + duration;
        _listener = listener;
    }

    /**
     * Return the absolute expiry time of this lease.
     *
     * @return the expiry time of the lease, in milliseconds
     */
    public long getExpiryTime() {
        return _expiryTime;
    }

    /**
     * Returns the duration of the lease.
     *
     * @return the duration of the lease, in milliseconds
     */
    public long getDuration() {
        return _duration;
    }

    /**
     * Change the duration of the lease.
     *
     * @param duration the new lease duration, in milliseconds
     */
    public void setDuration(long duration) {
        _duration = duration;
        _expiryTime = System.currentTimeMillis() + duration;
    }

    /**
     * Returns the time remaining on the lease.
     *
     * @return the time remaining, in milliseconds
     */
    public long getRemainingTime() {
        return (System.currentTimeMillis() - _expiryTime);
    }

    /**
     * Returns the leased object.
     *
     * @return the leased object
     */
    public Object getLeasedObject() {
        return _leasedObject;
    }

    /**
     * Returns the listener to notify of lease events.
     *
     * @return the listener to notify of lease events
     */
    public LeaseEventListenerIfc getLeaseEventListener() {
        return _listener;
    }

    /**
     * Compares this object with the specified object. It returns a negative
     * integer is this object is less than the specified object; zero if this
     * object is equal to the specified object or a positive integer if this
     * object is greater than the specified object
     * <p/>
     * The comparison is based on the expiration time.
     */
    public int compareTo(Object object) {
        int result = 0;

        if (object instanceof BaseLease) {
            BaseLease lease = (BaseLease) object;
            if (lease.getExpiryTime() != this.getExpiryTime()) {
                if (lease.getExpiryTime() > this.getExpiryTime()) {
                    result = -1;
                } else {
                    result = 1;
                }
            }
        }
        return result;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(_leasedObject.toString());
        buf.append(" duration = ");
        buf.append(_duration);
        buf.append(" expiryTime = ");
        buf.append(_expiryTime);

        return buf.toString();
    }

    /**
     * Notify the listeners that this lease has expird. This method has package
     * level scope.
     */
    protected void notifyLeaseExpired() {
        synchronized (_listener) {
            _listener.onLeaseExpired(_leasedObject);
        }
    }

}
