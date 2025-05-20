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
 * Time: 1:20:14 PM
 *
 *
 */
public class BrowseNode implements Serializable {
    private String browseId,browseName,mode;

    private ArrayList subNodes = new ArrayList();
    private ArrayList parentNodes = new ArrayList();

    public String getBrowseId() {
        return browseId;
    }

    public void setBrowseId(String browseId) {
        this.browseId = browseId;
    }

    public String getBrowseName() {
        return browseName;
    }

    public void setBrowseName(String browseName) {
        this.browseName = browseName;
    }

    public ArrayList getSubNodes() {
        return subNodes;
    }

    public void addSubNode(BrowseNode sNode) {
        subNodes.add(sNode);
    }

    public BrowseNode getSubNode(String browseId) {
        if (subNodes != null && subNodes.size() > 0) {
            BrowseNode node = new BrowseNode();
            for (int x = 0; x < subNodes.size(); x++) {
                node = (BrowseNode) subNodes.get(x);
                if (node.getBrowseId().trim().equalsIgnoreCase(browseId.trim())) {
                    return node;
                }
            }
        }
        return null; // if it gets this far it isn't in there.
    }

    public void printNode() {
        System.out.println(browseId + " - " + browseName);
        if (subNodes != null && subNodes.size() > 0) {
            System.out.println("  -- # of subNodes " + subNodes.size() + " -- \n");
            BrowseNode sNode = new BrowseNode();
            for (int x = 0; x < subNodes.size(); x++) {
                sNode = (BrowseNode) subNodes.get(x);
                System.out.println("    " + sNode.getBrowseId() + " - " + sNode.getBrowseName());
            }
        }
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setSubNodes(ArrayList array) {
        this.subNodes = array;
    }

    public ArrayList getParentNodes() {
        return parentNodes;
    }

    public void setParentNodes(ArrayList parentNodes) {
        this.parentNodes = parentNodes;
    }

    public String toString() {
        return this.browseId + " - " + this.browseName + " -- " + this.mode;
    }
}
