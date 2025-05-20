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

import com.browsersoft.openhre.hl7.api.regular.ExpressionMatrix;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapper;

import java.util.ArrayList;

public class ExpressionMatrixImpl implements ExpressionMatrix {

    private ArrayList lines;
    private int numberOfElements;

    public ExpressionMatrixImpl() {
        lines = new ArrayList();
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getNumberOfNodes() {
        return lines.size();
    }

    public void creatMatrix( int numberOfElements ) {
        this.numberOfElements = numberOfElements;
    }

    public void setValue( int node, int elementID, int value ) {
        if ( node >=0 && node < lines.size() ) {
            //System.out.println("setValue: " + node + " --" + ((char)(((int) 'A') + elementID)) + "-->" + value );
            int[] elements = (int[]) lines.get(node);
            if ( elementID >=0 && elementID < elements.length ) {
                elements[elementID] = value;
            }
        }
    }

    public int getValue( int node, int elementID ) {
        if ( node >=0 && node < lines.size() ) {
            int[] elements = (int[]) lines.get(node);
            if ( elementID >=0 && elementID < elements.length ) {
                return elements[elementID];
            }
        }
        return -1;
    }

    public int addNewNode() {
        int[] ar = new int[numberOfElements];
        for ( int i = 0; i < ar.length; i++ ) {
            ar[i] = -1;
        }
        lines.add(ar);
        return lines.size() - 1;
    }

    public String outNoStandardConnections( boolean forward, ExpressionElementMapper mapper ) {
        String ret = "";
        for ( int i = 0 ; i < lines.size(); i++ ) {
            for ( int j = 0 ; j < numberOfElements; j++ ) {

                String idElement = "";
               if ( mapper == null ) {
                   idElement = String.valueOf((char)(((int)'A') + j));
               } else {
                   idElement = mapper.getItem(j).toString();
               }

               int val = getValue(i, j);
               if ( val != (-1) && (i != j) ) {
                  if ( forward ) {
                      if ( i < val ) {
                          ret += i + " --- " + idElement + " ---> " + val;
                          ret += "\n";
                      }
                  }  else {
                      if ( i >= val ) {
                          ret += i + " --- " + idElement + " ---> " + val;
                          ret += "\n";
                      }
                  }
               }
            }
        }
        return ret;
    }

    public String toString() {
        String ret = "MATRIX\n";
        ret += "-----------------------\n";
        char ch = 'A';
        ret += " | ";
        for ( int j = 0 ; j < numberOfElements; j++ ) {
            ret += ch;
            ch++;
            if ( j != numberOfElements - 1 ) {
              ret += " , ";
            }
        }
        ret += "\n";
        for ( int i = 0 ; i < lines.size(); i++ ) {
            ret += i + "| ";
            for ( int j = 0 ; j < numberOfElements; j++ ) {
               int val = getValue(i, j);
               if ( val == (-1)) {
                  ret += "E";
               } else {
                  ret += getValue(i, j);
               }
               if ( j != numberOfElements - 1 ) {
                  ret += " , ";
               }
            }
            ret += "\n";
        }


        ret += "\nCONNECTIONS\n";
        ret += "-----------------------\n";

        for ( int i = 0 ; i < lines.size(); i++ ) {
            for ( int j = 0 ; j < numberOfElements; j++ ) {
               int val = getValue(i, j);
               if ( val != (-1) && (i != j) ) {
                  ret += i + " --- " + (char)(((int)'A') + j) + " ---> " + val;
                  ret += "\n";
               }
            }
        }

        return ret;
    }

}


