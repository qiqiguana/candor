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
 * Copyright 1999 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: LeaseComparator.java,v 1.1 2004/11/26 01:50:42 tanderson Exp $
 *
 * Date         Author  Changes
 * 3/01/2000    jima    Created
 */
package org.exolab.jms.lease;

import java.util.Comparator;


/**
 * The LeaseComparator is usedto sort leases based on the expiration
 * time
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:42 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 **/
public class LeaseComparator
    implements Comparator {

    /**
     * Compare two objects and return 0 if they are equal, a negative integer
     * if the first argument is less than the second argument and a positive
     * number if the first argument is greater than the second argument
     * <p>
     * It will throw ClassCastException if either obj1 or obj2 are not of type
     * BaseLease
     *
     * @param       obj1            first object to compare
     * @param       obj2            second object to compare
     * @return      int
     */
    public int compare(Object obj1, Object obj2) {
        int result = 0;

        if ((obj1 instanceof BaseLease) &&
            (obj2 instanceof BaseLease)) {
            BaseLease lease1 = (BaseLease) obj1;
            BaseLease lease2 = (BaseLease) obj2;

            if (lease1.getExpiryTime() != lease2.getExpiryTime()) {
                if (lease1.getExpiryTime() < lease2.getExpiryTime()) {
                    result = -1;
                } else {
                    result = 1;
                }
            }
        } else {
            throw new ClassCastException("obj1 is of type " +
                obj1.getClass().getName() + " obj2 is of type " +
                obj2.getClass().getName());
        }

        return result;
    }

    /**
     * Return true if the specified comparator matches this object and
     * false otherwise
     *
     * @param           comparator      comparator to test
     * @return          boolean         true if they are the same
     */
    public boolean equals(Object comparator) {
        return comparator instanceof LeaseComparator;
    }
}


