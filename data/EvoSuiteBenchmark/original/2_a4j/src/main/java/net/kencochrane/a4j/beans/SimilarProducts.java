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
public class SimilarProducts implements Serializable {

    ArrayList simProducts;

    public String[] getProduct() {
        String[] retString = new String[simProducts.size()];
        if (simProducts.size() > 0) simProducts.toArray(retString);

        return retString;
    }

    public void setProduct(String[] newString) {
        simProducts = new ArrayList(newString.length);
        for (int i = 0; i < newString.length; i++) {
            simProducts.add(newString[i]);
        }

    }

    public ArrayList getProductsArray() {
        return simProducts;
    }

    public String getProduct(int index) {
        String retString = null;

        if (simProducts.size() - 1 < index) {
            retString = (String) simProducts.get(index);
        }

        return retString;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        String string = new String();
        if (simProducts != null && simProducts.size() > 0) {
            output.append("# of Simular products = " + simProducts.size() + "\n");
            for (int x = 0; x < simProducts.size(); x++) {
                string = simProducts.get(x).toString();
                if (string != null) {
                    output.append("ProductAction - " + string + "\n");
                }
            }
        } else {
            output.append("Similar Products is null or size 0" + "\n");
        }
        return output.toString();
    }

}
