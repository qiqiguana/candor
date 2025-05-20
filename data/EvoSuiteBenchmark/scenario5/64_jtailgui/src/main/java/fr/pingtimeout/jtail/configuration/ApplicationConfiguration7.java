package fr.pingtimeout.jtail.configuration;

import fr.pingtimeout.jtail.gui.action.*;
import fr.pingtimeout.jtail.gui.controller.SelectIndexTypeListener;
import fr.pingtimeout.jtail.gui.model.JTailMainModel;
import fr.pingtimeout.jtail.gui.model.OpenFileModel;
import fr.pingtimeout.jtail.gui.view.JTailMainFrame;
import fr.pingtimeout.jtail.gui.view.OpenFileDialog;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import java.util.ResourceBundle;

@Configuration
public class ApplicationConfiguration {

    /**
     * Create the "open" action that will be used by menus and buttons.
     *
     * @return a new "open" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileAction openAction() {
        return new OpenFileAction(jTailMainModel(), openFileModel(), openFileDialog());
    }

    /**
     * Create an application model.
     *
     * @return a new application model
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public JTailMainModel jTailMainModel();

    /**
     * Create an "open file" model.
     *
     * @return a new "open file" model
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileModel openFileModel();

    /**
     * Create the "open file" dialog
     *
     * @return the "open file" dialog
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileDialog openFileDialog();
}
