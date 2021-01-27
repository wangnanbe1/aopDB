package com.trust.mediclarecordcollection.serviceImpl;

import com.trust.mediclarecordcollection.entity.*;
import com.trust.mediclarecordcollection.service.ECGCollectionService;
import com.trust.mediclarecordcollection.service.MedicalService;
import com.trust.mediclarecordcollection.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 17:01
 * @Description:
 */

@Component
public class ECGCollectionServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ECGCollectionServiceImpl.class);

    @Autowired
    private ECGCollectionService ecgCollectionService;
    @Autowired
    private CreateXML createXML;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private UUIDUtils uuidUtils;
    @Autowired
    private MedicalService medicalService;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private CloseClinicalSystemByPatientAndAdmission closeClinicalSystemByPatientAndAdmission;


    public void ecgUpload(UploadTask uploadTask) {
        String dirPath = MakeDirUtils.mkdirs();
        List<ECG> ecgs = ecgCollectionService.queryECGInfo(uploadTask.getBrxh());
        try {
            if (ecgs.size() == 0) {
                int code = closeClinicalSystemByPatientAndAdmission.closeClinicalSystemByPatientIdAndAdmissionAndClinicalSysCode(uploadTask.getBah(), uploadTask.getZycs(), "0103");
                if (code == 204) {
                    medicalService.updateFlagByBrxh("ecg_flag", "3", uploadTask.getBrxh());
                } else {
                    medicalService.updateFlagByBrxh("ecg_flag", "2", uploadTask.getBrxh());
                }

            } else {
                boolean flag = true;
                for (int i = 0; i < ecgs.size(); i++) {
                    String uuid = uuidUtils.randomUUIDWithoutRod();
                    ECG ecg = ecgs.get(i);
                    XMlBean xMlBean = new XMlBean();
                    xMlBean.setPatientcode(uploadTask.getBah());
                    xMlBean.setAdmission(uploadTask.getZycs());
                    xMlBean.setSystemcode(ClinicalSystemCode.ECG_CODE);
                    xMlBean.setDoctitle(ecg.getTitle());
                    xMlBean.setIndex(String.valueOf(i + 1));
                    xMlBean.setTotal(String.valueOf(ecgs.size()));
                    xMlBean.setDocdesc(ecg.getTEST_ITEM_CODE_TEST());
                    xMlBean.setDocdatetime(ecg.getREPORT_TIME());
                    xMlBean.setDocuniquedesc(uploadTask.getBah() + uploadTask.getZycs() + ecg.getREPORT_TIME());
                    createXML.createXml(xMlBean, dirPath + File.separator + uuid);
                    String report_path = ecg.getREPORT_PATH();
                    String fileName = FindFileName.findFileName(report_path);
                    String fileDir = FindFileName.findFileDir(report_path, fileName);
                    fileUtil.downFromFtp2("10.20.13.4", 21, "tjxdt", "tjxdt", fileDir, fileName, dirPath + File.separator + uuid + ".pdf");

                   

                    int code = uploadUtils.runPOSTExample("http://10.20.13.79:8081/medicalarchivefiles", dirPath + File.separator + uuid + ".xml", dirPath + File.separator + uuid + ".pdf");
                    if (code != 202) {
                        flag = false;
                    }
                }
                if (flag) {
                    medicalService.updateFlagByBrxh("ecg_flag", "1", uploadTask.getBrxh());
                } else {
                    medicalService.updateFlagByBrxh("ecg_flag", "2", uploadTask.getBrxh());
                }


            }
        } catch (Exception e) {
            medicalService.updateFlagByBrxh("ecg_flag", "2", uploadTask.getBrxh());
            logger.error(e.getMessage());
        }

    }


}
