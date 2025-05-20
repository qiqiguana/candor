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
     * Create the "open file" dialog
     *
     * @return the "open file" dialog
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileDialog openFileDialog() {
        return new OpenFileDialog(openFileModel(), chooseFileAction(), selectIndexTypeListener(), indexFileAction());
    }

    /**
     * Create an "open file" model.
     *
     * @return a new "open file" model
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileModel openFileModel();

    /**
     * Create the "choose a file" action that will be used by menus and buttons.
     *
     * @return a new "choose a file" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public ChooseFileAction chooseFileAction();

    /**
     * Create the "select index type" action that will be used by menus and buttons.
     *
     * @return a new "select index type" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public SelectIndexTypeListener selectIndexTypeListener();

    /**
     * Create the "index file" action that will be used by menus and buttons.
     *
     * @return a new "index file" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public IndexFileAction indexFileAction();
}
