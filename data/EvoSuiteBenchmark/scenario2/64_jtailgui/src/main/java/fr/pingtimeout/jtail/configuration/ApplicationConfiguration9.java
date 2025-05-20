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
     * Create the "close all" action that will be used by menus and buttons.
     *
     * @return a new "close all" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public CloseAllAction closeAllAction() {
        return new CloseAllAction(jTailMainModel());
    }
}
