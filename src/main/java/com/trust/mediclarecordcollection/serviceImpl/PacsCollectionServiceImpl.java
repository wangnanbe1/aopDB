package com.trust.mediclarecordcollection.serviceImpl;

import com.trust.mediclarecordcollection.entity.*;
import com.trust.mediclarecordcollection.service.MedicalService;
import com.trust.mediclarecordcollection.service.PacsCollectionService;
import com.trust.mediclarecordcollection.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 19:29
 * @Description:
 */

@Component
public class PacsCollectionServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PacsCollectionServiceImpl.class);

    @Autowired
    private CreateXML createXML;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private UUIDUtils uuidUtils;
    @Autowired
    private PacsCollectionService pacsCollectionService;
    @Autowired
    private MedicalService medicalService;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private CloseClinicalSystemByPatientAndAdmission closeClinicalSystemByPatientAndAdmission;

    public void pacsUpload(UploadTask uploadTask) {
        String dirPath = MakeDirUtils.mkdirs();
        List<Pacs> pacss = pacsCollectionService.queryPacsInfo(uploadTask.getBah());
        try {
            if (pacss.size() == 0) {
                int code = closeClinicalSystemByPatientAndAdmission.closeClinicalSystemByPatientIdAndAdmissionAndClinicalSysCode(uploadTask.getBah(), uploadTask.getZycs(), "0102");
                if (code == 204) {
                    medicalService.updateFlagByBrxh("pacs_flag", "3", uploadTask.getBrxh());
                } else {
                    medicalService.updateFlagByBrxh("pacs_flag", "2", uploadTask.getBrxh());
                }
            } else {
                boolean flag = true;
                for (int i = 0; i < pacss.size(); i++) {
                    String uuid = uuidUtils.randomUUIDWithoutRod();
                    Pacs pacs = pacss.get(i);
                    XMlBean xMlBean = new XMlBean();
                    xMlBean.setPatientcode(pacs.getHospitalID());
                    xMlBean.setAdmission("1");
                    xMlBean.setSystemcode(ClinicalSystemCode.PACS_CODE);
                    xMlBean.setIndex(String.valueOf(i + 1));
                    xMlBean.setTotal(String.valueOf(pacss.size()));
                    xMlBean.setDoctitle(pacs.getReportName());
                    xMlBean.setDocdesc(pacs.getExamBodyPart());
                    xMlBean.setDocdatetime(pacs.getReportTime());
                    xMlBean.setDocuniquedesc(pacs.getRequisitionID());
                    createXML.createXml(xMlBean, dirPath + File.separator + uuid);
                    String report_path = pacs.getFilePath();
                    report_path = removeStr(report_path);
                    String fileName = FindFileName.findFileName(report_path);
                    String fileDir = FindFileName.findFileDir(report_path, fileName);
                    //"pdf"+File.separator+fileDir
                    fileUtil.downFromFtp2("10.20.11.5", 21, "ftpuser", "Wxsy@6789",  fileDir, fileName, dirPath + File.separator + uuid + ".pdf");
                    int code = uploadUtils.runPOSTExample("http://10.20.13.79:8081/medicalarchivefiles", dirPath + File.separator + uuid + ".xml", dirPath + File.separator + uuid + ".pdf");
                    if (code != 202) {
                        flag = false;
                    }
                }
                if (flag) {
                    medicalService.updateFlagByBrxh("pacs_flag", "1", uploadTask.getBrxh());
                } else {
                    medicalService.updateFlagByBrxh("pacs_flag", "2", uploadTask.getBrxh());
                }
            }
        } catch (Exception e) {
            medicalService.updateFlagByBrxh("pacs_flag", "2", uploadTask.getBrxh());
            logger.error(e.getMessage());
        }

    }

    private static String removeStr(String str) {
        String fileName = "";
        if (str.contains("E$")) {
            String[] split = str.split("E\\$");
            for (String s : split) {
                if(!s.contains("10.20.11.5")){
                    fileName = s;
                }
            }
        } else {
            fileName = str;
        }
        return fileName;
    }

    public static void main(String[] args) {
        String str = "\\\\10.20.11.5\\E$\\PDF\\2021\\0118\\2021011820001853.pdf";
        String s = removeStr(str);
        System.out.println(s);
    }

}
