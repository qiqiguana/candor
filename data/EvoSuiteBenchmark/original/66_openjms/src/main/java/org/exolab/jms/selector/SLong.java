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
 * Copyright 2000-2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;


/**
 * This class is an adapter for the Long type.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SNumber
 */
final class SLong extends SNumber {

    /**
     * The wrapped value
     */
    private long _value;

    /**
     * Construct a new <code>SLong</code>
     *
     * @param value the underlying value
     */
    public SLong(final long value) {
        _value = value;
    }

    /**
     * Returns the addition of a number to this
     *
     * @param number the number to add
     * @return the value of <code>this + number<code>
     */
    public SNumber add(final SNumber number) {
        SNumber result;
        if (number instanceof SDouble) {
            result = promote().add(number);
        } else {
            result = new SLong(_value + number.getLong());
        }
        return result;
    }

    /**
     * Returns the value of the substraction of a number from this
     *
     * @param number the number to subtract
     * @return the value of <code>this - number<code>
     */
    public SNumber subtract(final SNumber number) {
        SNumber result;
        if (number instanceof SDouble) {
            result = promote().subtract(number);
        } else {
            result = new SLong(_value - number.getLong());
        }
        return result;
    }

    /**
     * Returns the multiplication of a number to this
     *
     * @param number the number to multiply
     * @return the value of <code>this * number<code>
     */
    public SNumber multiply(final SNumber number) {
        SNumber result;
        if (number instanceof SDouble) {
            result = promote().multiply(number);
        } else {
            result = new SLong(_value * number.getLong());
        }
        return result;
    }

    /**
     * Returns the division of a number from this
     *
     * @param number the number to divide
     * @return the value of <code>this / number<code>
     */
    public SNumber divide(final SNumber number) {
        SNumber result = null;
        try {
            if (number instanceof SDouble) {
                result = promote().divide(number);
            } else {
                result = new SLong(_value / number.getLong());
            }
        } catch (ArithmeticException ignore) {
        }
        return result;
    }

    /**
     * Returns the value of this as a <code>long</code>
     *
     * @return the value of this as a <code>long</code>
     */
    public long getLong() {
        return _value;
    }

    /**
     * Returns the value of this as a <code>double</code>
     *
     * @return the value of this as a <code>double</code>
     */
    public double getDouble() {
        return _value;
    }

    /**
     * Returns the value of this, wrapped in a <code>Long</code>
     *
     * @return the value of this, wrapped in a <code>Long</code>
     */
    public Object getObject() {
        return new Long(_value);
    }

    /**
     * Determines if this is equal to another object.
     *
     * @param obj the object to compare. An instance of <code>SNumber</code>
     * @return <code>SBool.TRUE</code> if <code>this = obj</code>, otherwise
     * <code>SBool.FALSE</code>
     */
    public SBool equal(final SObject obj) {
        SBool result = null;
        if (obj instanceof SLong) {
            long rhs = ((SNumber) obj).getLong();
            if (_value == rhs) {
                result = SBool.TRUE;
            } else {
                result = SBool.FALSE;
            }
        } else if (obj instanceof SDouble) {
            result = promote().equal(obj);
        }
        return result;
    }

    /**
     * Determines if this is less than another object.
     *
     * @param obj the object to compare. An instance of <code>SNumber</code>
     * @return <code>SBool.TRUE</code> if <code>this &lt; obj</code>, otherwise
     * <code>SBool.FALSE</code>
     */
    public SBool less(final SObject obj) {
        SBool result = null;
        if (obj instanceof SLong) {
            long rhs = ((SNumber) obj).getLong();
            if (_value < rhs) {
                result = SBool.TRUE;
            } else {
                result = SBool.FALSE;
            }
        } else if (obj instanceof SDouble) {
            result = promote().less(obj);
        }
        return result;
    }

    /**
     * Determines if this is greater than another object.
     *
     * @param obj the object to compare. An instance of <code>SNumber</code>
     * @return <code>SBool.TRUE</code> if <code>this &gt; obj</code>, otherwise
     * <code>SBool.FALSE</code>
     */
    public SBool greater(final SObject obj) {
        SBool result = null;
        if (obj instanceof SLong) {
            long rhs = ((SNumber) obj).getLong();
            if (_value > rhs) {
                result = SBool.TRUE;
            } else {
                result = SBool.FALSE;
            }
        } else if (obj instanceof SDouble) {
            result = promote().greater(obj);
        }
        return result;
    }

    /**
     * Promotes this to a double
     *
     * @return the value of this as a double
     */
    private SDouble promote() {
        return new SDouble(_value);
    }

} //-- SLong
