package net.virtualinfinity.atrobots.gui;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * TODO: JavaDoc
 *
 * @author <a href='mailto:daniel.pitts@cbs.com'>Daniel Pitts</a>
 */
public class RobotFileUtils {
    public static FileNameExtensionFilter getAtRobotsFileNameFilter() {
        return new FileNameExtensionFilter("AT-Robots files", "at2", "ats");
    }

    static EntrantFile[] getFilesByName(java.util.List<String> initialRobots) {
        final java.util.List<EntrantFile> files = new ArrayList<EntrantFile>();
        boolean debug = false;
        for (String file : initialRobots) {
            if (!debug && "-d".equals(file)) {
                debug = true;
            } else {
                files.add(new EntrantFile(debug, robotFile(file)));
                debug = false;
            }
        }
        return files.toArray(new EntrantFile[files.size()]);
    }

    static EntrantFile[] getEntrantFiles(File[] initialRobots) {
        final java.util.List<EntrantFile> files = new ArrayList<EntrantFile>();
        for (File file : initialRobots) {
            files.add(new EntrantFile(false, file));
        }
        return files.toArray(new EntrantFile[files.size()]);
    }

    static File robotFile(String robotName) {
        final File file = new File(robotName);
        if (file.exists()) {
            return file;
        }
        for (File f : file.getParentFile().listFiles(new FilenameAt2Filter(robotName))) {
            return f;
        }
        return file;
    }

    static class EntrantFile {
        final boolean debug;
        final File file;

        EntrantFile(boolean debug, File file) {
            this.debug = debug;
            this.file = file;
        }
    }

    static class FilenameAt2Filter implements FilenameFilter {
        private final String robotName;

        public FilenameAt2Filter(String robotName) {
            this.robotName = robotName;
        }

        public boolean accept(File dir, String name) {
            return name.toLowerCase().equals(robotName + ".at2");
        }
    }
}
