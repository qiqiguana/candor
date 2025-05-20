/*
 * GNU GENERAL PUBLIC LICENSE
 * Version 3, 29 June 2007
 * 
 * Copyright (C) 2010, vlad
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package httpanalyzer;

import java.util.Properties;
import javax.swing.JFrame;

/**
 *
 * @author vlad
 */
public class HttpPreference {

    /**
     * Get values from work form and set properties
     * Then call method FileUtil.savePreferenceToFile
     * @param mainView HttpAnalyzerView
     * @param parentFrame JFrame
     */
    public void savePreference(HttpAnalyzerView analyseView, JFrame parentFrame,
            String notes) {
        Properties properties = new Properties();
        if (notes != null) {
            properties.setProperty("httpanalyzer.notes", notes);
        }
        properties.setProperty("httpanalyzer.tools.options.merge", Boolean.toString(analyseView.mergeInfoCheckBox.isSelected()));
        if (analyseView.proxyNoneRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.proxytype", "None");
        } else if (analyseView.proxyHttpRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.proxytype", "Http");
        } else {
            properties.setProperty("httpanalyzer.options.proxytype", "Socks");
        }
        properties.setProperty("httpanalyzer.options.proxy",
                analyseView.proxyField.getText());
        properties.setProperty("httpanalyzer.options.proxyauth",
                Boolean.toString(analyseView.proxyAuthCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.proxyuser",
                analyseView.proxyUserTextField.getText());
        properties.setProperty("httpanalyzer.options.proxypassword",
                new String(analyseView.proxyPassPasswordField.getPassword()));
        // properties.setProperty("httpanalyzer.options.sockslevel", );
        properties.setProperty("httpanalyzer.options.httpversion",
                analyseView.httpVersionComboBox.getSelectedItem().toString());
        properties.setProperty("httpanalyzer.options.httpauth",
                Boolean.toString(analyseView.httpAuthCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.httpauthuser",
                analyseView.httpUserTextField.getText());
        properties.setProperty("httpanalyzer.options.httpauthpassword",
                new String(analyseView.httpPassPasswordField.getPassword()));
        properties.setProperty("httpanalyzer.options.customrefer.enable",
                Boolean.toString(analyseView.customRefererCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.customrefer",
                analyseView.customRefererTextField.getText());
        properties.setProperty("httpanalyzer.options.customcookie.enable",
                Boolean.toString(analyseView.customCookieCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.customcookie",
                analyseView.customCookieTextField.getText());
        if (analyseView.firefoxRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "0");
        } else if (analyseView.ie6RadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "1");
        } else if (analyseView.ie7RadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "2");
        } else if (analyseView.ie8RadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "3");
        } else if (analyseView.googleBotRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "4");
        } else if (analyseView.msnRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "5");
        } else if (analyseView.yahooRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "6");
        } else if (analyseView.iPhoneRadioButton.isSelected()) {
            properties.setProperty("httpanalyzer.options.agents", "7");
        }
        if (analyseView.getRadio.isSelected()) {
            properties.setProperty("httpanalyzer.options.method", "GET");
        } else if (analyseView.postRadio.isSelected()) {
            properties.setProperty("httpanalyzer.options.method", "POST");
        } else {
            properties.setProperty("httpanalyzer.options.method", "HEAD");
        }
        properties.setProperty("httpanalyzer.options.tofile.enable",
                Boolean.toString(analyseView.toFileCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.tofile",
                analyseView.toFileTextField.getText());
        properties.setProperty("httpanalyzer.options.urlcombo.elements",
                Integer.toString(analyseView.urlCombo.getItemCount()));
        for (int i = 0; i < analyseView.urlCombo.getItemCount(); i++) {
            properties.setProperty("httpanalyzer.options.urlcombo." + Integer.toString(i),
                    analyseView.urlCombo.getItemAt(i).toString());
        }
        properties.setProperty("httpanalyzer.options.params.elements",
                Integer.toString(analyseView.paramsComboBox.getItemCount()));
        for (int i = 0; i < analyseView.paramsComboBox.getItemCount(); i++) {
            properties.setProperty("httpanalyzer.options.params." + Integer.toString(i),
                    analyseView.paramsComboBox.getItemAt(i).toString());
        }
        properties.setProperty("httpanalyzer.options.usecustom.headers",
                Boolean.toString(analyseView.useCustomHeadersCheckBox.isSelected()));
        properties.setProperty("httpanalyzer.options.custom.headers",
                analyseView.customHeaders.getText());
        properties.setProperty("httpanalyzer.options.intelligencemode",
                Boolean.toString(analyseView.intelSaveCheckBox.isSelected()));
        HttpFileUtils fileUtil = new HttpFileUtils();
        fileUtil.savePreferenceToFile(properties, parentFrame);
    }

    /**
     * Get values from work form and set properties
     * Then call method FileUtil.savePreferenceToFile
     * @param mainView HttpAnalyzerView
     * @param parentFrame JFrame
     * @return String Notes
     */
    public String  loadPreference(HttpAnalyzerView mainView, JFrame parentFrame) {
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

        int ii =Integer.decode(properties.getProperty("httpanalyzer.options.urlcombo.elements"));
        mainView.urlCombo.removeAllItems();
        for (int i = 0; i < ii; i++) {
            String el = properties.getProperty("httpanalyzer.options.urlcombo." + Integer.toString(i));
            mainView.urlCombo.addItem(el);
        }

        ii =Integer.decode(properties.getProperty("httpanalyzer.options.params.elements"));
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
