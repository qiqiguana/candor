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
    public QueueDAO() {
    }

    public static final String TYPE_APPROVAL = new String("approval");
    public static final String TYPE_CATALOG = new String("catalog");

    private DataSource dataSource;
    private CommonDAO cd = new CommonDAO();
    private MetadataDAO md = new MetadataDAO();

    private static final String SELECT_QUEUE_BY_TYPE = "SELECT QueuedRecordId," +
            " MetadataId, Status, Comments FROM QueuedRecords WHERE Type LIKE ?";
    private static final String INSERT_QUEUE_ENTRY = "INSERT INTO QueuedRecords" +
            " (Type, MetadataId, Status, Comments) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUEUE_ENTRY = "UPDATE QueuedRecords" +
            " SET Type = ?, MetadataId = ?, Status = ?, Comments = ? WHERE" +
            " QueuedRecordId = ?";
    private static final String DELETE_QUEUE_ENTRY = "DELETE FROM QueuedRecords" +
            " WHERE QueuedRecordId = ?";

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        cd.setDataSource(dataSource);
        md.setDataSource(dataSource);
    }

    /**
     * @param type The type of QueuedRecordBeans to get.
     *
     * @return A List of QueuedRecordBeans.
     */
    public List<QueuedRecordBean> getQueueByType(final String type) {
        List<QueuedRecordBean> result = null;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            result = getQueue(type, conn);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * @return The List of QueuedRecordBeans representing the catalog queue.
     */
    public List<QueuedRecordBean> getCatalogQueue() {
        return getQueueByType(TYPE_CATALOG);
    }

    /**
     * @return The List of QueuedRecordBeans representing the approval queue.
     */
    public List<QueuedRecordBean> getApprovalQueue() {
        return getQueueByType(TYPE_APPROVAL);
    }

    public List<QueuedRecordBean> getQueue(final String type, final Connection conn) throws SQLException {
        final List<QueuedRecordBean> result = new ArrayList<QueuedRecordBean>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(SELECT_QUEUE_BY_TYPE);
            ps.setString(1, type);
            rs = ps.executeQuery();

            while(rs.next()) {
                final QueuedRecordBean queueMember = new QueuedRecordBean();
                queueMember.setQueuedRecordId(rs.getString(1));
                queueMember.getShortMetadata().setMetadataId(rs.getString(2));
                queueMember.setStatus(rs.getString(3));
                queueMember.setComments(rs.getString(4));
                result.add(queueMember);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(ps != null) {
                ps.close();
            }
        }

        for(QueuedRecordBean temp : result) {
            final String metadataId = temp.getShortMetadata().getMetadataId();
            // Here we're putting a MetadataBean into a ShortMetadataBean reference
            // because it's necessary for the queue display pages
            temp.setShortMetadata(md.getMetadata(metadataId));
        }

        return result;
    }

    /**
     * Saves a {@link QueuedRecordBean} to the database.
     *
     * @param queuedRecord A non-null QueuedRecordBean to save.
     *
     * @return <code>true</code> if the save is successful, false otherwise.
     */
    public boolean saveQueuedRecord(final QueuedRecordBean queuedRecord) {
        boolean result = false;

        if(null == queuedRecord) {
            throw new IllegalArgumentException("queuedRecord argument must be non-null");
        }
        if(null == queuedRecord.getType()) {
            throw new IllegalArgumentException("Cannot save a QueuedRecordBean without a type");
        }
        if(null == queuedRecord.getShortMetadata()
                || null == queuedRecord.getShortMetadata().getMetadataId()) {
            throw new IllegalArgumentException("Cannot save a queued record without a metadataId");
        }

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            saveQueuedRecord(queuedRecord, conn);
            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public void saveQueuedRecord(final QueuedRecordBean queuedRecord, final Connection conn) throws SQLException {
        final boolean isUpdate = null != queuedRecord.getQueuedRecordId();
        final String sql = isUpdate ? UPDATE_QUEUE_ENTRY : INSERT_QUEUE_ENTRY;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, queuedRecord.getType());
            ps.setString(2, queuedRecord.getShortMetadata().getMetadataId());
            ps.setString(3, queuedRecord.getStatus());
            ps.setString(4, queuedRecord.getComments());

            if(isUpdate) {
                ps.setString(5, queuedRecord.getQueuedRecordId());
            }

            ps.executeUpdate();
        } finally {
            if(null != ps) {
                ps.close();
            }
        }
    }

    public boolean enqueue(final String type, final String metadataId) {
        boolean result = false;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            final QueuedRecordBean temp = new QueuedRecordBean();
            temp.setType(type);
            temp.getShortMetadata().setMetadataId(metadataId);
            saveQueuedRecord(temp, conn);
            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean dequeue(final String queuedRecordId) {
        boolean result = false;
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            dequeue(queuedRecordId, conn);
            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public void dequeue(final String queuedRecordId, final Connection conn) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(DELETE_QUEUE_ENTRY);
            ps.setString(1, queuedRecordId);
            ps.executeUpdate();
        } finally {
            if(null != ps) {
                ps.close();
            }
        }
    }

    public boolean markRecordApproved(final String metadataId) {
        boolean result = false;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            cd.updateMetadataTimestampProperty("ApproveDate",
                    new Timestamp(System.currentTimeMillis()), metadataId, conn);
            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public boolean markRecordCataloged(final String metadataId) {
        boolean result = false;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();

            final CompleteMetadataBean cmb = md.getCompleteMetadata(metadataId, conn);
            final Date now = new Date();

            cmb.setCatalogDate(now);
            cmb.setPublicationDate(now);
            cmb.setPrivate(false);
            md.saveCompleteMetadata(cmb, conn);

            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public boolean rejectRecord(final String metadataId) {
        boolean result = false;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            cd.updateMetadataTimestampProperty("RejectDate",
                    new Timestamp(System.currentTimeMillis()), metadataId, conn);
            result = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(null != conn) {
                try {
                    conn.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
