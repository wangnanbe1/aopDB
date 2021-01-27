package com.trust.mediclarecordcollection.utils;

import com.trust.mediclarecordcollection.entity.XMlBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


/**
 * @author wc
 */
@Component
public class CreateXML {

    private static final Logger logger = LoggerFactory.getLogger(CreateXML.class);






    public  void createXml(XMlBean xml, String dir){
        try {
            // 创建解析器工厂
           // String fardir = "F:\\upload\\";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            // 不显示standalone="no"
            document.setXmlStandalone(true);
            Element medicaldoc = document.createElement("medicaldoc");
            // 向bookstore根节点中添加子节点book
            Element hospitalcode = document.createElement("hospitalcode");

            Element systemcode = document.createElement("systemcode");
            Element patientcode = document.createElement("patientcode");
            Element admission = document.createElement("admission");
            Element index = document.createElement("index");
            Element total = document.createElement("total");
            Element docuniquedesc = document.createElement("docuniquedesc");
            Element doctitle = document.createElement("doctitle");
            Element docdesc = document.createElement("docdesc");
            Element docdatetime = document.createElement("docdatetime");
            Element docfileformat = document.createElement("docfileformat");
            Element docfileverifydata = document.createElement("docfileverifydata");
            Element docfileverifytype = document.createElement("docfileverifytype");
            Element verifydatas = document.createElement("verifydatas");


            // 不显示内容 name.setNodeValue("不好使");
            hospitalcode.setTextContent(xml.getHospitalcode());
            systemcode.setTextContent(xml.getSystemcode());
            patientcode.setTextContent(xml.getPatientcode());
            admission.setTextContent(xml.getAdmission());
            index.setTextContent(xml.getIndex());
            total.setTextContent(xml.getTotal());
            docuniquedesc.setTextContent(xml.getDocuniquedesc());
            doctitle.setTextContent(xml.getDoctitle());
            docdesc.setTextContent(xml.getDocdesc());
            docdatetime.setTextContent(xml.getDocdatetime());
            docfileformat.setTextContent(xml.getDocfileformat());


            medicaldoc.appendChild(hospitalcode);
            medicaldoc.appendChild(systemcode);
            medicaldoc.appendChild(patientcode);
            medicaldoc.appendChild(admission);
            medicaldoc.appendChild(index);
            medicaldoc.appendChild(total);
            medicaldoc.appendChild(docuniquedesc);
            medicaldoc.appendChild(doctitle);
            medicaldoc.appendChild(docdesc);
            medicaldoc.appendChild(docdatetime);
            medicaldoc.appendChild(docfileformat);
            medicaldoc.appendChild(docfileverifydata);
            medicaldoc.appendChild(docfileverifytype);
            medicaldoc.appendChild(verifydatas);
            // 将bookstore节点（已包含book）添加到dom树中
            document.appendChild(medicaldoc);

            // 创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            // 创建 Transformer对象
            Transformer tf = tff.newTransformer();

            // 输出内容是否使用换行
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            // 创建xml文件并写入内容
            tf.transform(new DOMSource(document), new StreamResult(new File(dir + ".xml")));
            logger.debug("create xml.xml success");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("create xml.xml fail");
        }
    }

}
