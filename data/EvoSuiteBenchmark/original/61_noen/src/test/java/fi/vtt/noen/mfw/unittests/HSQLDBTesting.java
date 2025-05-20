/*
 * Copyright (C) 2010-2011 VTT Technical Research Centre of Finland.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package fi.vtt.noen.mfw.unittests;

import fi.vtt.noen.mfw.bundle.common.Const;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.BMDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ServerEvent;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.ProbeDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.TargetDescription;
import fi.vtt.noen.mfw.bundle.server.shared.datamodel.Value;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class HSQLDBTesting {
  public static void main_jdbc(String[] args) throws Exception {
    Class.forName("org.hsqldb.jdbcDriver");

    Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "sa", "");
    Statement st = conn.createStatement();
    st.executeUpdate("create table BM (BM_ID integer not null, BM_NAME varchar(32) not null, primary key (BM_ID))");
    PreparedStatement ps = conn.prepareStatement("insert into BM values (?, ?)");
    ps.setInt(1, 1);
    ps.setString(2, "bob");
    ps.executeUpdate();

    ResultSet rs = st.executeQuery("select * from BM");
    while (rs.next()) {
      int id = rs.getInt(1);
      String name = rs.getString(2);
      System.out.println("results:" + id + "," + name);
    }
  }

  public static void main(String[] args) {
    Configuration config = new Configuration();
    config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
    config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/mfw_db");
    config.setProperty("hibernate.connection.username", "root");
    config.setProperty("hibernate.connection.password", "");
    config.setProperty("hibernate.connection.pool_size", "5");
    config.setProperty("hibernate.connection.autocommit", "true");
    config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
    config.setProperty("hibernate.id.new_generator_mappings", "true");
    //create/valide db schema on session creation?
//    config.setProperty("hibernate.hbm2ddl.auto", "create");
    config.setProperty("hibernate.hbm2ddl.auto", "update");
    //show sql on console
    config.setProperty("hibernate.show_sql", "true");
    config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
    config.setProperty("hibernate.current_session_context_class", "thread");

    // Add your mapped classes here:
    config.addAnnotatedClass(BMDescription.class);
    config.addAnnotatedClass(Value.class);
    config.addAnnotatedClass(ServerEvent.class);
    config.addAnnotatedClass(TargetDescription.class);
    config.addAnnotatedClass(ProbeDescription.class);

    SessionFactory sf =config.buildSessionFactory();
    Session session = sf.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    transaction.begin();
//    BMDescription bm = new BMDescription();
    //setting the id is pointless as hibernate will just manage it..
    BMDescription bm = new BMDescription(null, "myclass", "myname", "bmdesc");
    Value v = new Value(bm, 2, "11", System.currentTimeMillis());

    session.save(bm);
    session.save(v);
    transaction.commit();
    //session is automatically closed on transaction commit(what is the point of session/transaction separation then?)
//    session.close();

  }

  /*
  public static void main_jpa_store(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mfw-persistence-manager");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    BMDescription bm = new BMDescription("myclass", "myname", "ttype", "tname", "bmdesc", 2);
    Value v = new Value(bm, 2, 11, System.currentTimeMillis());
    em.persist(bm);
    em.persist(v);

    transaction.commit();
    em.close();
  }
*/
  public static void main__(String[] args) {
    HSQLDBTesting testing = new HSQLDBTesting();
    Map<String, String> props = new HashMap<String, String>();
    props.put(Const.PROBE_BM_CLASS, "bmclass");
    props.put(Const.PROBE_BM_NAME, "bmname");
    props.put(Const.PROBE_NAME, "probe name");
    props.put(Const.PROBE_TARGET_TYPE, "target type");
    props.put(Const.PROBE_TARGET_NAME, "target name");
    props.put(Const.PROBE_BM_DESCRIPTION, "bm description");
    props.put(Const.PROBE_PRECISION, "1");
    ProbeDescription pd = testing.createProbeDescription(props);
//    BMDescription bm = testing.createBMDescription(props);
//    ProbeDescription pd = testing.createProbeDescription(props);
    System.out.println("bmid:"+pd.getBm().getBmId()+" bm:"+pd.getBm());

  }

  /**
   * Creates a ProbeDescription for the given information. Checks the DB for an existing suitable description.
   * If none is found, a new one is created and stored into the database. Whichever succeeds, the result is returned.
   *
   * @param properties  The information describing the probe.
   * @return The ProbeDescription object matching the given information.
   */
  public ProbeDescription createProbeDescription(Map<String, String> properties) {
    TargetDescription target = createTargetDescription(properties);
    BMDescription bm = createBMDescription(properties);
    //if (true) return null;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mfw-persistence-manager");
    EntityManager em = emf.createEntityManager();

    Query query = em.createQuery("select distinct pd from ProbeDescription pd where pd.probeName = :pname and pd.target.targetName = :tname "+
            "and pd.target.targetType = :ttype and pd.bm.bmClass = :bmClass and pd.bm.bmName = :bmName");
    String probeName = properties.get(Const.PROBE_NAME);
    String targetName = properties.get(Const.PROBE_TARGET_NAME);
    String targetType = properties.get(Const.PROBE_TARGET_TYPE);
    String bmClass = properties.get(Const.PROBE_BM_CLASS);
    String bmName = properties.get(Const.PROBE_BM_NAME);
    query.setParameter("pname", probeName);
    query.setParameter("tname", targetName);
    query.setParameter("ttype", targetType);
    query.setParameter("bmClass", bmClass);
    query.setParameter("bmName", bmName);

    List<ProbeDescription> resultList = query.getResultList();
    assert resultList.size() <= 1 : "There should be maximum of one probe description in the database with unique probe name, target type, target name, bm class and bm name."+
            " had "+resultList.size()+" for {"+probeName+","+targetType+","+targetName+","+bmClass+","+bmName+"}";
    if (resultList.size() == 1) {
      return resultList.get(0);
    }
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    ProbeDescription probe = new ProbeDescription(properties, target, bm);
    System.out.println("probe bmid:" + probe.getBm().getBmId());
    em.persist(probe);
    transaction.commit();
    em.close();
    return probe;
  }

  /**
   * Retrieves a BMDescription for the given properties. If one is found in the database, it is
   * returned. If not, a new one is created, stored into the database, and returned. The relevant values are
   * target name, target type, bm class, and bm name.
   *
   * @param properties Information for the BMDescription to be created.
   * @return The BMDescription matching the given arguments.
   */
  public BMDescription createBMDescription(Map<String, String> properties) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mfw-persistence-manager");
    EntityManager em = emf.createEntityManager();
    Query query = em.createQuery("select distinct bm from BMDescription bm where bm.target.targetName = :tname "+
            "and bm.target.targetType = :ttype and bm.bmClass = :bmClass and bm.bmName = :bmName");
    //todo:remove these calls and cache locally, or use registry to get them
    TargetDescription target = createTargetDescription(properties);
    String targetName = target.getTargetName();
    String targetType = target.getTargetType();
    String bmClass = properties.get(Const.PROBE_BM_CLASS);
    String bmName = properties.get(Const.PROBE_BM_NAME);
    String bmDescription = properties.get(Const.PROBE_BM_DESCRIPTION);
    query.setParameter("tname", targetName);
    query.setParameter("ttype", targetType);
    query.setParameter("bmClass", bmClass);
    query.setParameter("bmName", bmName);

    List<BMDescription> resultList = query.getResultList();
    assert resultList.size() <= 1 : "There should be maximum of one BM description in the database with unique target type, target name, bm class and bm name."+
            " had "+resultList.size()+" for {"+targetType+","+targetName+","+bmClass+","+bmName+"}";
    if (resultList.size() == 1) {
      System.out.println("bm found");
      return resultList.get(0);
    }
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    BMDescription bm = new BMDescription(target, bmClass, bmName, bmDescription);
    em.persist(bm);
    transaction.commit();
    em.close();
    System.out.println("bm saved "+bm.getBmId());
    return bm;
  }

  public TargetDescription createTargetDescription(Map<String, String> properties) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("mfw-persistence-manager");
    EntityManager em = emf.createEntityManager();
    Query query = em.createQuery("select distinct t from TargetDescription t where t.targetName = :tname "+
            "and t.targetType = :ttype");
    String targetType = properties.get(Const.PROBE_TARGET_TYPE);
    String targetName = properties.get(Const.PROBE_TARGET_NAME);
    query.setParameter("tname", targetName);
    query.setParameter("ttype", targetType);

    List<TargetDescription> resultList = query.getResultList();
    assert resultList.size() <= 1 : "There should be maximum of one target description in the database with unique target name,and target type."+
            " had "+resultList.size()+" for {"+targetType+","+targetName+"}";
    if (resultList.size() == 1) {
      System.out.println("found target");
      return resultList.get(0);
    }
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    TargetDescription target = new TargetDescription(targetType, targetName);
    em.persist(target);
    transaction.commit();
    em.close();
    System.out.println("target saved");
    return target;
  }
}
