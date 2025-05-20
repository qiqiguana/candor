/*
 *   CVS $Id: StreamHandler.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

import java.util.*;
import java.io.*;

public class StreamHandler extends Thread {
    InputStream is;
    String type;
    private static org.apache.log4j.Logger log =
		org.apache.log4j.Logger.getLogger(StreamHandler.class.getName());
    
    public StreamHandler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
    
    public void run() {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                if(type.equals("ERROR"))
                    log.error(line);   
                else if(type.equals("OUTPUT"))
                    log.info(line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}
