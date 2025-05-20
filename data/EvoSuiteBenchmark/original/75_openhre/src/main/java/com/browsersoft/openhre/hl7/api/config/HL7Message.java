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


package com.browsersoft.openhre.hl7.api.config;

import com.browsersoft.openhre.hl7.api.regular.MessageTracer;

/**
 * Class represents message and match following
 * structure in configuration file hl7_XXX_messages.xml
 * <pre>
 * &lt;message id="VXX"&gt;
 *   &lt;description&gt;
 *      Response to Vaccination Query Returning Multiple PID Matches
 *   &lt;/description&gt;
 *   &lt;group>
 *     &lt;segment id="MSH"/&gt;
 *     &lt;segment id="MSA"/&gt;
 *     &lt;segment id="QRD"/&gt;
 *     &lt;segment id="QRF" required="N"/&gt;
 *     &lt;group repeatable="Y"&gt;
 *        &lt;segment id="PID"/&gt;
 *        &lt;segment id="NK1" repeatable="Y" required="N"/&gt;
 *     &lt;/group&gt;
 *   &lt;/group&gt;
 * &lt;/message&gt;
 * </pre>
 */

public interface HL7Message {

	/**
	 * Returns the message id ( for example VXQ, VXR, ORU.... )
	 *
	 * @return message id
	 */
	public String getID();

	/**
	 * Sets he message id ( for example VXQ, VXR, ORU.... )
	 *
	 * @param id message id
	 */
	public void setID( String id );

	/**
	 * Returns the message description
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the message description
	 *
	 * @param description message description
	 */
	public void setDescription( String description );


	/**
	 * Returns group of segments
	 *
	 * @return group of segments
	 */
	public HL7MessageGroup getGroup();

	/**
	 * Sets group of segments
	 *
	 * @param group group of segments
	 */
	public void setGroup( HL7MessageGroup group );


    public MessageTracer getTracer();

    public void setTracer( MessageTracer tracer );


}
