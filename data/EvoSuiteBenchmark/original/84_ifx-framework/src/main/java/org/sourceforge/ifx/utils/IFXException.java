/*
 * $Id: IFXException.java,v 1.1 2004/02/23 03:36:46 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/utils/IFXException.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.utils;

import java.text.MessageFormat;

/**
 * Thin wrapper over real Exceptions thrown by the framework. Allows throwing
 * application level exceptions which may not be caught by the JVM because 
 * they are not JVM exceptions.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXException extends Exception {

    /**
     * Creates an IFXException object using the error message.
     * @param message the Exception message.
     */
    public IFXException(String message) {
        super(message);
    }

    /**
     * Creates an IFXException object using the error message and
     * replaceable positional parameters.
     * @param message the Exception message with positional parameters.
     * @param args an array of positional parameter String values.
     */
    public IFXException(String message, String[] args) {
        super(MessageFormat.format(message, args));
    }

    /**
     * Creates an IFXException object using the error message and a 
     * chained exception.
     * @param message the Exception message.
     * @param e the underlying chained exception.
     */
    public IFXException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Creates an IFXException object using the error message, replaceable
     * positional parameters and a chained exception.
     * @param message the Exception message with positional parameters.
     * @param args an array of positional parameter String values.
     * @param e the underlying chained exception.
     */
    public IFXException(String message, String[] args, Exception e) {
        super(MessageFormat.format(message, args), e);
    }
}
