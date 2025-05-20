/*
 * Created on 16.09.2004
 */
package ch.bluepenguin.email.client.tests;


import junit.framework.TestCase;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
/**
 * @author Christian
 *
 */
public class SpringTestBase extends TestCase {
    protected XmlBeanFactory factory ;
    
    public void setUp() {
    	ClassPathResource res = new ClassPathResource("applicationContext.xml");
        factory = new XmlBeanFactory(res);
    }

}
