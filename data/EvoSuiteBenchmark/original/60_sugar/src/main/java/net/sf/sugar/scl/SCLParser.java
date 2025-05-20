// $ANTLR 3.3 Nov 30, 2010 12:46:29 net/sf/sugar/scl/SCL.g 2013-06-10 17:15:03

	package net.sf.sugar.scl;	

	import static net.sf.sugar.scl.PathConversionUtils.convertToXPath;
	import static net.sf.sugar.scl.PathConversionUtils.processDotDelimitedPath;
	
	import java.net.URI;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.DocumentBuilder;	
	import javax.xml.parsers.ParserConfigurationException;
	import org.w3c.dom.Document;
	import org.w3c.dom.Node;
	import org.w3c.dom.Attr;
	import org.w3c.dom.NodeList;
	import org.w3c.dom.NamedNodeMap;
	import org.w3c.dom.Element;
	
	import javax.xml.xpath.XPath;
	import javax.xml.xpath.XPathFactory;
	import javax.xml.xpath.XPathConstants;
	import javax.xml.xpath.XPathExpressionException;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SCLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "VALID_NAME_CHARS", "OPEN_NESTING", "CLOSE_NESTING", "ASSIGNMENT_OP", "STRING_LITERAL", "START_INCLUDE", "AS_KEYWORD", "END_INCLUDE", "START_UNPARSED_INCLUDE", "REF_CHAR", "WS", "QUOTE", "STRING_SEQUENCE_CHAR", "COMMENT", "LINE_COMMENT", "'.'"
    };
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int VALID_NAME_CHARS=4;
    public static final int OPEN_NESTING=5;
    public static final int CLOSE_NESTING=6;
    public static final int ASSIGNMENT_OP=7;
    public static final int STRING_LITERAL=8;
    public static final int START_INCLUDE=9;
    public static final int AS_KEYWORD=10;
    public static final int END_INCLUDE=11;
    public static final int START_UNPARSED_INCLUDE=12;
    public static final int REF_CHAR=13;
    public static final int WS=14;
    public static final int QUOTE=15;
    public static final int STRING_SEQUENCE_CHAR=16;
    public static final int COMMENT=17;
    public static final int LINE_COMMENT=18;

    // delegates
    // delegators


        public SCLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SCLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return SCLParser.tokenNames; }
    public String getGrammarFileName() { return "net/sf/sugar/scl/SCL.g"; }



    	public static final String SCL_ROOT_ELEMENT = "scl";
        private URI rootSCLFile;
        private Document doc;
        private Stack<Element> elements = new Stack<Element>();
        private XPath xpath = XPathFactory.newInstance().newXPath();
        private IncludeProcessor includeProcessor;
        private static LocalReferenceProcessor localReferenceProcessor;
        private List<LocalReference> localRefs = new ArrayList<LocalReference>();
        private int namespaceSteps;
        
        {
            try {
    		    doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    		    Element rootElement = doc.createElement(SCL_ROOT_ELEMENT);
    		    doc.appendChild(rootElement);
    		    this.elements.push(rootElement);
    		    
            } catch (ParserConfigurationException pce) {
    	    	pce.printStackTrace();
            }
        }
            
        protected Element addChildElement(Element element, String child) {
        	Element childElement = this.doc.createElement(child);
        	element.appendChild(childElement);

        	return childElement;
        }
        
        protected Element addChildElements(Element currentElement, String compositePath) {
        	return processDotDelimitedPath(currentElement, compositePath);
        }
        
        protected void addAttribute(Element currentElement, String attributeName, String attributeValue) {
        	Element childElement = this.doc.createElement(attributeName);
        	childElement.setTextContent(attributeValue);
        	currentElement.appendChild(childElement);
        }

        protected void loadIncludeValue(Element parentElement, String propertyName, String includeLocation, String xPathExpression) throws IncludeException {
    		this.includeProcessor.prepareIncludeValue(parentElement, propertyName, includeLocation, this.rootSCLFile, xPathExpression);
        }

        protected void loadInclude(Element parentElement, String includeLocation) throws IncludeException {
    		this.includeProcessor.prepareInclude(parentElement, includeLocation, this.rootSCLFile);
        }
        
        protected void loadIncludeNodes(Element parentElement, String includeLocation, String xPathExpression) throws IncludeException {
    		this.includeProcessor.prepareInclude(parentElement, includeLocation, this.rootSCLFile, xPathExpression);
        }
     
     	protected void loadUnparsedInclude(Element parentElement, String propertyName, String includeLocation) throws IncludeException {
     		this.includeProcessor.loadUnparsedInclude(parentElement, propertyName, includeLocation, this.rootSCLFile);
     	}
     	
        protected void prepareLocalRefTree(final Element parentElement, final String xPathExpression) {
        	LocalReference localRef = new LocalReference () {{
    										setParentElement(parentElement);
    										setXPathQuery(xPathExpression);
    									  }};
    		this.localRefs.add(localRef);
        }
            
        protected void prepareLocalRef(final Element parentElement, final String attributeName, final String xPathExpression) {
            LocalAttributeReference localRef = new LocalAttributeReference () {{
    													setParentElement(parentElement);
    													setXPathQuery(xPathExpression);
    													setAttributeName(attributeName);
    											   }};
    		this.localRefs.add(localRef);
        }
                
        public void setRootSCLFile(URI rootSCLFile) {
        	this.rootSCLFile = rootSCLFile;
        }
        
        public URI getRootSCLFile() {
        	return this.rootSCLFile;
        }
        
        public Document getRootDocument() {
        	return this.doc;
        }
        
        public void setIncludeProcessor(IncludeProcessor includeProcessor) {
        	this.includeProcessor = includeProcessor;
        }
        
        public IncludeProcessor getIncludeProcessor() {
        	return this.includeProcessor;
        }
        
        public void setLocalReferenceProcessor(LocalReferenceProcessor processor) {
        	localReferenceProcessor = processor;
        }

        public LocalReferenceProcessor getLocalReferenceProcessor() {
        	return localReferenceProcessor;
        }
            
        public List<LocalReference> getLocalRefs() {
        	return this.localRefs;
        }	
        
        public String sanitiseText(String rawText) {
            String unquotedText = stripQuotes(rawText);
            return normaliseEscapeChars(unquotedText);
        }
        
        private String stripQuotes(String quotedText) {
            return quotedText.substring(1, quotedText.length() -1);
        }
        
        private String normaliseEscapeChars(String text) {
        	return text.replace("\\\\", "\\").replace("\\\"", "\"");
        }
        



    // $ANTLR start "scl"
    // net/sf/sugar/scl/SCL.g:152:1: scl returns [Document properties] : statementSeq ;
    public final Document scl() throws RecognitionException {
        Document properties = null;

        try {
            // net/sf/sugar/scl/SCL.g:153:6: ( statementSeq )
            // net/sf/sugar/scl/SCL.g:153:8: statementSeq
            {
            pushFollow(FOLLOW_statementSeq_in_scl44);
            statementSeq();

            state._fsp--;

             
            							try {
            								this.includeProcessor.resolveIncludes(this.doc);
            								
            								localReferenceProcessor.processLocalReferences(this.localRefs);
            								
            							} catch (IncludeException ie) {
            								ie.printStackTrace();
            							} catch (LocalReferenceException lre) {
            								lre.printStackTrace();
            							} 

            							properties = doc; 
            						

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return properties;
    }
    // $ANTLR end "scl"


    // $ANTLR start "statementSeq"
    // net/sf/sugar/scl/SCL.g:168:1: statementSeq : ( statement )+ ;
    public final void statementSeq() throws RecognitionException {
        try {
            // net/sf/sugar/scl/SCL.g:168:15: ( ( statement )+ )
            // net/sf/sugar/scl/SCL.g:168:17: ( statement )+
            {
            // net/sf/sugar/scl/SCL.g:168:17: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                switch ( input.LA(1) ) {
                case VALID_NAME_CHARS:
                case START_INCLUDE:
                case REF_CHAR:
                    {
                    alt1=1;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:168:17: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_statementSeq61);
            	    statement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "statementSeq"


    // $ANTLR start "statement"
    // net/sf/sugar/scl/SCL.g:170:1: statement : ( namespaceDecl | assignment | includeTree | t= localRefTree );
    public final void statement() throws RecognitionException {
        String t = null;


        try {
            // net/sf/sugar/scl/SCL.g:170:13: ( namespaceDecl | assignment | includeTree | t= localRefTree )
            int alt2=4;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:170:15: namespaceDecl
                    {
                    pushFollow(FOLLOW_namespaceDecl_in_statement73);
                    namespaceDecl();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // net/sf/sugar/scl/SCL.g:171:9: assignment
                    {
                    pushFollow(FOLLOW_assignment_in_statement84);
                    assignment();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // net/sf/sugar/scl/SCL.g:172:9: includeTree
                    {
                    pushFollow(FOLLOW_includeTree_in_statement95);
                    includeTree();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // net/sf/sugar/scl/SCL.g:173:9: t= localRefTree
                    {
                    pushFollow(FOLLOW_localRefTree_in_statement108);
                    t=localRefTree();

                    state._fsp--;


                    							prepareLocalRefTree(this.elements.peek(), t);
                    						

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "statement"


    // $ANTLR start "namespaceDecl"
    // net/sf/sugar/scl/SCL.g:177:1: namespaceDecl : namespaceName namespaceBody ;
    public final void namespaceDecl() throws RecognitionException {
        try {
            // net/sf/sugar/scl/SCL.g:177:16: ( namespaceName namespaceBody )
            // net/sf/sugar/scl/SCL.g:177:18: namespaceName namespaceBody
            {
            pushFollow(FOLLOW_namespaceName_in_namespaceDecl120);
            namespaceName();

            state._fsp--;

            pushFollow(FOLLOW_namespaceBody_in_namespaceDecl122);
            namespaceBody();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "namespaceDecl"

    public static class namespaceName_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "namespaceName"
    // net/sf/sugar/scl/SCL.g:179:1: namespaceName : ( varName | compositeNamespaceName );
    public final SCLParser.namespaceName_return namespaceName() throws RecognitionException {
        SCLParser.namespaceName_return retval = new SCLParser.namespaceName_return();
        retval.start = input.LT(1);

        SCLParser.compositeNamespaceName_return compositeNamespaceName1 = null;


        try {
            // net/sf/sugar/scl/SCL.g:179:16: ( varName | compositeNamespaceName )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:179:18: varName
                    {
                    pushFollow(FOLLOW_varName_in_namespaceName132);
                    varName();

                    state._fsp--;

                     this.elements.push(addChildElement(this.elements.peek(), input.toString(retval.start,input.LT(-1)))); 

                    }
                    break;
                case 2 :
                    // net/sf/sugar/scl/SCL.g:180:9: compositeNamespaceName
                    {
                    pushFollow(FOLLOW_compositeNamespaceName_in_namespaceName145);
                    compositeNamespaceName1=compositeNamespaceName();

                    state._fsp--;

                     this.elements.push(addChildElements(this.elements.peek(), (compositeNamespaceName1!=null?input.toString(compositeNamespaceName1.start,compositeNamespaceName1.stop):null)));  

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "namespaceName"

    public static class compositeNamespaceName_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "compositeNamespaceName"
    // net/sf/sugar/scl/SCL.g:182:1: compositeNamespaceName : ( VALID_NAME_CHARS )+ ( '.' ( VALID_NAME_CHARS )+ )+ ;
    public final SCLParser.compositeNamespaceName_return compositeNamespaceName() throws RecognitionException {
        SCLParser.compositeNamespaceName_return retval = new SCLParser.compositeNamespaceName_return();
        retval.start = input.LT(1);

        try {
            // net/sf/sugar/scl/SCL.g:182:24: ( ( VALID_NAME_CHARS )+ ( '.' ( VALID_NAME_CHARS )+ )+ )
            // net/sf/sugar/scl/SCL.g:182:28: ( VALID_NAME_CHARS )+ ( '.' ( VALID_NAME_CHARS )+ )+
            {
            // net/sf/sugar/scl/SCL.g:182:28: ( VALID_NAME_CHARS )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                switch ( input.LA(1) ) {
                case VALID_NAME_CHARS:
                    {
                    alt4=1;
                    }
                    break;

                }

                switch (alt4) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:182:28: VALID_NAME_CHARS
            	    {
            	    match(input,VALID_NAME_CHARS,FOLLOW_VALID_NAME_CHARS_in_compositeNamespaceName160); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            // net/sf/sugar/scl/SCL.g:182:46: ( '.' ( VALID_NAME_CHARS )+ )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                switch ( input.LA(1) ) {
                case 19:
                    {
                    alt6=1;
                    }
                    break;

                }

                switch (alt6) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:182:47: '.' ( VALID_NAME_CHARS )+
            	    {
            	    match(input,19,FOLLOW_19_in_compositeNamespaceName164); 
            	    // net/sf/sugar/scl/SCL.g:182:51: ( VALID_NAME_CHARS )+
            	    int cnt5=0;
            	    loop5:
            	    do {
            	        int alt5=2;
            	        switch ( input.LA(1) ) {
            	        case VALID_NAME_CHARS:
            	            {
            	            alt5=1;
            	            }
            	            break;

            	        }

            	        switch (alt5) {
            	    	case 1 :
            	    	    // net/sf/sugar/scl/SCL.g:182:51: VALID_NAME_CHARS
            	    	    {
            	    	    match(input,VALID_NAME_CHARS,FOLLOW_VALID_NAME_CHARS_in_compositeNamespaceName166); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    if ( cnt5 >= 1 ) break loop5;
            	                EarlyExitException eee =
            	                    new EarlyExitException(5, input);
            	                throw eee;
            	        }
            	        cnt5++;
            	    } while (true);


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compositeNamespaceName"


    // $ANTLR start "namespaceBody"
    // net/sf/sugar/scl/SCL.g:184:1: namespaceBody : OPEN_NESTING ( statement )* CLOSE_NESTING ;
    public final void namespaceBody() throws RecognitionException {
        try {
            // net/sf/sugar/scl/SCL.g:184:16: ( OPEN_NESTING ( statement )* CLOSE_NESTING )
            // net/sf/sugar/scl/SCL.g:184:18: OPEN_NESTING ( statement )* CLOSE_NESTING
            {
            match(input,OPEN_NESTING,FOLLOW_OPEN_NESTING_in_namespaceBody181); 
            // net/sf/sugar/scl/SCL.g:184:31: ( statement )*
            loop7:
            do {
                int alt7=2;
                switch ( input.LA(1) ) {
                case VALID_NAME_CHARS:
                case START_INCLUDE:
                case REF_CHAR:
                    {
                    alt7=1;
                    }
                    break;

                }

                switch (alt7) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:184:32: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_namespaceBody184);
            	    statement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match(input,CLOSE_NESTING,FOLLOW_CLOSE_NESTING_in_namespaceBody188); 
             
            									this.elements.pop();
            					

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "namespaceBody"


    // $ANTLR start "assignment"
    // net/sf/sugar/scl/SCL.g:188:1: assignment : varName ASSIGNMENT_OP ( varValue | includeValue | v= unparsedIncludeValue | r= localReference ) ;
    public final void assignment() throws RecognitionException {
        String v = null;

        String r = null;

        SCLParser.varName_return varName2 = null;

        SCLParser.varValue_return varValue3 = null;

        SCLParser.includeValue_return includeValue4 = null;


        try {
            // net/sf/sugar/scl/SCL.g:188:14: ( varName ASSIGNMENT_OP ( varValue | includeValue | v= unparsedIncludeValue | r= localReference ) )
            // net/sf/sugar/scl/SCL.g:188:16: varName ASSIGNMENT_OP ( varValue | includeValue | v= unparsedIncludeValue | r= localReference )
            {
            pushFollow(FOLLOW_varName_in_assignment201);
            varName2=varName();

            state._fsp--;

            match(input,ASSIGNMENT_OP,FOLLOW_ASSIGNMENT_OP_in_assignment203); 
            // net/sf/sugar/scl/SCL.g:188:38: ( varValue | includeValue | v= unparsedIncludeValue | r= localReference )
            int alt8=4;
            switch ( input.LA(1) ) {
            case STRING_LITERAL:
                {
                alt8=1;
                }
                break;
            case START_INCLUDE:
                {
                alt8=2;
                }
                break;
            case START_UNPARSED_INCLUDE:
                {
                alt8=3;
                }
                break;
            case REF_CHAR:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:188:39: varValue
                    {
                    pushFollow(FOLLOW_varValue_in_assignment206);
                    varValue3=varValue();

                    state._fsp--;

                     						
                    							          addAttribute(this.elements.peek(), 
                    							     			(varName2!=null?input.toString(varName2.start,varName2.stop):null), 
                    							       			sanitiseText((varValue3!=null?input.toString(varValue3.start,varValue3.stop):null)));
                    							       

                    }
                    break;
                case 2 :
                    // net/sf/sugar/scl/SCL.g:194:14: includeValue
                    {
                    pushFollow(FOLLOW_includeValue_in_assignment230);
                    includeValue4=includeValue();

                    state._fsp--;


                    							      	try {
                    								        this.loadIncludeValue(this.elements.peek(),
                    								        					  (varName2!=null?input.toString(varName2.start,varName2.stop):null),
                    								        					  sanitiseText((includeValue4!=null?includeValue4.includeLocation:null)),
                    								        			          sanitiseText((includeValue4!=null?includeValue4.xPathQuery:null)));
                    								    } catch (IncludeException ie) {
                    								        ie.printStackTrace();
                    								        System.out.println(ie);
                    							        }
                    							      

                    }
                    break;
                case 3 :
                    // net/sf/sugar/scl/SCL.g:205:12: v= unparsedIncludeValue
                    {
                    pushFollow(FOLLOW_unparsedIncludeValue_in_assignment247);
                    v=unparsedIncludeValue();

                    state._fsp--;


                    								  	  try {
                    										this.loadUnparsedInclude(this.elements.peek(), (varName2!=null?input.toString(varName2.start,varName2.stop):null), v);
                    									  } catch (IncludeException ie) {
                    										ie.printStackTrace();
                    									  }
                    								  

                    }
                    break;
                case 4 :
                    // net/sf/sugar/scl/SCL.g:212:15: r= localReference
                    {
                    pushFollow(FOLLOW_localReference_in_assignment267);
                    r=localReference();

                    state._fsp--;

                    								  	
                    								  		//todo : handle empty values, also handle non numeric, date and boolean return types
                    								  		prepareLocalRef(this.elements.peek(), (varName2!=null?input.toString(varName2.start,varName2.stop):null), r);
                    							      

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "assignment"

    public static class varName_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "varName"
    // net/sf/sugar/scl/SCL.g:218:1: varName : ( VALID_NAME_CHARS )+ ;
    public final SCLParser.varName_return varName() throws RecognitionException {
        SCLParser.varName_return retval = new SCLParser.varName_return();
        retval.start = input.LT(1);

        try {
            // net/sf/sugar/scl/SCL.g:218:12: ( ( VALID_NAME_CHARS )+ )
            // net/sf/sugar/scl/SCL.g:218:14: ( VALID_NAME_CHARS )+
            {
            // net/sf/sugar/scl/SCL.g:218:14: ( VALID_NAME_CHARS )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                switch ( input.LA(1) ) {
                case VALID_NAME_CHARS:
                    {
                    alt9=1;
                    }
                    break;

                }

                switch (alt9) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:218:14: VALID_NAME_CHARS
            	    {
            	    match(input,VALID_NAME_CHARS,FOLLOW_VALID_NAME_CHARS_in_varName298); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varName"

    public static class varValue_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "varValue"
    // net/sf/sugar/scl/SCL.g:220:1: varValue : STRING_LITERAL ;
    public final SCLParser.varValue_return varValue() throws RecognitionException {
        SCLParser.varValue_return retval = new SCLParser.varValue_return();
        retval.start = input.LT(1);

        try {
            // net/sf/sugar/scl/SCL.g:220:13: ( STRING_LITERAL )
            // net/sf/sugar/scl/SCL.g:220:15: STRING_LITERAL
            {
            match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_varValue312); 

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varValue"

    public static class includeValue_return extends ParserRuleReturnScope {
        public String includeLocation;
        public String xPathQuery;
    };

    // $ANTLR start "includeValue"
    // net/sf/sugar/scl/SCL.g:223:1: includeValue returns [String includeLocation, String xPathQuery] : START_INCLUDE s1= STRING_LITERAL AS_KEYWORD s2= STRING_LITERAL END_INCLUDE ;
    public final SCLParser.includeValue_return includeValue() throws RecognitionException {
        SCLParser.includeValue_return retval = new SCLParser.includeValue_return();
        retval.start = input.LT(1);

        Token s1=null;
        Token s2=null;

        try {
            // net/sf/sugar/scl/SCL.g:224:6: ( START_INCLUDE s1= STRING_LITERAL AS_KEYWORD s2= STRING_LITERAL END_INCLUDE )
            // net/sf/sugar/scl/SCL.g:224:8: START_INCLUDE s1= STRING_LITERAL AS_KEYWORD s2= STRING_LITERAL END_INCLUDE
            {
            match(input,START_INCLUDE,FOLLOW_START_INCLUDE_in_includeValue334); 
            s1=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_includeValue338); 
            match(input,AS_KEYWORD,FOLLOW_AS_KEYWORD_in_includeValue340); 
            s2=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_includeValue344); 
            match(input,END_INCLUDE,FOLLOW_END_INCLUDE_in_includeValue346); 

            							    retval.includeLocation = (s1!=null?s1.getText():null);
            							    retval.xPathQuery = (s2!=null?s2.getText():null); 
            							

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "includeValue"


    // $ANTLR start "unparsedIncludeValue"
    // net/sf/sugar/scl/SCL.g:229:1: unparsedIncludeValue returns [String includeLocation] : START_UNPARSED_INCLUDE s1= STRING_LITERAL END_INCLUDE ;
    public final String unparsedIncludeValue() throws RecognitionException {
        String includeLocation = null;

        Token s1=null;

        try {
            // net/sf/sugar/scl/SCL.g:230:6: ( START_UNPARSED_INCLUDE s1= STRING_LITERAL END_INCLUDE )
            // net/sf/sugar/scl/SCL.g:230:8: START_UNPARSED_INCLUDE s1= STRING_LITERAL END_INCLUDE
            {
            match(input,START_UNPARSED_INCLUDE,FOLLOW_START_UNPARSED_INCLUDE_in_unparsedIncludeValue373); 
            s1=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_unparsedIncludeValue377); 
            match(input,END_INCLUDE,FOLLOW_END_INCLUDE_in_unparsedIncludeValue379); 

            							includeLocation = stripQuotes(s1.getText());
            						

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return includeLocation;
    }
    // $ANTLR end "unparsedIncludeValue"


    // $ANTLR start "includeTree"
    // net/sf/sugar/scl/SCL.g:234:1: includeTree : START_INCLUDE s1= STRING_LITERAL ( AS_KEYWORD s2= STRING_LITERAL )? END_INCLUDE ;
    public final void includeTree() throws RecognitionException {
        Token s1=null;
        Token s2=null;

        try {
            // net/sf/sugar/scl/SCL.g:234:15: ( START_INCLUDE s1= STRING_LITERAL ( AS_KEYWORD s2= STRING_LITERAL )? END_INCLUDE )
            // net/sf/sugar/scl/SCL.g:234:17: START_INCLUDE s1= STRING_LITERAL ( AS_KEYWORD s2= STRING_LITERAL )? END_INCLUDE
            {
            match(input,START_INCLUDE,FOLLOW_START_INCLUDE_in_includeTree399); 
            s1=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_includeTree403); 
            // net/sf/sugar/scl/SCL.g:234:49: ( AS_KEYWORD s2= STRING_LITERAL )?
            int alt10=2;
            switch ( input.LA(1) ) {
                case AS_KEYWORD:
                    {
                    alt10=1;
                    }
                    break;
            }

            switch (alt10) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:234:50: AS_KEYWORD s2= STRING_LITERAL
                    {
                    match(input,AS_KEYWORD,FOLLOW_AS_KEYWORD_in_includeTree406); 
                    s2=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_includeTree410); 

                    }
                    break;

            }

            match(input,END_INCLUDE,FOLLOW_END_INCLUDE_in_includeTree414); 

            							try {
            								if (s2 == null || "".equals(s2)) {
            									this.loadInclude(this.elements.peek(),
            							    					 sanitiseText(s1.getText()));
            								} else {	
            							    	this.loadIncludeNodes(this.elements.peek(),
            							    					  sanitiseText(s1.getText()),
            							    					  sanitiseText(s2.getText()));
            							    }
            							} catch (IncludeException ie) {
            							    ie.printStackTrace();
            						    }
            					

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "includeTree"


    // $ANTLR start "localRefTree"
    // net/sf/sugar/scl/SCL.g:250:1: localRefTree returns [String xPathQuery] : r= localReference ;
    public final String localRefTree() throws RecognitionException {
        String xPathQuery = null;

        String r = null;


        try {
            // net/sf/sugar/scl/SCL.g:251:7: (r= localReference )
            // net/sf/sugar/scl/SCL.g:251:10: r= localReference
            {
            pushFollow(FOLLOW_localReference_in_localRefTree442);
            r=localReference();

            state._fsp--;

             xPathQuery = r; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return xPathQuery;
    }
    // $ANTLR end "localRefTree"


    // $ANTLR start "localReference"
    // net/sf/sugar/scl/SCL.g:253:1: localReference returns [String resolvedValue] : REF_CHAR ( WS )* STRING_LITERAL ;
    public final String localReference() throws RecognitionException {
        String resolvedValue = null;

        Token STRING_LITERAL5=null;

        try {
            // net/sf/sugar/scl/SCL.g:254:7: ( REF_CHAR ( WS )* STRING_LITERAL )
            // net/sf/sugar/scl/SCL.g:254:10: REF_CHAR ( WS )* STRING_LITERAL
            {
            match(input,REF_CHAR,FOLLOW_REF_CHAR_in_localReference475); 
            // net/sf/sugar/scl/SCL.g:254:19: ( WS )*
            loop11:
            do {
                int alt11=2;
                switch ( input.LA(1) ) {
                case WS:
                    {
                    alt11=1;
                    }
                    break;

                }

                switch (alt11) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:254:19: WS
            	    {
            	    match(input,WS,FOLLOW_WS_in_localReference477); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            STRING_LITERAL5=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_localReference480); 

            							    		resolvedValue = sanitiseText((STRING_LITERAL5!=null?STRING_LITERAL5.getText():null));
            								

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return resolvedValue;
    }
    // $ANTLR end "localReference"

    // Delegated rules


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA2_eotS =
        "\6\uffff";
    static final String DFA2_eofS =
        "\6\uffff";
    static final String DFA2_minS =
        "\2\4\4\uffff";
    static final String DFA2_maxS =
        "\1\15\1\23\4\uffff";
    static final String DFA2_acceptS =
        "\2\uffff\1\3\1\4\1\1\1\2";
    static final String DFA2_specialS =
        "\6\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\4\uffff\1\2\3\uffff\1\3",
            "\1\1\1\4\1\uffff\1\5\13\uffff\1\4",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "170:1: statement : ( namespaceDecl | assignment | includeTree | t= localRefTree );";
        }
    }
    static final String DFA3_eotS =
        "\4\uffff";
    static final String DFA3_eofS =
        "\4\uffff";
    static final String DFA3_minS =
        "\2\4\2\uffff";
    static final String DFA3_maxS =
        "\1\4\1\23\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA3_specialS =
        "\4\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1",
            "\1\1\1\2\15\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "179:1: namespaceName : ( varName | compositeNamespaceName );";
        }
    }
 

    public static final BitSet FOLLOW_statementSeq_in_scl44 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statementSeq61 = new BitSet(new long[]{0x0000000000002212L});
    public static final BitSet FOLLOW_namespaceDecl_in_statement73 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_statement84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_includeTree_in_statement95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localRefTree_in_statement108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespaceName_in_namespaceDecl120 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_namespaceBody_in_namespaceDecl122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varName_in_namespaceName132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compositeNamespaceName_in_namespaceName145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALID_NAME_CHARS_in_compositeNamespaceName160 = new BitSet(new long[]{0x0000000000080010L});
    public static final BitSet FOLLOW_19_in_compositeNamespaceName164 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_VALID_NAME_CHARS_in_compositeNamespaceName166 = new BitSet(new long[]{0x0000000000080012L});
    public static final BitSet FOLLOW_OPEN_NESTING_in_namespaceBody181 = new BitSet(new long[]{0x0000000000002250L});
    public static final BitSet FOLLOW_statement_in_namespaceBody184 = new BitSet(new long[]{0x0000000000002250L});
    public static final BitSet FOLLOW_CLOSE_NESTING_in_namespaceBody188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varName_in_assignment201 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ASSIGNMENT_OP_in_assignment203 = new BitSet(new long[]{0x0000000000003310L});
    public static final BitSet FOLLOW_varValue_in_assignment206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_includeValue_in_assignment230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unparsedIncludeValue_in_assignment247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localReference_in_assignment267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VALID_NAME_CHARS_in_varName298 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_varValue312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_INCLUDE_in_includeValue334 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_includeValue338 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_AS_KEYWORD_in_includeValue340 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_includeValue344 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_END_INCLUDE_in_includeValue346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_UNPARSED_INCLUDE_in_unparsedIncludeValue373 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_unparsedIncludeValue377 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_END_INCLUDE_in_unparsedIncludeValue379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_START_INCLUDE_in_includeTree399 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_includeTree403 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_AS_KEYWORD_in_includeTree406 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_includeTree410 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_END_INCLUDE_in_includeTree414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localReference_in_localRefTree442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REF_CHAR_in_localReference475 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_WS_in_localReference477 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_localReference480 = new BitSet(new long[]{0x0000000000000002L});

}