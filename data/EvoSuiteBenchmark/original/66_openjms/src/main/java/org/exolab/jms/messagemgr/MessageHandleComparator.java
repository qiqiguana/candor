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
 */
package org.exolab.jms.messagemgr;


import java.util.Comparator;


/**
 * MessageHandleComparator is used to order messages on priority, acceptance
 * time and sequence number.
 *
 * @version     $Revision: 1.2 $ $Date: 2005/03/18 03:58:39 $
 * @author      <a href="mailto:jimm@intalio.com">Jim Mourikis</a>
 */
public class MessageHandleComparator
    implements Comparator {

    // implementation of Comparator.compare
    public int compare(Object o1, Object o2) {

        int result = -1;

        // The order is on priority-acceptance time-sequence number
        if ((o1 instanceof MessageHandle) &&
            (o2 instanceof MessageHandle)) {
            MessageHandle m1 = (MessageHandle) o1;
            MessageHandle m2 = (MessageHandle) o2;

            if (m1.getPriority() == m2.getPriority()) {
                if (m1.getAcceptedTime() == m2.getAcceptedTime()) {
                    if (m1.getSequenceNumber() == m2.getSequenceNumber()) {
                        result = 0;
                    } else if (m1.getSequenceNumber() > m2.getSequenceNumber()) {
                        result = 1;
                    } else {
                        result = -1;
                    }
                } else if (m1.getAcceptedTime() > m2.getAcceptedTime()) {
                    result = 1;
                } else {
                    result = -1;
                }
            } else if (m1.getPriority() > m2.getPriority()) {
                result = -1;
            } else {
                result = 1;
            }
        }

        return result;
    }

    // implementation of Comparator.equals
    public boolean equals(Object obj) {

        if (obj instanceof MessageHandleComparator) {
            return true;
        } else {
            return false;
        }
    }
}

