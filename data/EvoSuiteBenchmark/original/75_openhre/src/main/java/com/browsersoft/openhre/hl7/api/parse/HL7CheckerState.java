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


package com.browsersoft.openhre.hl7.api.parse;

import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7Field;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;
import com.browsersoft.openhre.hl7.api.regular.MessageTracer;


/**
 * This class represents state during the conversion in class <CODE>HL7Checker</CODE>.
 * It contains a lot of utility methods.
 */
public interface HL7CheckerState {

    public boolean isSeriousError();
    public void setSeriousError( boolean seriousError );

    public MessageTracer getMessageTracer();
    public void setMessageTracer( MessageTracer messageTracer );

    public void eventBeginSegment( String segmentID );
    public void eventBeginField( boolean repeatable );
    public void eventBeginComponent();
    public void eventBeginSubComponent();

    public void addToActualFieldLength( int length );

    public boolean isDataTypePrimitive( HL7DataType dataType );

    public void generateError( int code, String message );

    public void generateWarning( int code, String message );

    public HL7CheckerHandler getCheckerHandler();

    public void setCheckerHandler( HL7CheckerHandler handler );

    public HL7Segment getActualSegment();

    public void setActualSegment( HL7Segment actualSegment );

    public int getActualFieldPosition();

    public void setActualFieldPosition( int actualFieldPosition );

    public HL7Field getActualField();

    public void setActualField( HL7Field actualField );

    public int getActualFieldLength();

    public void setActualFieldLength( int actualFieldLength );

    public int getActualDataTypePartsPossition();

    public void setActualDataTypePartsPossition( int actualDataTypePartsPossition );

    public int getActualDataTypeSubPartsPossition();

    public void setActualDataTypeSubPartsPossition( int actualDataTypeSubPartsPossition );

    public int getActualFieldRepeatableIndex();

    public void setActualFieldRepeatableIndex( int actualFieldPositionRepeatableIndex );

    public HL7DataType getActualDataType();

    public void setActualDataType( HL7DataType actualDataType );

    public HL7Configuration getConfiguration();

    public void setConfiguration( HL7Configuration configuration );

    public HL7DataType getActualComponentDataType();

    public void setActualComponentDataType( HL7DataType actualComponentDataType );

    public HL7DataType getActualSubComponentDataType();

    public void setActualSubComponentDataType( HL7DataType actualSubComponentDataType );

}
