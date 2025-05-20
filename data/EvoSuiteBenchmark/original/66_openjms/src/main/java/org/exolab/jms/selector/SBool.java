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
 * This class is an adapter for the Boolean type.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SObject
 */
class SBool extends SObject {

    /**
     * Boolean true
     */
    public static final SBool TRUE = new SBool(Boolean.TRUE);

    /**
     * Boolean false
     */
    public static final SBool FALSE = new SBool(Boolean.FALSE);

    /**
     * The wrapped value
     */
    private Boolean _value;


    /**
     * Construct a new <code>SBool</code>
     *
     * @param value the boolean value
     */
    public SBool(final Boolean value) {
        _value = value;
    }

    /**
     * Returns the value of this, wrapped in a <code>Boolean</code>
     *
     * @return the value of this, wrapped in a <code>Boolean</code>
     */
    public final Object getObject() {
        return _value;
    }

    /**
     * Returns the value of this
     *
     * @return the value of this
     */
    public final boolean value() {
        return _value.booleanValue();
    }

    /**
     * Evaluates <code>this AND rhs</code>
     *
     * @param rhs the right hand side of the expression. May be null
     * @return <code>SBool.TRUE</code> if <code>this</code> and
     * <code>rhs</code> are <code>true</code>, <code>SBool.FALSE</code>
     * if one is <code>false</code>, or <code>null</code> if <code>rhs</code>
     * is <code>null</code>
     */
    public final SBool and(final SBool rhs) {
        SBool result = null;
        if (rhs != null) {
            if (value() && rhs.value()) {
                result = SBool.TRUE;
            } else {
                result = SBool.FALSE;
            }
        } else if (!value()) {
            result = SBool.FALSE;
        }

        return result;
    }

    /**
     * Evaluates <code>this OR rhs</code>
     *
     * @param rhs the right hand side of the expression. May be null
     * @return <code>SBool.TRUE</code> if <code>this</code> is
     * <code>true</code>, otherwise <code>rhs</code>
     */
    public final SBool or(final SBool rhs) {
        SBool result = null;
        if (value()) {
            result = SBool.TRUE;
        } else {
            result = rhs;
        }
        return result;
    }

    /**
     * Evaluates <code>NOT this</code>
     *
     * @return <code>SBool.TRUE</code> if <code>this</code> is
     * <code>false</code>, otherwise <code>SBool.FALSE</code>
     */
    public final SBool not() {
        SBool result = SBool.TRUE;
        if (value()) {
            result = SBool.FALSE;
        }
        return result;
    }

    /**
     * Returns the type of this
     *
     * @return {@link Type#BOOLEAN}
     */
    public final Type type() {
        return Type.BOOLEAN;
    }

} //-- SBool
