/******************************************************************************
 *
 * Copyright (c) 1998,99 by Mindbright Technology AB, Stockholm, Sweden.
 *                 www.mindbright.se, info@mindbright.se
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
 *
 *****************************************************************************
 * $Author: webjabber $
 * $Date: 2006/06/13 18:43:42 $
 * $Name:  $
 *****************************************************************************/
package mindbright.ssh;

public interface SSHSCPIndicator {
    public void connected(String server);
    public void startFile(String file, long size);
    public void startDir(String file);
    public void endFile();
    public void endDir();
    public void progress(long size);
}
