/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 16, 2003
 * Time: 11:06:53 AM
 *
 *
 */
public class Platforms implements Serializable {

    ArrayList platform;

    public String[] getPlatform() {
        String[] retString = new String[platform.size()];
        if (platform.size() > 0) platform.toArray(retString);

        return retString;
    }

    public void setPlatform(String[] newString) {
        platform = new ArrayList(newString.length);
        for (int i = 0; i < newString.length; i++) {
            platform.add(newString[i]);
        }

    }

    public ArrayList getPlatformsArray() {
        return platform;
    }

    public String getPlatform(int index) {
        String retString = null;

        if (platform.size() - 1 < index) {
            retString = (String) platform.get(index);
        }

        return retString;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        String string = new String();
        if (platform != null && platform.size() > 0) {
            output.append("# of Platforms = " + platform.size() + "\n");
            for (int x = 0; x < platform.size(); x++) {
                string = platform.get(x).toString();
                if (string != null) {
                    output.append("Platform - " + string + "\n");
                }
            }
        } else {
            output.append("Platforms is null or size 0" + "\n");
        }
        return output.toString();
    }

}
