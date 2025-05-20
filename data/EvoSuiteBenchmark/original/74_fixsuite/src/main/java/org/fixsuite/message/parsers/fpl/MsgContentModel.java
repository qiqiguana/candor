/**
 * MsgContentModel.java 12:34:27 AM May 1, 2008
 * 
 * <PRE>
 * Copyright (c) 2008, Jan Amoyo
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS';
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 * </PRE>
 */

package org.fixsuite.message.parsers.fpl;

/**
 * Model representing an element in a MsgContents.xml specification. This is
 * used for sorting misplaced contents from FIX 5.0 and FIX 5.0SP1.
 * 
 * @author jramoyo
 */
public class MsgContentModel implements Comparable<MsgContentModel> {

    private int indent;

    private double position;

    private String tagText;

    private boolean isRequired;

    private String description;

    private int id;

    /**
     * Returns the indent
     * 
     * @return the indent
     */
    public int getIndent() {
        return indent;
    }

    /**
     * Modifies the indent
     * 
     * @param indent - the indent to set
     */
    public void setIndent(int indent) {
        this.indent = indent;
    }

    /**
     * Returns the position
     * 
     * @return the position
     */
    public double getPosition() {
        return position;
    }

    /**
     * Modifies the position
     * 
     * @param position - the position to set
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * Returns the tagText
     * 
     * @return the tagText
     */
    public String getTagText() {
        return tagText;
    }

    /**
     * Modifies the tagText
     * 
     * @param tagText - the tagText to set
     */
    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    /**
     * Returns the isRequired
     * 
     * @return the isRequired
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Modifies the isRequired
     * 
     * @param isRequired - the isRequired to set
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Returns the description
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifies the description
     * 
     * @param description - the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the id
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Modifies the id
     * 
     * @param id - the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public int compareTo(MsgContentModel model) {
        if (id < model.getId()) {
            return -1;
        } else if (id == model.getId()) {

            if (position < model.getPosition()) {
                return -1;
            } else if (position == model.getPosition()) {
                return 0;
            } else {
                return 1;
            }

        } else {
            return 1;
        }
    }

}
