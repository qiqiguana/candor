package nu.staldal.xodus;

import java.io.*;

import junit.framework.*;


public class TestXMLCharacterEncoder extends TestCase
{
    private char[] ca;

    
    public TestXMLCharacterEncoder(String name)
    {
        super(name);
    }
	
    
    public void setUp() throws Exception
    {        
        ca = new char[] { 'A', 'B', 'C', 'D', 'E', 'F' };
    }
    
    
    public void testXMLCharacterEncoderWriter() throws Exception
	{
        StringWriter buf = new StringWriter();
        XMLCharacterEncoder xce = new XMLCharacterEncoder(buf);       
        
        xce.write('a');
        xce.write(ca);
        xce.write(ca, 2, 3);
        xce.write("Räksmörgås!");     
        xce.write("abcdef", 2, 3);
        xce.finish();
        // xce.flush();
        xce.close(); 

        assertEquals("aABCDEFCDERäksmörgås!cde", buf.toString());
	}   
    

    public void testXMLCharacterEncoder() throws Exception
    {
        _testXMLCharacterEncoder("iso-8859-1");            
        _testXMLCharacterEncoder("utf-8");
        _testXMLCharacterEncoder("utf-16");                    
    }
    
    
    private void _testXMLCharacterEncoder(String encoding) throws Exception
	{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLCharacterEncoder xce = new XMLCharacterEncoder(baos, encoding);
                
        xce.write('a');
        xce.write(ca);
        xce.write(ca, 2, 3);
        xce.write("Räksmörgås!");     
        xce.write("abcdef", 2, 3); 
        xce.finish();
        // xce.flush();
        xce.close(); 

        byte[] ba = baos.toByteArray();
        
        /* 
        System.out.print(encoding + " [");
        for (int i = 0; i<ba.length; i++)
        {
            System.out.print(ba[i]);
            if (i<ba.length-1) System.out.print(','); 
        }
        System.out.println(']');
        */
        
        assertEquals("aABCDEFCDERäksmörgås!cde", new String(ba, encoding));
	}   

    
    public void testXMLCharacterEncoderWithoutEscaping() throws Exception
	{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLCharacterEncoder xce = new XMLCharacterEncoder(baos, "us-ascii");
                        
        xce.write('a');
        xce.write(ca);
        xce.write(ca, 2, 3);
        try {
            xce.write("Räksmörgås!");
            fail("Should throw java.io.CharConversionException");
        }
        catch (java.io.CharConversionException e)
        {         
        }
	}   

    
    public void testXMLCharacterEncoderEscaping() throws Exception
	{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLCharacterEncoder xce = new XMLCharacterEncoder(baos, "us-ascii");
                
        // xce.enableEscaping();
        xce.write('a');
        xce.write(ca);
        xce.write(ca, 2, 3);
        xce.write("Räksmörgås!");     
        xce.write("abcdef", 2, 3); 
        xce.finish();
        // xce.flush();
        xce.close(); 

        byte[] ba = baos.toByteArray();
        
        /*
        System.out.print("us-ascii [");
        for (int i = 0; i<ba.length; i++)
        {
            System.out.print(ba[i]);
            if (i<ba.length-1) System.out.print(','); 
        }
        System.out.println(']');
        */
        
        assertEquals("aABCDEFCDER&#xe4;ksm&#xf6;rg&#xe5;s!cde", new String(ba, "us-ascii"));
	}   
    
    
	public void tearDown()
	{
	}
}

