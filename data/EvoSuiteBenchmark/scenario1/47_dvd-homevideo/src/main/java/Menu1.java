import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class creates a background menu for a DVD.  The user
 * can specify a background picture and audio to use for the
 * menu, or a default background can also be used.  The user
 * also has the option of specifying unique titles for each
 * chapter of the DVD.  If nothing is specified then the title
 * of the DVD will be used followed by an underscore and increasing
 * integer for each video clip.
 *
 * TODO - Can I use streams on dvd-menu?  Really need to standardize
 *        how I use streams across the board for all classes.
 */
public class Menu implements Runnable {

    public boolean createXML() {
        File xmlFile = new File(m_GUI.strOutputDir + "/vmgm.xml");
        File ListDir = new File(m_GUI.strOutputDir);
        /* 
         * Need to find all vob files, but don't want to include menu.vob
         * which was created by dvd-menu.
         */
        FilenameFilter filter = new FilenameFilter() {

            boolean ret_val = true;

            public boolean accept(File dir, String name) {
                if (name.endsWith("vob")) {
                    if (!name.equals("menu.vob"))
                        ret_val = true;
                    else
                        ret_val = false;
                } else
                    ret_val = false;
                return ret_val;
            }
        };
        video_files = ListDir.list(filter);
        /* Write the xml file.  It's not pretty, but it works. */
        int i;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
            writer.write("<dvdauthor dest=\"" + m_GUI.strOutputDir + "/DVD/\" jumppad=\"0\">");
            writer.newLine();
            writer.write("\t<vmgm>");
            writer.newLine();
            writer.write("\t\t<menus>");
            writer.newLine();
            writer.write("\t\t\t<pgc entry=\"title\" >");
            writer.newLine();
            writer.write("\t\t\t\t<vob file=\"" + m_GUI.strOutputDir + "/menu.vob\" pause=\"inf\" />");
            writer.newLine();
            if (titles[0] == null) {
                for (i = 1; i <= video_files.length; i++) {
                    writer.write("\t\t\t\t<button>jump title " + i + ";</button>");
                    writer.newLine();
                }
            } else {
                for (i = 1; i < titles.length; i++) {
                    if (!titles[i - 1].equals("BLANK") && !titles[i - 1].equals("DONE")) {
                        writer.write("\t\t\t\t<button>jump title " + i + ";</button>");
                        writer.newLine();
                    } else if (titles[i - 1].equals("DONE"))
                        break;
                }
            }
            writer.write("\t\t\t\t<post> jump vmgm menu 1; </post>");
            writer.newLine();
            writer.write("\t\t\t</pgc>");
            writer.newLine();
            writer.write("\t\t</menus>");
            writer.newLine();
            writer.write("\t</vmgm>");
            writer.newLine();
            writer.write("\t<titleset>");
            writer.newLine();
            writer.write("\t<titles>");
            writer.newLine();
            for (i = 1; i < video_files.length; i++) {
                writer.write("\t\t<pgc>");
                writer.newLine();
                writer.write("\t\t\t<vob file=\"" + video_files[i - 1] + "\" />");
                writer.newLine();
                writer.write("\t\t\t<post>jump title " + (i + 1) + ";</post>");
                writer.newLine();
                writer.write("\t\t</pgc>");
                writer.newLine();
            }
            writer.write("\t\t<pgc>");
            writer.newLine();
            writer.write("\t\t\t<vob file=\"" + video_files[i - 1] + "\" />");
            writer.newLine();
            writer.write("\t\t\t<post>call vmgm menu 1;</post>");
            writer.newLine();
            writer.write("\t\t</pgc>");
            writer.newLine();
            writer.write("\t</titles>");
            writer.newLine();
            writer.write("\t</titleset>");
            writer.newLine();
            writer.write("</dvdauthor>");
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
            m_GUI.MessageBox(baseErr + "IO Error\n" + ex.toString(), 0);
            ex.printStackTrace();
            return true;
        }
        /* Shouldn't expect to see any errors from this method call. */
        return false;
    }
}
