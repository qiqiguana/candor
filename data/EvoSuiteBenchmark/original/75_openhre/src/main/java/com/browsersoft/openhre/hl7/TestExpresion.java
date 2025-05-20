
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

package com.browsersoft.openhre.hl7;

import com.browsersoft.openhre.hl7.api.regular.Expression;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrixBuilder;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrix;
import com.browsersoft.openhre.hl7.impl.regular.ExpressionImpl;
import com.browsersoft.openhre.hl7.impl.regular.ExpressionMatrixBuilderImpl;
import com.browsersoft.openhre.hl7.api.regular.InvalidExpressionException;

public class TestExpresion {

    public static void main( String[] args ) {
        try {
            Expression expression = new ExpressionImpl();
            //String exp = "[A[B]C{D}{E[F]}]G";
            //String exp = "[ABC[D][EFG]HIJK]L";
            String exp = "A[B]{[C]D}E";
            //String exp = "[A][[[B]C][D]E]F";
            System.out.println( "------------------------------");
            System.out.println( "exp:" + exp);
            System.out.println( "------------------------------");
            expression.readFromStringForDebug(exp);
            //System.out.println(expression.toString());
            ExpressionMatrixBuilder builder = new ExpressionMatrixBuilderImpl();
            ExpressionMatrix matrix = builder.buildMatrix(expression);
            System.out.println("Non standard forward:");
            System.out.println(matrix.outNoStandardConnections(true, null));
            System.out.println("Non standard backward:");
            System.out.println(matrix.outNoStandardConnections(false, null));
        } catch ( InvalidExpressionException invalidExpressionException ) {
            invalidExpressionException.printStackTrace();
        }
    }

}
