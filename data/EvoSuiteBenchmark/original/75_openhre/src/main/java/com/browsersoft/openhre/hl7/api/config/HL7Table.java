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

/**
 * Class represents one table and match following
 * structure in configuration file hl7_XXX_tables.xml
 * <pre>
 * &lt;table id="0001"&gt;
 *   &lt;description&gt;
 *     Sex [values suggested by HL7] (use in PID-8, NK1-15)
 *   &lt;/description&gt;
 *   &lt;items&gt;
 *      &lt;item value="F" description="Female"/&gt;
 *      &lt;item value="M" description="Male"/&gt;
 *      &lt;item value="O" description="Other"/&gt;
 *      &lt;item value="U" description="Unknown"/&gt;
 *   &lt;/items&gt;
 * &lt;/table&gt;
 * </pre>;
 */

public interface HL7Table {

    /**
     * Returns the table id ( for example 0001, 0002 .... )
     *
     * @return table id
     */
    public String getID();

    /**
     * Sets the table id ( for example 0001, 0002 .... )
     *
     * @param id table id
     */
    public void setID( String id );

    /**
     * Returns the table description
     *
     * @return description
     */
    public String getDescription();

    /**
     * Sets the table description
     *
     * @param description table description
     */
    public void setDescription( String description );

    /**
     * Returns list of items
     *
     * @return list of items
     */
    public HL7TableItemMap getItems();

    /**
     * Sets list of items
     *
     * @param items list of items
     */
    public void setItems( HL7TableItemMap items );

}
