/******************************************************************************
 *
 * Copyright (c) 2001 by ISNetworks, Seattle, WA.
 *                       www.isnetworks.com, info@isnetworks.com
 * Based on MindTerm from Mindbright Technology AB, Stockholm, Sweden.
 *                        www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *****************************************************************************/


/**
* Simple exception to throw if something goes wrong when performing
* an operation.  Currently only used in response to some user-driven command
* in the SCP dialog
*/
package com.isnetworks.ssh;

public class SSHException extends Exception {

	public SSHException( String message ) {
		super( message );
	}
	
	public SSHException() {
		super();
	}

}