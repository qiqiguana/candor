package httpanalyzer;

import java.util.Properties;
import javax.swing.JFrame;

/**
 * @author vlad
 */
public class HttpPreference {

    /**
     * Get values from work form and set properties
     * Then call method FileUtil.savePreferenceToFile
     * @param mainView HttpAnalyzerView
     * @param parentFrame JFrame
     */
    public void savePreference(HttpAnalyzerView analyseView, JFrame parentFrame, String notes);

    /**
     * Get values from work form and set properties
     * Then call method FileUtil.savePreferenceToFile
     * @param mainView HttpAnalyzerView
     * @param parentFrame JFrame
     * @return String Notes
     */
    public String loadPreference(HttpAnalyzerView mainView, JFrame parentFrame);
}
