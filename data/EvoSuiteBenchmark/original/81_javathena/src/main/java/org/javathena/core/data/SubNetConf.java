/*
 * SubNetConf.java
 *
 * Created on 2 juin 2006, 19:29
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
 * @author Darksid_1
 */
public class SubNetConf
{
    private long subnet;
    private long mask;
    private long char_ip;
    private long map_ip;
    /** Creates a new instance of SubNetConf */
    public SubNetConf()
    {
    }

    public long getSubnet()
    {
        return subnet;
    }

    public void setSubnet(long subnet)
    {
        this.subnet = subnet;
    }

    public long getMask()
    {
        return mask;
    }

    public void setMask(long mask)
    {
        this.mask = mask;
    }

    public long getChar_ip()
    {
        return char_ip;
    }

    public void setChar_ip(long char_ip)
    {
        this.char_ip = char_ip;
    }

    public long getMap_ip()
    {
        return map_ip;
    }

    public void setMap_ip(long map_ip)
    {
        this.map_ip = map_ip;
    }
    
}
