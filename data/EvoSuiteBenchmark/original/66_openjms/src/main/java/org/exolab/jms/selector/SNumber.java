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
 * This class is the base class for numeric type adapters.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SObject
 */
abstract class SNumber extends SObject {

    /**
     * Returns the addition of a number to this
     *
     * @param number the number to add
     * @return the value of <code>this + number<code>
     */
    public abstract SNumber add(final SNumber number);

    /**
     * Returns the value of the substraction of a number from this
     *
     * @param number the number to subtract
     * @return the value of <code>this - number<code>
     */
    public abstract SNumber subtract(final SNumber number);

    /**
     * Returns the multiplication of a number to this
     *
     * @param number the number to multiply
     * @return the value of <code>this * number<code>
     */
    public abstract SNumber multiply(final SNumber number);

    /**
     * Returns the division of a number from this
     *
     * @param number the number to divide
     * @return the value of <code>this / number<code>
     */
    public abstract SNumber divide(final SNumber number);

    /**
     * Returns the value of this as a <code>long</code>
     *
     * @return the value of this as a <code>long</code>
     */
    public abstract long getLong();

    /**
     * Returns the value of this as a <code>double</code>
     *
     * @return the value of this as a <code>double</code>
     */
    public abstract double getDouble();

    /**
     * Returns the type of this
     *
     * @return {@link Type#NUMERIC}
     */
    public final Type type() {
        return Type.NUMERIC;
    }

} //-- SNumber
