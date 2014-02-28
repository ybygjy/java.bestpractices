package org.ybygjy.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * xml文件解析
 * @author WangYanCheng
 * @version 2010-5-7
 */
public class XMLParserTest {

    /**
     * 解析字符串为xml
     * @param xmlSrc xmlSrc
     */
    public void parserStr2XML(String xmlSrc) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            InputStream ins = new FileInputStream(new File("c:\\1"));
            ParserXML4Sax pxsInst = new ParserXML4Sax();
            saxParser.parse(ins, pxsInst);
            List<Map> rtnList = pxsInst.getRtnList();
            for (Iterator<Map> iterator = rtnList.iterator(); iterator.hasNext();) {
                Map mapInst = iterator.next();
                /*for (Iterator<Map.Entry> mapIterator = mapInst.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry entry = mapIterator.next();
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }*/
                doPrint(mapInst);
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * 使用dom方式解析xml
     * @param file fileInst
     */
    public void parserXMLUsedDom(File file) {
        DocumentBuilder dbInst = null;
        Document docInst = null;
        try {
            dbInst = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            docInst = dbInst.parse(file);
        } catch (SAXException saxe) {
            saxe.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Element elem = docInst.getDocumentElement();
        System.out.println("ServiceName:" + elem.getAttribute("SERVICENAME"));
        NodeList nodeList = elem.getElementsByTagName("Task");
        int len = nodeList.getLength();
        for (int index = 0; index < len; index++) {
            Node tmpNode = nodeList.item(index);
            NamedNodeMap nnmInst = tmpNode.getAttributes();
            doPrint(nnmInst);
        }
    }
    /**
     * doPrint
     * @param mapInst mapInst
     */
    public void doPrint(Map mapInst) {
        for (Iterator<Map.Entry> iterator = mapInst.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = iterator.next();
            System.out.println(entry);
        }
    }
    /**
     * 工具方法,打印
     * @param nnm nnm
     */
    public void doPrint(NamedNodeMap nnm) {
        int len = nnm.getLength();
        for (int index = 0; index < len; index++) {
            Node tmpNode = nnm.item(index);
            System.out.println(tmpNode.getNodeName() + ":" + tmpNode.getNodeValue() + ":" + tmpNode.getNodeType());
        }
    }
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        XMLParserTest xmlTest = new XMLParserTest();
//        StringBuilder sbuilder = new StringBuilder();
//        // sbuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
//        sbuilder.append("<TaskList serviceName=\"task\">");
//        sbuilder.append("<Task PKCODE=\"110\" NAME=\"B-208 油浆泵前轴承\" CHECKTYPE=\"2\" PERSONPKCODE=\"dongzhiyi\" />");
//        sbuilder.append("</TaskList>");
        xmlTest.parserStr2XML("");
//        xmlTest.parserXMLUsedDom(new File("C:\\12"));
    }

    /**
     * XML解析方式之SAX
     * @author WangYanCheng
     * @version 2010-5-7
     */
    class ParserXML4Sax extends DefaultHandler {
        /**rtnList*/
        private List<Map> rtnList = new ArrayList();
        /**
         * rtnList
         * @return rtnList
         */
        public List<Map> getRtnList() {
            return rtnList;
        }
        /**
         * 文档读取开始<br/>
         * {@inheritDoc}
         */
        public void startDocument() throws SAXException {
            super.startDocument();
        }
        /**
         * 文档读取开始<br/>
         * {@inheritDoc}
         */
        public void endDocument() throws SAXException {
            super.endDocument();
        }
        /**
         * 读取元素开始<br/>
         * {@inheritDoc}
         */
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if ("ROOT".equals(qName)) {
                return;
            }
            Map tmpMap = new HashMap();
            int attrLen = attributes.getLength();
            for (int index = 0; index < attrLen; index++) {
                String key = null, value = null;
                key = attributes.getLocalName(index);
                value = attributes.getValue(index);
                tmpMap.put(key, value);
            }
            rtnList.add(tmpMap);
            System.out.println(rtnList.size());
        }
        /**
         * 读取元素结束<br/>
         * {@inheritDoc}
         */
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }
    }
}
