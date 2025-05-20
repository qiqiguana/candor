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
 * Copyright 2000,2001 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;


/**
 * This class is an adapter for the Double type.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @see         SNumber
 */
final class SDouble extends SNumber {

    /**
     * The wrapped value
     */
    private final double _value;

    /**
     * Construct a new <code>SDouble</code>, initialised to <code>0</code>
     */
    public SDouble() {
        _value = 0;
    }

    /**
     * Construct a new <code>SDouble</code>
     *
     * @param value the underlying value
     */
    public SDouble(final double value) {
        _value = value;
    }

    /**
     * Returns the addition of a number to this
     *
     * @param number the number to add
     * @return the value of <code>this + number<code>
     */
    public SNumber add(final SNumber number) {
        return new SDouble(_value + number.getDouble());
    }

    /**
     * Returns the value of the substraction of a number from this
     *
     * @param number the number to subtract
     * @return the value of <code>this - number<code>
     */
    public SNumber subtract(final SNumber number) {
        return new SDouble(_value - number.getDouble());
    }

    /**
     * Returns the multiplication of a number to this
     *
     * @param number the number to multiply
     * @return the value of <code>this * number<code>
     */
    public SNumber multiply(final SNumber number) {
        return new SDouble(_value * number.getDouble());
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
            result = new SDouble(_value / number.getDouble());
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
        return (long) _value;
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
     * Returns the value of this, wrapped in a <code>Double</code>
     *
     * @return the value of this, wrapped in a <code>Double</code>
     */
    public Object getObject() {
        return new Double(_value);
    }

    /**
     * Determines if this is equal to another object.
     *
     * @param obj the object to compare. An instance of <code>SNumber</code>
     * @return <code>SBool.TRUE</code> if <code>this = obj</code>, otherwise
     * <code>SBool.FALSE</code>
     */
    public SBool equal(final SObject obj) {
        SBool result = SBool.FALSE;
        double rhs = ((SNumber) obj).getDouble();
        if (_value == rhs) {
            result = SBool.TRUE;
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
        SBool result = SBool.FALSE;
        double rhs = ((SNumber) obj).getDouble();
        if (_value < rhs) {
            result = SBool.TRUE;
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
        SBool result = SBool.FALSE;
        double rhs = ((SNumber) obj).getDouble();
        if (_value > rhs) {
            result = SBool.TRUE;
        }
        return result;
    }

} //-- SDouble
