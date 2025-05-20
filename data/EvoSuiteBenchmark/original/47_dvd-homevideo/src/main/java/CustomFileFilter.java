import java.io.File;

/**
 *
 * @author Shane Santner
 * This class is used to instantiate a custom file filter.
 */

public class CustomFileFilter extends javax.swing.filechooser.FileFilter 
{
    private String m_FileExtension;
    
    /**
     * Creates a default instance of CustomFileFilter 
     */
    public CustomFileFilter(){}
    
    /**
     * Creates an instance of CustomFileFilter with the user
     * supplied String type as the file extension type
     * @param   type    file type to filter 
     */
    public CustomFileFilter(String type)
    {
        m_FileExtension = type;        
    }
    
    /*
     * Checks if the file ends with the extension that was passed
     * to the constructor
     * @param   file    The file which is to be filtered
     * @return  A boolean indicating if the file extension matches the 
     *          value passed to the constructor
     */
    public boolean accept(File file) 
    {
        String filename = file.getName();        
        return filename.endsWith(m_FileExtension);
    }
    
    /*
     * Returns the file type extension that will be filtered
     * @return  The file extension type passed to the constructor.
     */
    public String getDescription() 
    {
        return m_FileExtension;
    }
}