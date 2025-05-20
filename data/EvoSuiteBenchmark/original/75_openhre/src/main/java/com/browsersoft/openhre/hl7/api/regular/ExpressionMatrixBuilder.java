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

/**
 * This class is a expresion matrix builder. Please see into documentation of <CODE>ExpressionMatrix</CODE>.
 */
public interface ExpressionMatrixBuilder {

    /**
     * build the matrix for expression.
     * @param expression expression
     * @return matrix with relations
     * @throws InvalidExpressionException
     */
    public ExpressionMatrix buildMatrix( Expression expression ) throws InvalidExpressionException;

}