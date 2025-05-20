package com.browsersoft.openhre.hl7;

import com.browsersoft.openhre.hl7.api.parse.XML2HL7;
import com.browsersoft.openhre.hl7.api.parse.XML2HL7Handler;
import com.browsersoft.openhre.hl7.impl.parser.XML2HL7Impl;
import org.xml.sax.SAXException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Date: 19.12.2006
 * Time: 0:12:41
 */
public class ConvertorXMLToHL7 implements XML2HL7Handler {

    public void error( String message ) {
        System.out.println( "error" + message );
    }


    public void convert( String inputFile, String outputFile ) throws IOException, SAXException {

        XML2HL7 convertor = new XML2HL7Impl();
        convertor.setHandler(this);
        Reader reader = new FileReader(inputFile);
        Writer writer = new FileWriter(outputFile);
        convertor.convert( reader, writer);
        reader.close();
        writer.close();
    }

    public static void main( String[] args ) {

        if ( args.length != 2 ) {
            System.out.println( "Invalid length of argumens:");
            System.out.println( "1. argument: inputFile");
            System.out.println( "2. argument: outputFile");
            return;
        }

        try {
            new ConvertorXMLToHL7().convert(args[0], args[1]);
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( SAXException e ) {
            e.printStackTrace();
        }
    }
}
