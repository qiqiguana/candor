package de.huxhorn.lilith.swing;

import de.huxhorn.lilith.Lilith;
import de.huxhorn.lilith.LilithSounds;
import de.huxhorn.lilith.data.access.HttpStatus;
import de.huxhorn.lilith.data.logging.LoggingEvent;
import de.huxhorn.lilith.swing.filefilters.GroovyConditionFileFilter;
import de.huxhorn.lilith.swing.preferences.SavedCondition;
import de.huxhorn.lilith.swing.table.ColorScheme;
import de.huxhorn.lilith.swing.table.model.PersistentTableColumnModel;
import de.huxhorn.sulky.conditions.Condition;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.*;

public class ApplicationPreferences {

    public static byte[] getMD5(InputStream input) {
        if (input == null) {
            return null;
        }
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            for (; ; ) {
                int read = input.read(buffer);
                if (read < 0) {
                    break;
                }
                messageDigest.update(buffer, 0, read);
            }
            return messageDigest.digest();
        } catch (Throwable t) {
            final Logger logger = LoggerFactory.getLogger(ApplicationPreferences.class);
            if (logger.isWarnEnabled())
                logger.warn("Exception while calculating checksum!", t);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return null;
    }
}
