/*
 * Copyright 2006-2011 Sam Adams <sea36 at users.sourceforge.net>
 *
 * This file is part of JNI-InChI.
 *
 * JNI-InChI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JNI-InChI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JNI-InChI.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jniinchi;

/**
 * Encapsulates output from InChI generation.
 * @author Sam Adams
 */
public class JniInchiOutput {

    /**
     * InChI return status
     */
    private INCHI_RET retStatus;

    /**
     * InChI ASCIIZ string
     */
    private String sInchi;

    /**
     * Aux info ASCIIZ string
     */
    private String sAuxInfo;

    /**
     * Error/warning ASCIIZ message
     */
    private String sMessage;

    /**
     * log-file ASCIIZ string, contains a human-readable list of recognized
     * options and possibly an Error/warning message
     */
    private String sLog;

    
    public JniInchiOutput(int ret, String inchi, String auxInfo, String message, String log) {
    	this(INCHI_RET.getValue(ret), inchi, auxInfo, message, log);
    }
    
    public JniInchiOutput(INCHI_RET ret, String inchi, String auxInfo, String message, String log) {
    	this.retStatus = ret;
    	this.sInchi = inchi;
    	this.sAuxInfo = auxInfo;
    	this.sMessage = message;
    	this.sLog = log;
    }

    /**
     * Gets return status from InChI process.  OKAY and WARNING indicate
     * InChI has been generated, in all other cases InChI generation
     * has failed.
     */
    public INCHI_RET getReturnStatus() {
        return retStatus;
    }

    /**
     * Gets generated InChI string.
     */
    public String getInchi() {
        return sInchi;
    }

    /**
     * Gets generated InChI string.
     */
    public String getAuxInfo() {
        return sAuxInfo;
    }

    /**
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
        return sMessage;
    }

    /**
     * Gets generated log.
     */
    public String getLog() {
        return sLog;
    }

    protected void setLog(final String log) {
        this.sLog = log;
    }

    protected void setMessage(final String message) {
        this.sMessage = message;
    }

    protected void setRetStatus(final INCHI_RET retStatus) {
        this.retStatus = retStatus;
    }

    protected void setInchi(final String inchi) {
        this.sInchi = inchi;
    }

    protected void setAuxInfo(final String auxInfo) {
        this.sAuxInfo = auxInfo;
    }

    
    @Override
    public String toString() {
    	return "InChI_Output: " + retStatus + "/" + sInchi + "/" + sAuxInfo + "/" + sMessage + "/" + sLog;
    }
}
