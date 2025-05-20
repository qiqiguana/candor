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

import com.browsersoft.openhre.hl7.api.HL7DefaultConfigurationMaker;
import com.browsersoft.openhre.hl7.api.HL7DefaultConvertor;
import com.browsersoft.openhre.hl7.api.HL7DefaultConvertorHandler;
import com.browsersoft.openhre.hl7.api.MessageTranslator;
import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import com.browsersoft.openhre.hl7.impl.HL7DefaultConfigurationMakerImpl;
import com.browsersoft.openhre.hl7.impl.HL7DefaultConvertorImpl;
import com.browsersoft.openhre.hl7.impl.MessageTranslatorImpl;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Convertor implements HL7DefaultConvertorHandler {

    public void error( int type, String message ) {
        //if ( type == HL7Checker.TYPE_ERROR ) {
            System.out.println( message );
        //}
    }

    public void convert( String inputFile, String outputFile, String inputEncoding, String outputEncoding, String messageType, String hl7Version ) {

        MessageTranslator translator = new MessageTranslatorImpl();


        try {
            HL7DefaultConfigurationMaker configMaker = new HL7DefaultConfigurationMakerImpl();
            HL7VersionConfigurationMap configMap = configMaker.readConfiguration();

            HL7DefaultConvertor convertor = new HL7DefaultConvertorImpl();
            convertor.setHandler(this);
            convertor.setConfigurations(configMap);
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            String begin = "<?xml version=\"1.0\" encoding=\"" + outputEncoding + "\"?>\n<HL7>\n";
            String end = "</HL7>\n";
            output.write(begin.getBytes(outputEncoding));
            convertor.convert( input , inputEncoding, output, outputEncoding, messageType, hl7Version);
            output.write(end.getBytes(outputEncoding));
            output.close();
            input.close();

        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( SAXException e ) {
            e.printStackTrace();
        } catch ( InvalidConfigDataStructureException e ) {
            System.out.println( translator.getLocalizedMessage( e.getMessageCode() ) + " \"" + e.getPostfix()  + "\"");
        } catch ( ParserException e ) {
            System.out.println( translator.getLocalizedMessage( e.getMessageCode() ) + " \"" + e.getPostfix()  + "\"");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {

        if ( args.length != 6 ) {
            System.out.println( "Invalid length of argumens:");
            System.out.println( "1. argument: inputFile");
            System.out.println( "2. argument: inputEncoding");
            System.out.println( "3. argument: outputFile");
            System.out.println( "4. argument: outputEncoding");
            System.out.println( "5. argument: messageType");
            System.out.println( "6. argument: hl7version");
            return;
        }

          new Convertor().convert(args[0], args[1], args[2], args[3], args[4], args[5]);
    }

}
