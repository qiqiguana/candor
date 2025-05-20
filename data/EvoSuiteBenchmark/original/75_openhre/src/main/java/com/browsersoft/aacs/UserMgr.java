/*
 *   CVS $Id: UserMgr.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2, 
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */
package com.browsersoft.aacs;

//import gov.lanl.Web.User;
/**
 * Interface UserMgr
 * 
 */
public interface UserMgr {
  // Methods
  // Constructors
  // Accessor Methods
  // Operations
  /**
   * 
   * @param userid 
   * @param password 
   * @param authType 
   * @return boolean  
   */
	public boolean checkSecret(String userid, String password, String authType);
 
   /**
   * 
   * @return User  
   */
	public User getUser();
	
   /**
   * 
   * @return User  
   */
	public User getUser(String userid);
	
  /**
   * 
   * @return void  
   */
  public void logOut();
    
  
}

