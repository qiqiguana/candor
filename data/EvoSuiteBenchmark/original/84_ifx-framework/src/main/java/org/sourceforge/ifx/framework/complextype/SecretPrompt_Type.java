/*
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.framework.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class SecretPrompt_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SecretPrompt_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretId _secretId;

    /** 
     * Setter for secretId
     * @param secretId the org.sourceforge.ifx.framework.element.SecretId to set
     */
    public void setSecretId(org.sourceforge.ifx.framework.element.SecretId _secretId) {
        this._secretId = _secretId;
    }

    /**
     * Getter for secretId
     * @return a org.sourceforge.ifx.framework.element.SecretId
     */
    public org.sourceforge.ifx.framework.element.SecretId getSecretId() {
        return _secretId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Prompt _prompt;

    /** 
     * Setter for prompt
     * @param prompt the org.sourceforge.ifx.framework.element.Prompt to set
     */
    public void setPrompt(org.sourceforge.ifx.framework.element.Prompt _prompt) {
        this._prompt = _prompt;
    }

    /**
     * Getter for prompt
     * @return a org.sourceforge.ifx.framework.element.Prompt
     */
    public org.sourceforge.ifx.framework.element.Prompt getPrompt() {
        return _prompt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretOptional _secretOptional;

    /** 
     * Setter for secretOptional
     * @param secretOptional the org.sourceforge.ifx.framework.element.SecretOptional to set
     */
    public void setSecretOptional(org.sourceforge.ifx.framework.element.SecretOptional _secretOptional) {
        this._secretOptional = _secretOptional;
    }

    /**
     * Getter for secretOptional
     * @return a org.sourceforge.ifx.framework.element.SecretOptional
     */
    public org.sourceforge.ifx.framework.element.SecretOptional getSecretOptional() {
        return _secretOptional;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretFormat _secretFormat;

    /** 
     * Setter for secretFormat
     * @param secretFormat the org.sourceforge.ifx.framework.element.SecretFormat to set
     */
    public void setSecretFormat(org.sourceforge.ifx.framework.element.SecretFormat _secretFormat) {
        this._secretFormat = _secretFormat;
    }

    /**
     * Getter for secretFormat
     * @return a org.sourceforge.ifx.framework.element.SecretFormat
     */
    public org.sourceforge.ifx.framework.element.SecretFormat getSecretFormat() {
        return _secretFormat;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretMask _secretMask;

    /** 
     * Setter for secretMask
     * @param secretMask the org.sourceforge.ifx.framework.element.SecretMask to set
     */
    public void setSecretMask(org.sourceforge.ifx.framework.element.SecretMask _secretMask) {
        this._secretMask = _secretMask;
    }

    /**
     * Getter for secretMask
     * @return a org.sourceforge.ifx.framework.element.SecretMask
     */
    public org.sourceforge.ifx.framework.element.SecretMask getSecretMask() {
        return _secretMask;
    }


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * SecretId
       * Prompt
       * Memo
       * SecretOptional
       * SecretFormat
       * SecretMask
       */
    public final String[] ELEMENTS = {
              "SecretId"
                 ,"Prompt"
                 ,"Memo"
                 ,"SecretOptional"
                 ,"SecretFormat"
                 ,"SecretMask"
          };
}
