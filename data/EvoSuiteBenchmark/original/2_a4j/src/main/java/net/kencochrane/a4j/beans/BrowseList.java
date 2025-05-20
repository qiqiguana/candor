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
 * Time: 1:21:38 PM
 *
 *
 */
public class BrowseList implements Serializable {
    ArrayList nodes;

    public BrowseNode[] getBrowseNode() {
        BrowseNode[] nodesArray = new BrowseNode[nodes.size()];
        return (BrowseNode[]) nodes.toArray(nodesArray);
    }

    public void setBrowseNode(BrowseNode[] browseNode) {
        nodes = new ArrayList(browseNode.length);
        for (int i = 0; i < browseNode.length; i++) {
            nodes.add(browseNode[i]);
        }
    }

    public ArrayList getBrowseNodeList() {
        return nodes;
    }

    public String toString() {
        StringBuffer output = new StringBuffer();
        BrowseNode node = new BrowseNode();

        if (getBrowseNodeList() != null && getBrowseNodeList().size() > 0) {
            output.append("# of nodes = " + getBrowseNodeList().size() + "\n");
            for (int x = 0; x < getBrowseNodeList().size(); x++) {
                node = (BrowseNode) getBrowseNodeList().get(x);
                if (node != null) {
                    output.append("Name: " + node.getBrowseName() + "\n");
                    output.append("ID: " + node.getBrowseId() + "\n");
                }
            }
        } else {
            output.append("No nodes" + "\n");
        }

        return output.toString();
    }
}
