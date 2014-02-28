package org.ybygjy.xml.dom4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 负责解析[故障查询.xml]元素属性转换为element
 * @author WangYanCheng
 * @version 2010-11-18
 */
public class Attribute2Elem {
    /** docInst */
    public Document doc;
    /** outDoc */
    public Document outDoc;
    /** rootElem */
    public Element rootElem;
    /** saveXML */
    public File saveXml;

    /**
     * Constructor
     * @param fileInst fileInst
     * @param saveXML saveXML
     * @throws DocumentException DocumentException
     */
    public Attribute2Elem(File fileInst, File saveXML) throws DocumentException {
        this.saveXml = saveXML;
        doc = parse(fileInst);
        outDoc = createDom();
    }

    /**
     * 解析给定xml模式的文件
     * @param fileInst xml模式的文件
     * @return docInst docInst
     * @throws DocumentException DocumentException
     */
    private Document parse(File fileInst) throws DocumentException {
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
     * 创建文档实例
     * @return domInst domInst
     */
    public Document createDom() {
        Document docInst = DocumentHelper.createDocument();
        this.rootElem = docInst.addElement("rootElem");
        return docInst;
    }

    /**
     * 持久存储xml文档
     */
    public void saveDocument() {
        try {
            XMLWriter xmlWriteInst = new XMLWriter(new FileOutputStream(saveXml));
            xmlWriteInst.write(outDoc);
            xmlWriteInst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换
     */
    public void doConvert() {
        Element elem = doc.getRootElement();
        Iterator<Element> iterator = elem.elementIterator();
        innerConvert(iterator);
        doGetColumn();
        doGetColumn4Path();
        saveDocument();
    }

    /**
     * innerConvert
     * @param iterator iterator
     */
    private void innerConvert(Iterator<Element> iterator) {
        for (; iterator.hasNext();) {
            Element tmpElem = iterator.next();
            Iterator attIterator = tmpElem.attributeIterator();
            createElement(tmpElem.getName(), attIterator);
        }
    }
    /**
     * 提取列
     */
    private void doGetColumn() {
        List<Element> elemArr = rootElem.elements("ROW");
        Element elem = elemArr.get(0);
        List<Element> childElemArr = elem.elements();
        for (Iterator<Element> iterator = childElemArr.iterator(); iterator.hasNext();) {
            Element tmpElem = iterator.next();
            System.out.println(tmpElem.getName() + "\t\t\t" + tmpElem.getTextTrim());
        }
    }
    /**
     * 提取节点，使用XPATH方式
     */
    private void doGetColumn4Path() {
        Element element = (Element) rootElem.selectSingleNode("ROW");
        Element tmpElem = (Element) element.selectSingleNode("故障编号");
System.out.println(tmpElem.getName() + ":" + tmpElem.getStringValue());
    }
    /**
     * createElement
     * @param tmpElem elementName
     * @param attIterator attributeIterator
     */
    private void createElement(String tmpElem, Iterator attIterator) {
        Element elem = DocumentHelper.createElement(tmpElem);
        for (; attIterator.hasNext();) {
            Attribute tmpAtt = (Attribute) attIterator.next();
            elem.addElement(tmpAtt.getName()).add(DocumentHelper.createCDATA(tmpAtt.getValue()));
        }
        rootElem.add(elem);
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        File outFile = new File("src\\org\\ybygjy\\xml\\故障信息.xml");
        File inFile = new File("src\\org\\ybygjy\\xml\\故障查询.xml");
        try {
            Attribute2Elem att2Elem = new Attribute2Elem(inFile, outFile);
            att2Elem.doConvert();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
