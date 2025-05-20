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

    private String sql;

    private String filepath;

    private int start;

    private int limit;

    private int totalCount;

    private String[] colNames;

    private String[][] pas;

    private String sort;

    private String dir;

    private final String XMLPATH = "sqlfolder/";

    private Session session = null;

    private Query query;

    private List rs;

    private boolean paging;

    private ListRange range;

    private String querySql;

    private String queryValue;

    public ListRange getRange();

    public void setRange(ListRange range);

    public List getRs();

    public void setRs(List rs);

    public int getTotalCount();

    public void setTotalCount(int totalCount);

    public String[] getColNames();

    public void setColNames(String[] colNames);

    public void run(BasePara basePara);

    /**
     * 从xml解析sql
     */
    public void parseXML();

    /**
     * 获取总记录数
     */
    public void getCount();

    /**
     * 获取sql中的字段名集合
     */
    public void getColsName();

    /**
     * 查询数据
     */
    public List loadDataWithSql();

    public void getQuerySql();

    /**
     * 接收参数
     */
    public Query getParams(Query query, String[][] params);

    /**
     * 首页Tree数据
     */
    public String getArrayResults();

    public void collectToMap(String flag);

    public void collectToMap();
}
