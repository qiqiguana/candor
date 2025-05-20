package com.hf.sfm.test;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLParse {
	public static void main(String[] args) {
		try {
			SAXReader reader = new SAXReader();
			File f = new File("src/com/hf/sfm/test/b.xml");
			Document doc = reader.read(f);
			Node node = doc.node(0);
			System.out.println("node:"+node.getName()+",nodecount:"+doc.nodeCount());
			Element root = doc.getRootElement();
			Element foo = null;
			for(Iterator it = root.elementIterator();it.hasNext();){
				foo = (Element) it.next();
				System.out.println("name:"+foo.getName());
				System.out.println("车牌号码："+foo.elementText("NO"));
				System.out.println("车主地址："+foo.elementText("ADDR"));
			}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
