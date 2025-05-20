/*
 * SessionType.java
 *
 * Created on September 22, 2005, 8:50 PM
 *
 * Copyright (c) 2006, Francois Bradette
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of their contributors may be used to endorse or
 *       promote products derived from this software without specific prior
 *       written permission.
 *
 * This software is provided by the regents and contributors "as is" and any
 * express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are
 * disclaimed.  In no event shall the regents and contributors be liable for any
 * direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services;
 * loss of use, data, or profits; or business interruption) however caused and
 * on any theory of liability, whether in contract, strict liability, or tort
 * (including negligence or otherwise) arising in any way out of the use of this
 * software, even if advised of the possibility of such damage.
 *
 * Translate from eAthena(c) by Francois Bradette
 */


package org.javathena.core.data;

/**
 *
 * @author Francois
 */
public enum SessionType
{
    SESSION_UNKNOWN(-1),
    SESSION_RAW(0),
    SESSION_HTTP(1),
    //-----------------
    SESSION_MAX(2);

    private int value;
    
    public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	SessionType(int value)
    {
        this.value = value;
    }
}
