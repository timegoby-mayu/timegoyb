package com.fly.my.Util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * 作用: XML解析工具类,其中的属性根据自己需要另行添加或者更改
 */
public class ParseXmlUtil {
    static Logger logger = LoggerFactory.getLogger(ParseXmlUtil.class);
    static FileInputStream ins;

    public static String trxId;              //文件id
    public static String trxBank;            //银行编码
    public static String trxOper;            //
    public static String trxDate;            //数据日期
    public static String PkgNo;              //包号
    public static String fileCode;           //文件编码
    public static String fileName;           //文件类型
    public static String fileContent;        //文件内容


    public static void PullConfigXml(String path) {
        logger.info("开始读取配置文件...");
        try {
            File file = null;
            //本地测试路径  /home/ngpcom/dfgz/config
            //String path=System.getProperty("user.home")+file.separator+"dfgz"+file.separator+"config"+file.separator+"config1.xml";
            //String path =System.getProperty("user.home")+file.separator+"config"+file.separator+"config1.xml";
            logger.info("配置文件的路径[" + path + "]");
            ins = new FileInputStream(new File(path));
        } catch (Exception e) {
            logger.error("读取配置文件异常，异常信息为：【" + e.getMessage() + "】");
        }
        logger.info("读取配置文件成功，开始解析xml文档");

        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML输入,此处为文件流
        InputSource source = new InputSource(ins);
        // 创建一个新的SAXBuilder
        SAXBuilder saxbBuilder = new SAXBuilder();
        try {
            // 通过输入源构造一个Document
            Document doc = saxbBuilder.build(source);
            // 取得xml根元素
            Element root = doc.getRootElement();
            // 取得根元素的子元素
            List<?> node = root.getChildren();
            for (int i = 0; i < node.size(); i++) {
                Element element = (Element) node.get(i);
                if (element.getName().equals("APPTYPE")) {
                    trxId = element.getValue();
                } else if (element.getName().equals("ERRRECORDS")) {
                    trxBank = element.getValue();
                } else if (element.getName().equals("trxOper")) {
                    trxOper = element.getValue();
                } else if (element.getName().equals("trxDate")) {
                    trxDate = element.getValue();
                } else if (element.getName().equals("PkgNo")) {
                    PkgNo = element.getValue();
                } else if (element.getName().equals("fileCode")) {
                    fileCode = element.getValue();
                } else if (element.getName().equals("fileName")) {
                    fileName = element.getValue();
                } else if (element.getName().equals("fileContent")) {
                    fileContent = element.getValue();
                }
            }
            logger.info("                                      解析xml配置文件成功");
            logger.info("*****************************************************************************");
            logger.info("    APPTYPE:[" + trxId + "]");
            logger.info("    ERRRECORDS:[" + trxBank + "]");
            logger.info("    ERRFIELD:[" + trxOper + "]");
            logger.info("    trxDate:[" + trxDate + "]");
            logger.info("    PkgNo:[" + PkgNo + "]");
            logger.info("    fileCode:[" + fileCode + "]");
            logger.info("    fileName:[" + fileName + "]");
            logger.info("    fileContent:[" + fileContent + "]");
            logger.info("*****************************************************************************");
        } catch (Exception e) {
            logger.error("解析xml配置文件异常，异常信息为：【" + e.getMessage() + "】");
        }

    }

    public static void main(String[] args) {
        PullConfigXml("D:/XMLData.XML");
    }
}