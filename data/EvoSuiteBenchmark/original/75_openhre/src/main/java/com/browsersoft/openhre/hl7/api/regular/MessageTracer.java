/*
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2,
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */

package com.browsersoft.openhre.hl7.api.regular;

import com.browsersoft.openhre.hl7.api.config.HL7Message;

/**
 * This class is used by <CODE>HL7Checker</CODE> in conversion process.
 * After each segment in record the checker call method <CODE>processNextSegment</CODE>
 * and that may cause one or more events into the <CODE>MessageTracerHandler</CODE>.
 * More events may be occured if you are using the additional tags and the process go out or into
 * the additional group.
 *
 */
public interface MessageTracer {

    /**
     * build the automat graph for <CODE>HL7Message</CODE>. For example:
     * for VXX message with the regural expression for segments order:
     * <pre>
     * MSH, MSA, QRD, [QRF], { PID [ {NK1} ] }
     * ---------------------------------------
     *  Forward
     *  0 --- END_POINT ---> 7
     *  3 --- PID       ---> 5
     *  5 --- END_POINT ---> 7

     *  Backward
     *  5 --- PID       ---> 5
     *  6 --- PID       ---> 5
     *  6 --- NK1       ---> 6
     * </pre>
     * see to the documentation of class <CODE>ExpressionMatrix</CODE>
     * @param message
     * @throws InvalidExpressionException
     */
    public void buildMatrixForMessage( HL7Message message ) throws InvalidExpressionException;

    /**
     * After each segment in record checker call this method
     * @param segmentID segmentID from parser event
     */
    public void processNextSegment( String segmentID );

    /**
     * after calling this function, MessageTracer goes to the END_POINT ( it may cause zero or more
     * events if you are using additional tags )
     */
    public void processEnd();

    public ExpressionMatrix getMatrix();
    public void setMatrix( ExpressionMatrix matrix );

    public ExpressionElementMapper getMapper();
    public void setMapper( ExpressionElementMapper mapper );

    public MessageTracerHandler getHandler();
    public void setHandler( MessageTracerHandler handler );





    public void reset();

}