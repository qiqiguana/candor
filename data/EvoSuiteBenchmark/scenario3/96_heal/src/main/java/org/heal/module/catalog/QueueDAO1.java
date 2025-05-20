package org.heal.module.catalog;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.util.CommonDAO;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DAO for managing queues stored in the database
 */
public class QueueDAO implements Serializable {

    /**
     * Saves a {@link QueuedRecordBean} to the database.
     *
     * @param queuedRecord A non-null QueuedRecordBean to save.
     * @return <code>true</code> if the save is successful, false otherwise.
     */
    public boolean saveQueuedRecord(final QueuedRecordBean queuedRecord);
}
