package httpanalyzer;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class HttpAnalyzerApp extends SingleFrameApplication {

    public static HttpAnalyzerApp getApplication() {
        return Application.getInstance(HttpAnalyzerApp.class);
    }
}
