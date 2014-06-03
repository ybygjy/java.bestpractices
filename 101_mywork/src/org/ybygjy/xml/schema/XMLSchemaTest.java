package org.ybygjy.xml.schema;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * 实践XML Schema
 * <p>1、通过DOM4J加载XSD验证XML
 * <p>2、http://lavasoft.blog.51cto.com/62575/97597/
 * @author WangYanCheng
 * @version 2014-5-27
 */
public class XMLSchemaTest {
    private File xmlFile = null;
    private File xsdFile = null;
    public XMLSchemaTest(File xmlFile, File xsdFile) {
        this.xmlFile = xmlFile;
        this.xsdFile = xsdFile;
    }
    public void validateByDTD() {
    }
    public void validateByXSD() {
        //XML错误处理器
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        //基于SAX的解析器
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        //指定解析器在解析文档时验证文档内容
        saxParserFactory.setValidating(true);
        //指定解析器对XML名称空间支持
        saxParserFactory.setNamespaceAware(true);
        //使用工厂解析器创建SAXParser实例
        SAXParser saxParser = null;
        try {
            saxParser = saxParserFactory.newSAXParser();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //创建文档读取实例
        SAXReader saxReader = new SAXReader();
        //定义xml文档实例
        Document xmlDocument = null;
        try {
            xmlDocument = saxReader.read(xmlFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            //设置XMLReader的基础实现中的特定属性值。
            //核心功能和属性列表可在http://sax.sourceforge.net/?selected=get-set中找到
            saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            System.out.println("xsdFile.toURI().toString()==>" + xsdFile.toURI().toString());
            saxParser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", xsdFile.toURI().toString());
        } catch (SAXNotRecognizedException e) {
            e.printStackTrace();
        } catch (SAXNotSupportedException e) {
            e.printStackTrace();
        }
        SAXValidator validator = null;
        try {
            //实例化文件较难工具
            validator = new SAXValidator(saxParser.getXMLReader());
            //注册错误处理器
            validator.setErrorHandler(errorHandler);
            //校验
            validator.validate(xmlDocument);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(OutputFormat.createPrettyPrint());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (errorHandler.getErrors().hasContent()) {
            System.out.println("XML文件通过XSD文件校验失败！");
            try {
                xmlWriter.write(errorHandler.getErrors());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("XML文件通过XSD文件校验成功！");
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        File xmlFile = new File(XMLSchemaTest.class.getResource("schema-data-content.xml").getFile());
        File xsdFile = new File(XMLSchemaTest.class.getResource("schema-data-content.xsd").getFile());
        XMLSchemaTest xmlSchemaTest = new XMLSchemaTest(xmlFile, xsdFile);
        xmlSchemaTest.validateByXSD();
    }
}
