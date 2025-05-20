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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: TimedCondition.java,v 1.1 2005/08/30 06:23:11 tanderson Exp $
 */
package org.exolab.jms.messagemgr;


/**
 * A timed {@link Condition}.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 06:23:11 $
 */
public class TimedCondition implements Condition {

    /**
     * The absolute time, in millseconds.
     */
    private final long _time;

    /**
     * Determines if the condition evaluates <code>true</code> while before or
     * after <code>time</code>.
     */
    private final boolean _before;


    /**
     * Construct a new <code>TimedCondition</code>.
     *
     * @param time   the absolute time, in milliseconds
     * @param before determines if the condition evaluates <code>true</code>
     *               while before or after <code>time</code>.
     */
    private TimedCondition(long time, boolean before) {
        _time = time;
        _before = before;
    }

    /**
     * Evaluates the condition.
     *
     * @return the value of the condition
     */
    public boolean get() {
        final long current = System.currentTimeMillis();
        if (_before) {
            return current < _time;
        }
        return current >= _time;
    }

    /**
     * Create a new <code>TimedCondition</code> that evaluates <code>true</code>
     * while the current time &lt; Now + time
     *
     * @param time the relative time, in milliseconds
     * @return a condition that evaluates <code>true</code> while the current
     * time &lt; Now + time
     */
    public static Condition before(long time) {
        return new TimedCondition(getAbsTime(time), true);
    }

    /**
     * Create a new <code>TimedCondition</code> that evaluates <code>true</code>
     * while the current time &gt;= Now + time
     *
     * @param time the relative time, in milliseconds
     * @return a condition that evaluates <code>true</code> while the current
     * time &gt;= Now + time
     */
    public static Condition after(long time) {
        return new TimedCondition(getAbsTime(time), false);
    }

    /**
     * Helper to return an absolute time in milliseconds, relative to now.
     *
     * @param time the relative time in milliseconds
     * @return the absolute time
     */
    private static long getAbsTime(long time) {
        return System.currentTimeMillis() + time;
    }
}
