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
public class Accessories implements Serializable {

    ArrayList accessory;

    public String[] getAccessory() {
        String[] retString = new String[accessory.size()];
        if (accessory.size() > 0) accessory.toArray(retString);

        return retString;
    }

    public void setAccessory(String[] newString) {
        accessory = new ArrayList(newString.length);
        for (int i = 0; i < newString.length; i++) {
            accessory.add(newString[i]);
        }

    }

    public ArrayList getAccessoryArray() {
        return accessory;
    }

    public String getAccessory(int index) {
        String retString = null;

        if (accessory.size() - 1 < index) {
            retString = (String) accessory.get(index);
        }

        return retString;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        String string = new String();
        if (accessory != null && accessory.size() > 0) {
            output.append("# of Accessories = " + accessory.size() + "\n");
            for (int x = 0; x < accessory.size(); x++) {
                string = accessory.get(x).toString();
                if (string != null) {
                    output.append("MiniProduct - " + string + "\n");
                }
            }
        } else {
            output.append("Accessories is null or size 0" + "\n");
        }
        return output.toString();
    }

}
