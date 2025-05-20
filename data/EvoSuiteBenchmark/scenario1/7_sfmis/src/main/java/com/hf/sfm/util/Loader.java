package com.hf.sfm.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class Loader {

    public List loadDataWithSql() {
        session = HibernateSessionFactory.currentSession();
        this.getQuerySql();
        if (sort != null && sort != "") {
            if (this.sql.indexOf("order by") > 0) {
                this.sql = this.sql.substring(0, this.sql.indexOf("order by")) + " order by " + sort + " " + dir;
            } else {
                this.sql = this.sql + " order by " + sort + " " + dir;
            }
        }
        query = session.createSQLQuery(this.sql);
        if (pas != null) {
            query = this.getParams(query, pas);
        }
        if (paging) {
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        List rows = query.list();
        HibernateSessionFactory.closeSession();
        this.setRs(rows);
        return rows;
    }
}
