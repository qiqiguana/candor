package com.eteks.sweethome3d.viewcontroller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import com.eteks.sweethome3d.model.LengthUnit;
import com.eteks.sweethome3d.model.TextureImage;
import com.eteks.sweethome3d.model.UserPreferences;

/**
 * A MVC controller for user preferences view.
 *
 * @author Emmanuel Puybaret
 */
public class UserPreferencesController implements Controller {

    /**
     * Returns <code>true</code> if language libraries can be imported.
     */
    public boolean mayImportLanguageLibrary() {
        return this.homeController != null;
    }
}
