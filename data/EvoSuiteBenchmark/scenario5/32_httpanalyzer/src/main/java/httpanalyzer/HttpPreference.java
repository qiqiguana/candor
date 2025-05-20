package httpanalyzer;

import java.util.Properties;
import javax.swing.JFrame;

/**
 * @author vlad
 */
public class HttpFileUtils {

    /**
     * Method load your template from file
     * Creates JFileChooser and  load from XML file
     *
     * @param parentFrame
     */
    public Properties loadPreferenceFromFile(JFrame parentFrame);
}

/**
 * @author vlad
 */
public class HttpPreference {

    /**
     * Get values from work form and set properties
     * Then call method FileUtil.savePreferenceToFile
     *
     * @param mainView HttpAnalyzerView
     * @param parentFrame JFrame
     * @return String Notes
     */
    public String loadPreference(HttpAnalyzerView mainView, JFrame parentFrame) {
        Properties properties = new Properties();
        HttpFileUtils fileUtil = new HttpFileUtils();
        properties = fileUtil.loadPreferenceFromFile(parentFrame);
        // Set values
        mainView.mergeInfoCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.tools.options.merge")));
        String workProps = properties.getProperty("httpanalyzer.options.proxytype");
        if (workProps.equals("None")) {
            mainView.proxyNoneRadioButton.setSelected(true);
        } else if (workProps.equals("Http")) {
            mainView.proxyHttpRadioButton.setSelected(true);
        } else {
            mainView.proxySocksRadioButton.setSelected(true);
        }
        mainView.proxyField.setText(properties.getProperty("httpanalyzer.options.proxy"));
        mainView.proxyAuthCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.proxyauth")));
        mainView.proxyUserTextField.setText(properties.getProperty("httpanalyzer.options.proxyuser"));
        mainView.proxyPassPasswordField.setText(properties.getProperty("httpanalyzer.options.proxypassword"));
        if (properties.getProperty("httpanalyzer.options.httpversion").equals("1.1")) {
            mainView.httpVersionComboBox.setSelectedIndex(0);
        } else {
            mainView.httpVersionComboBox.setSelectedIndex(1);
        }
        mainView.httpAuthCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.httpauth")));
        mainView.httpUserTextField.setText(properties.getProperty("httpanalyzer.options.httpauthuser"));
        mainView.httpPassPasswordField.setText(properties.getProperty("httpanalyzer.options.httpauthpassword"));
        mainView.customRefererCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.customrefer.enable")));
        mainView.customRefererTextField.setText(properties.getProperty("httpanalyzer.options.customrefer"));
        mainView.customCookieCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.customcookie.enable")));
        mainView.customCookieTextField.setText(properties.getProperty("httpanalyzer.options.customcookie"));
        workProps = properties.getProperty("httpanalyzer.options.agents");
        if (workProps.equals("0")) {
            mainView.firefoxRadioButton.setSelected(true);
        } else if (workProps.equals("1")) {
            mainView.ie6RadioButton.setSelected(true);
        } else if (workProps.equals("2")) {
            mainView.ie7RadioButton.setSelected(true);
        } else if (workProps.equals("3")) {
            mainView.ie8RadioButton.setSelected(true);
        } else if (workProps.equals("4")) {
            mainView.googleBotRadioButton.setSelected(true);
        } else if (workProps.equals("5")) {
            mainView.msnRadioButton.setSelected(true);
        } else if (workProps.equals("6")) {
            mainView.yahooRadioButton.setSelected(true);
        } else {
            mainView.iPhoneRadioButton.setSelected(true);
        }
        workProps = properties.getProperty("httpanalyzer.options.method");
        if (workProps.equals("GET")) {
            mainView.getRadio.setSelected(true);
        } else if (workProps.equals("POST")) {
            mainView.postRadio.setSelected(true);
        } else {
            mainView.headRadio.setSelected(true);
        }
        mainView.toFileTextField.setText(properties.getProperty("httpanalyzer.options.tofile"));
        mainView.toFileCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.tofile.enable")));
        int ii = Integer.decode(properties.getProperty("httpanalyzer.options.urlcombo.elements"));
        mainView.urlCombo.removeAllItems();
        for (int i = 0; i < ii; i++) {
            String el = properties.getProperty("httpanalyzer.options.urlcombo." + Integer.toString(i));
            mainView.urlCombo.addItem(el);
        }
        ii = Integer.decode(properties.getProperty("httpanalyzer.options.params.elements"));
        mainView.paramsComboBox.removeAllItems();
        for (int i = 0; i < ii; i++) {
            String el = properties.getProperty("httpanalyzer.options.params." + Integer.toString(i));
            mainView.paramsComboBox.addItem(el);
        }
        mainView.customHeaders.setText(properties.getProperty("httpanalyzer.options.custom.headers"));
        mainView.useCustomHeadersCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.usecustom.headers")));
        mainView.intelSaveCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("httpanalyzer.options.intelligencemode")));
        return properties.getProperty("httpanalyzer.notes");
    }
}
