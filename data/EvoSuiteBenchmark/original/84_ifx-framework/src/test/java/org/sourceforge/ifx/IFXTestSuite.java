/*
 * $Id: IFXTestSuite.java,v 1.1 2004/02/26 17:49:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/test/org/sourceforge/ifx/IFXTestSuite.java,v $
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
package org.sourceforge.ifx;

import junit.framework.*;

/**
 * Main calling test suite. More tests can be added to this suite as
 * time passes and we discover more bugs to regress.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXTestSuite extends TestCase {

    public static void main(String[] argv) {
        junit.textui.TestRunner.run(suite());
    }

    public IFXTestSuite(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(EncodingDecodingTest.suite());
        return suite;
    }
}

