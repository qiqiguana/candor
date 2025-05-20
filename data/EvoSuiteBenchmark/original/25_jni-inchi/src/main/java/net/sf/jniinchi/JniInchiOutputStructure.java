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
 * Encapsulates output from InChI to structure conversion.
 * @author Sam Adams
 */
public class JniInchiOutputStructure extends JniInchiStructure {

    /**
     * Return status from conversion.
     */
    private INCHI_RET retStatus;

    /**
     * Error/warning messages generated.
     */
    private String message;

    /**
     * Log generated.
     */
    private String log;

    /**
     * <p>Warning flags, see INCHIDIFF in inchicmp.h.
     *
     * <p>[x][y]:
     * <br>x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
     * <br>x=1 => Disconnected layer if Reconnected layer is present
     * <br>y=1 => Main layer or Mobile-H
     * <br>y=0 => Fixed-H layer
     */
    private long[][] warningFlags = new long[2][2];

    
    public JniInchiOutputStructure(int ret, String message, String log, long w00, long w01, long w10, long w11) {
    	this(INCHI_RET.getValue(ret));
    	setMessage(message);
    	setLog(log);
    	setWarningFlags(w00, w01, w10, w11);
    }
    

    public JniInchiOutputStructure(INCHI_RET value) {
		this.retStatus = value;
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
     * Gets generated (error/warning) messages.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets generated log.
     */
    public String getLog() {
        return log;
    }

    /**
     * <p>Returns warning flags, see INCHIDIFF in inchicmp.h.
     *
     * <p>[x][y]:
     * <br>x=0 => Reconnected if present in InChI otherwise Disconnected/Normal
     * <br>x=1 => Disconnected layer if Reconnected layer is present
     * <br>y=1 => Main layer or Mobile-H
     * <br>y=0 => Fixed-H layer
     */
    public long[][] getWarningFlags() {
        return warningFlags;
    }

    protected void setLog(final String log) {
        this.log = log;
    }

    protected void setMessage(final String message) {
        this.message = message;
    }

    protected void setRetStatus(final INCHI_RET retStatus) {
        this.retStatus = retStatus;
    }

    protected void setWarningFlags(final long[][] warningFlags) {
        this.warningFlags = warningFlags;
    }
    
    protected void setWarningFlags(long f00, long f01, long f10, long f11) {
    	this.warningFlags = new long[][] {{f00, f01}, {f10, f11}};
    }


}
