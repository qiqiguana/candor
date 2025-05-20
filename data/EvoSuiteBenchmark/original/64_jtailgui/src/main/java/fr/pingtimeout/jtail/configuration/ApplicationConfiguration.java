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
    protected static final ResourceBundle
            bundle = ResourceBundle.getBundle("fr.pingtimeout.jtail.gui.language"); //NON-NLS

    /**
     * Create an application model.
     *
     * @return a new application model
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public JTailMainModel jTailMainModel() {
        return new JTailMainModel();
    }

    /**
     * Create an "open file" model.
     *
     * @return a new "open file" model
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public OpenFileModel openFileModel() {
        return new OpenFileModel();
    }

    /**
     * Create the "file" menu action that will be used by menus and buttons.
     *
     * @return a new "file" menu action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public MenuAction fileMenuAction() {
        return new MenuAction(bundle.getString("action.file.label"), bundle.getString("action.file.mnemonic"));
    }

    /**
     * Create the "tools" menu action that will be used by menus and buttons.
     *
     * @return a new "tools" menu action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public MenuAction toolsMenuAction() {
        return new MenuAction(
                bundle.getString("action.tools.label"),
                bundle.getString("action.tools.mnemonic"));
    }

    /**
     * Create the "choose a file" action that will be used by menus and buttons.
     *
     * @return a new "choose a file" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public ChooseFileAction chooseFileAction() {
        return new ChooseFileAction(openFileModel());
    }

    /**
     * Create the "select index type" action that will be used by menus and buttons.
     *
     * @return a new "select index type" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public SelectIndexTypeListener selectIndexTypeListener() {
        return new SelectIndexTypeListener(openFileModel());
    }


    /**
     * Create the "index file" action that will be used by menus and buttons.
     *
     * @return a new "index file" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public IndexFileAction indexFileAction() {
        return new IndexFileAction(jTailMainModel(), openFileModel());
    }


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
     * Create the "close" action that will be used by menus and buttons.
     *
     * @return a new "close" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public CloseAction closeAction() {
        return new CloseAction(jTailMainModel());
    }

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

    /**
     * Create the "quit" action that will be used by menus and buttons.
     *
     * @return a new "quit" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public QuitAction quitAction() {
        return new QuitAction();
    }

    /**
     * Create the "Highlight" action that will be used by menus and buttons.
     *
     * @return a new "Highlight" action
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public HighlightAction highlightAction() {
        return new HighlightAction(jTailMainModel());
    }

    /**
     * Create the main frame of the application
     *
     * @return the main frame of the application
     */
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.NO)
    public JTailMainFrame jTailMainFrame() {
        return new JTailMainFrame(
                jTailMainModel(),
                fileMenuAction(),
                openAction(),
                closeAction(),
                closeAllAction(),
                quitAction(),
                toolsMenuAction(),
                highlightAction());
    }

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


    // @see http://static.springsource.org/spring/docs/3.0.0.M3/spring-framework-reference/html/ch04s12.html
}
