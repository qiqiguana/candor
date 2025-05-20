package fr.pingtimeout.jtail;

import fr.pingtimeout.jtail.gui.view.JTailMainFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: vadmin
 * Date: Sep 5, 2010
 * Time: 7:08:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main(String ... args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml"
        );

        JTailMainFrame jTailMainFrame = (JTailMainFrame) applicationContext.getBean("jTailMainFrame");
        jTailMainFrame.pack();
        jTailMainFrame.setVisible(true);
    }
}
