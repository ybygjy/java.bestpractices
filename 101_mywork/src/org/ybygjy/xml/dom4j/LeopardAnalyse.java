package org.ybygjy.xml.dom4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * dom4j 学习
 * @author WangYanCheng
 * @version 2010-8-3
 */
public class LeopardAnalyse {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        try {
            Dom4JBasic dom4JInst =
                new Dom4JBasic(new File("D:\\work\\workspace\\mywork\\src\\org\\ybygjy\\xml\\leopard.xml"));
            dom4JInst.setAopImpInst(new AOPListener() {
                public void beforeParseElement(Element elemInst) {
                }
                public void afterParseElement(Element elemInst) {
                    if (elemInst.isRootElement()) {
                        return;
                    }
                    Iterator iterator = elemInst.nodeIterator();
                    for (; iterator.hasNext();) {
                        Node nodeInst = (Node) iterator.next();
                        System.out.println(nodeInst.asXML());
                    }
                }
            });
            dom4JInst.parseXml4Iterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 学习dom4j基础
 * @author WangYanCheng
 * @version 2010-8-3
 */
class Dom4JBasic {
    /**处理事件AOP*/
    private AOPListener aopImpInst = null;
    /**inner document instance*/
    private Document docInst = null;
    /**
     * Constructor
     */
    public Dom4JBasic() {
    }
    /**
     * Constructor
     * @param fileInst fileInst
     * @throws DocumentException DocumentException
     */
    public Dom4JBasic(File fileInst) throws DocumentException {
        docInst = parse(fileInst);
    }
    /**
     * Constructor
     * @param xmlStr 符合xml格式的字符串
     * @throws DocumentException DocumentException
     */
    public Dom4JBasic(String xmlStr) throws DocumentException {
        docInst = DocumentHelper.parseText(xmlStr);
    }
    /**
     * 解析给定xml模式的文件
     * @param fileInst xml模式的文件
     * @return docInst docInst
     * @throws DocumentException DocumentException
     */
    public Document parse(File fileInst) throws DocumentException {
        Document docInst = null;
        SAXReader saxRInst = new SAXReader();
        saxRInst.setIgnoreComments(true);
        saxRInst.setMergeAdjacentText(true);
        saxRInst.setStringInternEnabled(false);
        saxRInst.setStripWhitespaceText(true);
        docInst = saxRInst.read(fileInst);
        return docInst;
    }
    /**
     * 解析操作xml文件内容
     * @param docInst 文件实例
     */
    public void parseXml4Iterator(Document docInst) {
        boolean aopListFlag = null == this.aopImpInst ? false : true;
        StringBuilder sbuStr = new StringBuilder();
        Element rootElemInst = docInst.getRootElement();
        if (aopListFlag) {
            this.aopImpInst.beforeParseElement(rootElemInst);
        }
        for (Iterator iterator = rootElemInst.elementIterator(); iterator.hasNext();) {
            Element tmpElem = (Element) iterator.next();
            if (aopListFlag) {
                this.aopImpInst.beforeParseElement(tmpElem);
            }
            sbuStr
            .append("getName:" + tmpElem.getName())
            .append("\tQName:" + tmpElem.getQName().getName())
            .append("\tattributeCount:" + tmpElem.attributeCount())
            .append("\tnodeCount:" + tmpElem.nodeCount())
            .append("\n");
            if (aopListFlag) {
                this.aopImpInst.afterParseElement(tmpElem);
            }
        }
        System.out.println(sbuStr);
    }
    /**
     * 解析xml文件实体内容
     */
    public void parseXml4Iterator() {
        if (null != this.docInst) {
            this.parseXml4Iterator(docInst);
        }
    }
    /**
     * 解析操作xml文件内容,使用XPath
     * @param docInst xml文件实例
     */
    public void parseXml4Xpath(Document docInst) {
        StringBuilder sbuStr = new StringBuilder();
        List<Node> listElem = docInst.selectNodes("//leopard/config-item");
        for (Node nodeInst : listElem) {
            Node itemName = nodeInst.selectSingleNode("item-name");
            sbuStr
            .append("getName:" + itemName.getName())
            .append("getText:" + itemName.getText())
            .append("\n");
        }
        System.out.println(sbuStr);
    }
    /**
     * 解析操作xml文件内容,使用XPath
     */
    public void parseXml4Xpath() {
        if (null != this.docInst) {
            this.parseXml4Xpath(this.docInst);
        }
    }
    /**
     * 创建文档实例
     * @return docInst docInst
     */
    public Document createDocument() {
        Document docInst = DocumentHelper.createDocument();
        Element rootElem = docInst.addElement("leopard");
        Element itemElem = rootElem.addElement("conf-item");
        itemElem.addElement("item-name").addText("nTimeOut");
        itemElem.addElement("item-value").addText("15");
        itemElem = rootElem.addElement("conf-item");
        itemElem.addElement("item-name").addText("nRetry");
        itemElem.addElement("item-value").addText("5");
        return docInst;
    }
    /**
     * 持久存储xml文档
     * @param docInst xml文档
     */
    public void saveDocument(Document docInst) {
        try {
            XMLWriter xmlWriteInst = new XMLWriter(new FileWriter("Dom4jFormatSave.xml"));
            xmlWriteInst.write(docInst);
            xmlWriteInst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * prettyPrint
     * @param docInst 文档实例
     */
    public void prettyPrint(Document docInst) {
        OutputFormat formatInst = OutputFormat.createPrettyPrint();
        XMLWriter xmlWInst;
        try {
            xmlWInst = new XMLWriter(System.out, formatInst);
            xmlWInst.write(docInst);
            xmlWInst.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * compactPrint
     * @param docInst 文档实例
     */
    public void compactPrint(Document docInst) {
        OutputFormat formatInst = OutputFormat.createCompactFormat();
        XMLWriter xmlWInst;
        try {
            xmlWInst = new XMLWriter(System.out, formatInst);
            xmlWInst.write(docInst);
            xmlWInst.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * 快速持久存储xml文档
     * @param docInst xml文档实例
     */
    public void quickSaveDocument(Document docInst) {
        FileWriter fwInst;
        try {
            fwInst = new FileWriter("Dom4jQuickSave.xml");
            docInst.write(fwInst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Converting to String
     * @return rtnStr xmlStr
     */
    public String convertDomInst2Str() {
        return this.docInst.asXML();
    }
    /**
     * 装配aop机制实现实例
     * @param aopImpInst aop实现实例
     */
    public void setAopImpInst(AOPListener aopImpInst) {
        this.aopImpInst = aopImpInst;
    }
}
