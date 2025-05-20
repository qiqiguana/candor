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

import com.browsersoft.openhre.hl7.api.config.HL7Message;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroup;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroupItem;
import com.browsersoft.openhre.hl7.api.config.HL7MessageSegment;
import com.browsersoft.openhre.hl7.api.regular.Expression;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapper;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapperItem;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrix;
import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrixBuilder;
import com.browsersoft.openhre.hl7.api.regular.ExpressionPart;
import com.browsersoft.openhre.hl7.api.regular.InvalidExpressionException;
import com.browsersoft.openhre.hl7.api.regular.MessageTracer;
import com.browsersoft.openhre.hl7.api.regular.MessageTracerHandler;

import java.util.Stack;

public class MessageTracerImpl implements MessageTracer {

    private ExpressionElementMapper mapper;
    private ExpressionMatrix matrix;
    private MessageTracerHandler handler;
    private int actualNode = 0;
    private Stack additionalStack;

    public MessageTracerImpl() {
        additionalStack = new Stack();
    }

    public ExpressionMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix( ExpressionMatrix matrix ) {
        this.matrix = matrix;
    }

    public ExpressionElementMapper getMapper() {
        return mapper;
    }

    public void setMapper( ExpressionElementMapper mapper ) {
        this.mapper = mapper;
    }

    public MessageTracerHandler getHandler() {
        return handler;
    }

    public void setHandler( MessageTracerHandler handler ) {
        this.handler = handler;
    }

    public void reset() {
        actualNode = 0;
    }

    public void processNextSegment( String segmentID ) {

        additionalStack.clear();
        if ( !browseNextSegment( segmentID, actualNode, additionalStack, false ) ) {
           handler.messageTracerErrorEvent( segmentID );
        }

    }

    public void processEnd() {

        additionalStack.clear();
        if ( !browseNextSegment( "", actualNode, additionalStack, true ) ) {
           handler.messageTracerErrorEvent( "END POINT" );
        }
    }

    private boolean browseNextSegment( String segmentID, int node, Stack additionalStack, boolean searchEnd ) {


        for ( int i = 0; i < matrix.getNumberOfElements(); i++ ) {
            int nextNode = matrix.getValue(node, i);
            if ( nextNode != (-1)) {
                // it is connection node -- mapper.getItem(i) --> nextNode
                ExpressionElementMapperItem element = mapper.getItem(i);
                switch( element.getType()) {
                    case  ExpressionElementMapperItem.SEGMENT_ITEM:
                        //the element is a segment
                        if ( element.getID().equals(segmentID)) {
                            actualNode = nextNode;
                            outStackToHandler(additionalStack);
                            handler.messageTracerSegmentEvent(element.getSegment());
                            return true;
                        }
                        break;
                    case ExpressionElementMapperItem.ADDITIONAL_ITEM_BEGIN:
                    case ExpressionElementMapperItem.ADDITIONAL_ITEM_END:
                        boolean pushOK = pushToStack(additionalStack, element);
                        if ( pushOK ) {
                            if ( browseNextSegment( segmentID, nextNode, additionalStack, searchEnd)) {
                                return true;
                            }
                            additionalStack.pop();
                        }
                        break;
                    case ExpressionElementMapperItem.END_ITEM:
                        if ( searchEnd ) {
                            outStackToHandler(additionalStack);
                            return true;
                        }
                        break;
                }
            }
        }
        return false;
    }

    private boolean pushToStack( Stack additionalStack, ExpressionElementMapperItem element ) {
        for ( int i = 0; i < additionalStack.size(); i++ ) {
            if ( additionalStack.get(i) == element ) {
                return false;
            }
        }
        additionalStack.push(element);
        return true;
    }

    private void outStackToHandler( Stack additionalStack ) {

       for ( int i = 0; i < additionalStack.size(); i++ ) {
           ExpressionElementMapperItem element = (ExpressionElementMapperItem) additionalStack.get(i);
            if ( element.getType() == ExpressionElementMapperItem.ADDITIONAL_ITEM_BEGIN ) {
               handler.messageTracerAdditionalTagBeginEvent(element.getID());
            } else if ( element.getType() == ExpressionElementMapperItem.ADDITIONAL_ITEM_END ) {
               handler.messageTracerAdditionalTagEndEvent(element.getID());
            }
        }

    }

    public void buildMatrixForMessage( HL7Message message ) throws InvalidExpressionException {

          Expression expression = new ExpressionImpl();

          HL7MessageGroup group = message.getGroup();

          mapper = new ExpressionElementMapperImpl();

          processGroup( expression, group );

          // add the end element
          ExpressionElementMapperItem mapperItem = new ExpressionElementMapperItemImpl();
          mapperItem.setType( ExpressionElementMapperItem.END_ITEM );
          mapper.addItem(mapperItem);

          ExpressionPart part = new ExpressionPartImpl();
          part.setType( ExpressionPart.ELEMENT );
          part.setElementID( mapper.size() - 1);
          expression.addItem(part);

          expression.setNumberOfElementTypes( mapper.size() );

          ExpressionMatrixBuilder builder = new ExpressionMatrixBuilderImpl();

          matrix = builder.buildMatrix(expression);
          /*System.out.println("----------------------------------------");
          System.out.println(message.getID());
          System.out.println("----------------------------------------");
          System.out.println("Forward");
          System.out.println(matrix.outNoStandardConnections(true, mapper));
          System.out.println("Backward");
          System.out.println(matrix.outNoStandardConnections(false, mapper));*/

    }

    public void processGroup( Expression expression, HL7MessageGroup group ) {

        doBeginItem(expression, group );

        if ( !group.getAdditional().equals("") ) {

             //new mapper item
             ExpressionElementMapperItem mapperItem = new ExpressionElementMapperItemImpl();
             mapperItem.setType( ExpressionElementMapperItem.ADDITIONAL_ITEM_BEGIN );
             mapperItem.setID( group.getAdditional() );
             mapper.addItem(mapperItem);

             ExpressionPart part = new ExpressionPartImpl();
             part.setType( ExpressionPart.ELEMENT );
             part.setElementID( mapper.size() - 1);
             expression.addItem(part);

        }


        processGroupContent(expression, group );


        if ( !group.getAdditional().equals("") ) {

             //new mapper item
             ExpressionElementMapperItem mapperItem = new ExpressionElementMapperItemImpl();
             mapperItem.setType( ExpressionElementMapperItem.ADDITIONAL_ITEM_END );
             mapperItem.setID( group.getAdditional() );
             mapper.addItem( mapperItem );

             ExpressionPart part = new ExpressionPartImpl();
             part.setType( ExpressionPart.ELEMENT );
             part.setElementID( mapper.size() - 1);
             expression.addItem(part);

        } 

        doEndItem(expression, group );



    }


    public void doBeginItem( Expression expression, HL7MessageGroupItem item ) {

         if ( item.isRepeatable() ) {
           ExpressionPart part = new ExpressionPartImpl();
           part.setType( ExpressionPart.OPEN_REPEATABLE );
           expression.addItem(part);
        }

        if ( !item.isRequired() ) {
           ExpressionPart part = new ExpressionPartImpl();
           part.setType( ExpressionPart.OPEN_OPTIONAL );
           expression.addItem(part);
        }

    }

    public void doEndItem( Expression expression, HL7MessageGroupItem item ) {

        if ( !item.isRequired() ) {
           ExpressionPart part = new ExpressionPartImpl();
           part.setType( ExpressionPart.CLOSE_OPTIONAL );
           expression.addItem(part);
        }

        if ( item.isRepeatable() ) {
           ExpressionPart part = new ExpressionPartImpl();
           part.setType( ExpressionPart.CLOSE_REPEATABLE );
           expression.addItem(part);
        }

    }

    public void processGroupContent( Expression expression, HL7MessageGroup group ) {

        for ( int i = 0; i < group.size(); i++ ) {

            HL7MessageGroupItem item = group.getItem(i);
            if ( item.getType() == HL7MessageGroupItem.ITEM_TYPE_GROUP ) {
                processGroup ( expression, (HL7MessageGroup) item );
            } else {

                 HL7MessageSegment segment = (HL7MessageSegment) item;

                 doBeginItem(expression, item );

                 //new mapper item
                 ExpressionElementMapperItem mapperItem = new ExpressionElementMapperItemImpl();
                 mapperItem.setType( ExpressionElementMapperItem.SEGMENT_ITEM );
                 mapperItem.setID(segment.getID());
                 mapper.addItem( mapperItem );

                 ExpressionPart part = new ExpressionPartImpl();
                 part.setType( ExpressionPart.ELEMENT );
                 part.setElementID( mapper.size() - 1);
                 expression.addItem(part);

                doEndItem(expression, item );

            }
        }

    }
}