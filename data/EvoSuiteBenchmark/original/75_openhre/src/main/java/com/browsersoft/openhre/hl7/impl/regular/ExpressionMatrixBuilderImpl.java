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

package com.browsersoft.openhre.hl7.impl.regular;

import com.browsersoft.openhre.hl7.api.regular.Expression;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrix;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrixBuilder;
import com.browsersoft.openhre.hl7.api.regular.ExpressionPart;
import com.browsersoft.openhre.hl7.api.regular.InvalidExpressionException;

public class ExpressionMatrixBuilderImpl implements ExpressionMatrixBuilder {

    private static final int GROUP_TYPE_NORMAL = 0;
    private static final int GROUP_TYPE_OPTIONAL = 1;

    public ExpressionMatrix buildMatrix( Expression expression ) throws InvalidExpressionException {

        ExpressionMatrix matrix = new ExpressionMatrixImpl();
        int numberOfElements = expression.getNumberOfElementTypes();

        matrix.creatMatrix(numberOfElements);

        buildProcess(matrix, expression);

        return matrix;

    }

    private void buildProcess( ExpressionMatrix matrix, Expression expression ) throws InvalidExpressionException {
        //first node
        matrix.addNewNode();

        processSubExpression(matrix, expression, 0, expression.size() - 1, GROUP_TYPE_NORMAL);

    }

    private ExpressionNodeList processSubExpression( ExpressionMatrix matrix, Expression expression, int start, int end, int typeOfGroup ) throws InvalidExpressionException {

        NodeStack repeatableStack = new NodeStack();

        ExpressionNodeList terminalNodes = new ExpressionNodeList();
        int startNode = getActualIndexOfNode(expression, start, end);
        terminalNodes.addItem(startNode);

        int realStart = start;
        if ( typeOfGroup != GROUP_TYPE_NORMAL ) {
            realStart++;
        }

        for ( int i = realStart; i <= end; i++ ) {

            ExpressionPart part = expression.getItem(i);
            switch ( part.getType() ) {

                case ExpressionPart.OPEN_REPEATABLE: {
                    repeatableStack.push( getActualIndexOfNode(expression, i, end) );
                    break;
                }
                case ExpressionPart.OPEN_OPTIONAL: {

                    int endOfGroup = getEndOfGroup(expression, i);
                    ExpressionNodeList terminalsFromGroup = processSubExpression(matrix, expression, i, endOfGroup, GROUP_TYPE_OPTIONAL);
                    int startNodeOfGroup = getActualIndexOfNode(expression, i, endOfGroup);

                    ConnectionList connections = getAllConnectionsFromNode(matrix, startNodeOfGroup);

                    for ( int j = 0; j < terminalNodes.size(); j++ ) {

                        int terminalItem = terminalNodes.getItem(j);
                        if ( terminalItem != startNodeOfGroup ) {

                            for ( int k = 0; k < connections.size(); k++ ) {
                                Connection connection = connections.getItem(k);
                                matrix.setValue(terminalItem, connection.getElementID(), connection.getTo());
                            }

                        }

                    }

                    terminalNodes.addExpressionList(terminalsFromGroup);

                    i = endOfGroup;

                    break;
                }
                case ExpressionPart.ELEMENT: {
                    matrix.addNewNode();
                    int indexOfNodeAfterElement = part.getElementID() + 1;
                    for ( int j = 0; j < terminalNodes.size(); j++ ) {
                        matrix.setValue(terminalNodes.getItem(j), part.getElementID(), indexOfNodeAfterElement);
                    }
                    terminalNodes.clearAll();
                    terminalNodes.addItem(indexOfNodeAfterElement);
                    break;
                }
                case ExpressionPart.CLOSE_REPEATABLE: {

                    int repeatableBeginNode = repeatableStack.pop();

                    ConnectionList connections = getAllConnectionsFromNode(matrix, repeatableBeginNode);

                    for ( int j = 0; j < terminalNodes.size(); j++ ) {

                        int terminalItem = terminalNodes.getItem(j);
                        if ( terminalItem > repeatableBeginNode ) {

                            for ( int k = 0; k < connections.size(); k++ ) {
                                Connection connection = connections.getItem(k);
                                matrix.setValue(terminalItem, connection.getElementID(), connection.getTo());
                            }

                        }

                    }

                    break;
                }

            }
        }

        if ( typeOfGroup == GROUP_TYPE_OPTIONAL ) {
            if ( !terminalNodes.isInList(startNode) ) {
                terminalNodes.addItem(startNode);
            }
        }

        return terminalNodes;

    }


    private ConnectionList getAllConnectionsFromNode( ExpressionMatrix matrix, int node ) {

        ConnectionList list = new ConnectionList();

        for ( int j = 0; j < matrix.getNumberOfElements(); j++ ) {
            int connection = matrix.getValue(node, j);
            if ( connection != (-1) ) {
                Connection connectionObject = new Connection();
                connectionObject.setElementID(j);
                connectionObject.setTo(connection);
                connectionObject.setFrom(node);
                list.addItem(connectionObject);
            }
        }

        return list;

    }

    private int getActualIndexOfNode( Expression expression, int index, int max ) throws InvalidExpressionException {
        for ( int j = index; j <= max; j++ ) {
            ExpressionPart p = expression.getItem(j);
            if ( p.getType() == ExpressionPart.ELEMENT ) {
                return p.getElementID();
            }
        }
        throw new InvalidExpressionException();
    }

    private int getEndOfGroup( Expression expression, int start ) throws InvalidExpressionException {

        ExpressionPart partStart = expression.getItem(start);
        int openType = partStart.getType();
        int closeType = -1;

        if ( openType == ExpressionPart.OPEN_OPTIONAL ) {
            closeType = ExpressionPart.CLOSE_OPTIONAL;
        }
        if ( openType == ExpressionPart.OPEN_REPEATABLE ) {
            closeType = ExpressionPart.CLOSE_REPEATABLE;
        }

        int level = 0;
        for ( int i = start; i < expression.size(); i++ ) {

            ExpressionPart part = expression.getItem(i);
            if ( part.getType() == openType ) {
                level++;
            } else if ( part.getType() == closeType ) {
                level--;
                if ( level == 0 ) {
                    return i;
                }
            }

        }

        throw new InvalidExpressionException();

    }


}