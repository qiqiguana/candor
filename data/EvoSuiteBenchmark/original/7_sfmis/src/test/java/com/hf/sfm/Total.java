package com.hf.sfm.test;

import java.util.List;

/**
 * 将List转化为xml时getXmlFromList()，用来存放记录总数
 * @author Administrator
 *
 */
public class Total {
	
	private long totalNum;//记录总数
	private List resultList;//要转换的List,也是转换后的结果集名称

	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultsList) {
		this.resultList = resultsList;
	}
	
}
