package uk.me.jockmacmad.jstate.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import uk.me.jockmacmad.jstate.Interface;
import uk.me.jockmacmad.jstate.JstateDocument;
import uk.me.jockmacmad.jstate.State;
import de.hunsicker.jalopy.Jalopy;

/**
 * @author Administrator
 *
 */
public class Statemachine2Java {

    private static final int INDENT = 4;
    private static final int OFFSET = 4;
    /**
     * @param args
     */
    public static void main(final String[] args) {
        Statemachine2Java parser = new Statemachine2Java();
        parser.init();
        if (args.length > 0) {
            parser.parseXml(args[0]);
        } else {
            System.exit(-1);
        }
    }

    private JstateDocument xmlDocument;
    
    private void createFactory(JstateDocument stateMachineObject)
    throws Exception {
        VelocityContext context = new VelocityContext();
        String packagePath = stateMachineObject.getJstate().getStatemachine().getPackage().replace('.', '/');
        context.put("StateMachine", stateMachineObject);
        StringWriter generatedJava = new StringWriter();
        StringWriter outputJava = new StringWriter();        
        Velocity.mergeTemplate("FactoryTemplate.vm", context, generatedJava );
        formatJava(generatedJava, outputJava);
        FileWriter fw = 
            new FileWriter(
                    "c:/Users/Administrator/workspace/JState/GeneratedOutput/"
                    + packagePath
                    + "/"
                    + stateMachineObject.getJstate().getStatemachine().getClass1()
                    + "Factory.java"
                    );
        fw.write(outputJava.toString());
        fw.close();
    }

    private void createInterface(JstateDocument stateMachineObject)
    throws Exception {
        String packagePath = stateMachineObject.getJstate().getStatemachine().getPackage().replace('.', '/');
        VelocityContext context = new VelocityContext();        
        context.put("StateMachine", stateMachineObject);
        context.put("Interface", stateMachineObject.getJstate().getStatemachine().getInterface());
        StringWriter generatedJava = new StringWriter();
        StringWriter outputJava = new StringWriter();        
        Velocity.mergeTemplate("InterfaceTemplate.vm", context, generatedJava );
        formatJava(generatedJava, outputJava);
        FileWriter fw = 
            new FileWriter(
                    "c:/Users/Administrator/workspace/JState/GeneratedOutput/"
                    + packagePath
                    + "/I"
                    + stateMachineObject.getJstate().getStatemachine().getClass1()
                    + ".java"
                    );
        fw.write(outputJava.toString());
        fw.close();
        
    }

    private void createStates(JstateDocument stateMachineObject)
    throws Exception {
        String packagePath = stateMachineObject.getJstate().getStatemachine().getPackage().replace('.', '/');
        FileWriter fw; 
        for(State state : stateMachineObject.getJstate().getStatemachine().getStateArray()) {
            VelocityContext context = new VelocityContext();
            context.put("Package", stateMachineObject.getJstate().getStatemachine().getPackage());
            context.put("Interface", stateMachineObject.getJstate().getStatemachine().getInterface());
            context.put("State", state);
            context.put("Clazz", stateMachineObject.getJstate().getStatemachine().getClass1());
            StringWriter generatedJava = new StringWriter();
            StringWriter outputJava = new StringWriter();        
            Velocity.mergeTemplate("StateTemplate.vm", context, generatedJava );
            formatJava(generatedJava, outputJava);
            fw = new FileWriter(
                    "c:/Users/Administrator/workspace/JState/GeneratedOutput/"
                    + packagePath
                    + "/"
                    + state.getName()
                    + ".java"
                    );
            fw.write(outputJava.toString());
            fw.close();
        }
    }
    
    private void createStateMachine(JstateDocument stateMachineObject)
    throws Exception {
        VelocityContext context = new VelocityContext();
        String packagePath = stateMachineObject.getJstate().getStatemachine().getPackage().replace('.', '/');
        context.put("StateMachine", stateMachineObject);
        StringWriter generatedJava = new StringWriter();
        StringWriter outputJava = new StringWriter();        
        Velocity.mergeTemplate("StateMachineTemplate.vm", context, generatedJava );
        formatJava(generatedJava, outputJava);
        FileWriter fw = 
            new FileWriter(
                    "c:/Users/Administrator/workspace/JState/GeneratedOutput/"
                    + packagePath
                    + "/"
                    + stateMachineObject.getJstate().getStatemachine().getClass1()
                    + ".java"
                    );
        fw.write(outputJava.toString());
        fw.close();
    }
    

    private String formatJava(StringWriter generatedJava, StringWriter outputJava) {
        Jalopy jalopy;
        jalopy = new Jalopy();
        jalopy.setInput(generatedJava.toString(), "c:/Users/Administrator/workspace/JState/GeneratedOutput");
        System.out.println(generatedJava );        
        jalopy.setOutput(outputJava);
        jalopy.format();
        System.out.println(outputJava );
        return outputJava.toString();
    }
    
    private final void generateJava(JstateDocument stateMachineObject) {
        try {
            createFactory(stateMachineObject);
            createInterface(stateMachineObject);            
            createStates(stateMachineObject);
            createStateMachine(stateMachineObject);
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MethodInvocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private final void init() {
        /* first, we init the runtime engine.  Defaults are fine. */
        try {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.path", "c:/Users/Administrator/workspace/JState/templates/");
            Velocity.init(p);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private final void parseXml(String xmlFile) {
        XmlOptions options = new XmlOptions();
        List<XmlError> errors = new ArrayList<XmlError>();
        options.setSavePrettyPrintIndent(INDENT);
        options.setSavePrettyPrintOffset(OFFSET);
        options.setSavePrettyPrint();
        options.setErrorListener(errors);
        try {
            xmlDocument = JstateDocument.Factory.parse(new File(xmlFile), options);
            if (xmlDocument.validate()) {
                generateJava(xmlDocument);
            } else {
                xmlDocument.dump();
                for(XmlError error : errors) {
                    System.out.println(error.getMessage());
                }
            }
        } catch (XmlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
