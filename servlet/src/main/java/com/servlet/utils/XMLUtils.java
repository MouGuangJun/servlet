package com.servlet.utils;

import cn.hutool.core.util.StrUtil;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于dom4j的工具包
 */
public class XMLUtils {

    /**
     * 通过文件的路径获取xml的document对象
     *
     * @param path 文件的路径
     * @return 返回文档对象
     */
    public static Document getDocument(String path) {
        if (null == path) {
            return null;
        }
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    /**
     * 通过xml字符获取document文档
     *
     * @param xmlStr 要序列化的xml字符
     * @return 返回文档对象
     * @throws DocumentException 文档异常
     */
    public static Document getXMLByString(String xmlStr) throws DocumentException {
        if (StrUtil.isBlank(xmlStr)) {
            return null;
        }

        return DocumentHelper.parseText(xmlStr);
    }

    /**
     * 获取某个元素的所有的子节点
     *
     * @param node 制定节点
     * @return 返回所有的子节点
     */
    public static List<Element> getChildElements(Element node) {
        if (null == node) {
            return null;
        }

        return node.elements();
    }

    /**
     * 获取指定节点的子节点
     *
     * @param node      父节点
     * @param childNode 指定名称的子节点
     * @return 返回指定的子节点
     */
    public static Element getChildElement(Element node, String childNode) {
        if (null == node || StrUtil.isBlank(childNode)) {
            return null;
        }

        return node.element(childNode);
    }

    /**
     * 获取所有的属性值
     *
     * @param node 需要获取属性的节点对象
     * @param arg  指定的属性值名称
     * @return 返回属性的值
     */
    public static Map<String, String> getAttributes(Element node, String... arg) {
        if (node == null) {
            return null;
        }

        // 如果没有指定属性名，则获取全部属性
        if (arg == null || arg.length == 0) {
            return node.attributes().stream().collect(Collectors.toMap(Node::getName, Attribute::getValue));
        }

        Map<String, String> attrMap = new HashMap<>();
        for (String attr : arg) {
            String attrValue = node.attributeValue(attr);
            attrMap.put(attr, attrValue);
        }

        return attrMap;
    }

    /**
     * 获取element的单个属性
     *
     * @param node 需要获取属性的节点对象
     * @param attr 需要获取的属性值
     * @return 返回属性的值
     */
    public static String getAttribute(Element node, String attr) {
        if (null == node || StrUtil.isBlank(attr)) {
            return "";
        }

        return node.attributeValue(attr);
    }

    /**
     * 添加孩子节点元素
     *
     * @param parent     父节点
     * @param childName  孩子节点名称
     * @param childValue 孩子节点值
     * @return 新增节点
     */
    public static Element addChild(Element parent, String childName, String childValue) {
        Element child = parent.addElement(childName);// 添加节点元素
        child.setText(childValue == null ? "" : childValue); // 为元素设值

        return child;
    }

    /**
     * DOM4j的Document对象转为XML报文串
     *
     * @param document xml文档
     * @param charset  字符集
     * @return 经过解析后的xml字符串
     */
    public static String documentToString(Document document, String charset) {
        StringWriter stringWriter = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();// 获得格式化输出流
        format.setEncoding(charset);// 设置字符集,默认为UTF-8
        XMLWriter xmlWriter = new XMLWriter(stringWriter, format);// 写文件流
        try {
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringWriter.toString();
    }

    /**
     * 去掉声明头的(即<?xml...?>去掉)
     *
     * @param document xml文档
     * @param charset  字符集
     * @return 去除后的结果
     */
    public static String documentToStringNoDeclaredHeader(Document document, String charset) {
        String xml = documentToString(document, charset);

        return xml.replaceFirst("\\s*<[^<>]+>\\s*", "");
    }

    /**
     * 获取element对象的text的值
     *
     * @param e   节点的对象
     * @param tag 节点的tag
     * @return 字符串内容
     */
    public static String getText(Element e, String tag) {
        Element _e = e.element(tag);
        if (_e != null) {
            return _e.getText();
        }

        return null;
    }

    /**
     * 获取去除空格的字符串
     *
     * @param e   节点的对象
     * @param tag 节点的tag
     * @return 字符串内容
     */
    public static String getTextTrim(Element e, String tag) {
        Element _e = e.element(tag);
        if (_e != null) {
            return _e.getTextTrim();
        }

        return null;
    }

    /**
     * 获取节点值.节点必须不能为空，否则抛错
     *
     * @param parent 父节点
     * @param tag    想要获取的子节点
     * @return 返回子节点
     */
    public static String getTextTrimNotNull(Element parent, String tag) {
        Element e = parent.element(tag);
        if (e == null) {
            throw new NullPointerException("节点为空");
        }

        return e.getTextTrim();
    }

    /**
     * 节点必须不能为空，否则抛错
     *
     * @param parent 父节点
     * @param tag    想要获取的子节点
     * @return 子节点
     */
    public static Element elementNotNull(Element parent, String tag) {
        Element e = parent.element(tag);
        if (e == null) {
            throw new NullPointerException("节点为空");
        }

        return e;
    }

    /**
     * 将文档对象写入对应的文件中
     *
     * @param document 文档对象
     * @param path     写入文档的路径
     * @throws IOException io异常
     */
    public static void writeXMLToFile(Document document, String path) throws IOException {
        if (document == null || path == null) {
            return;
        }
        XMLWriter writer = new XMLWriter(new FileWriter(path));
        writer.write(document);
        writer.close();
    }
}