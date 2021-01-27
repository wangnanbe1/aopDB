package com.trust.mediclarecordcollection;

import com.trust.mediclarecordcollection.entity.Brxx;
import com.trust.mediclarecordcollection.entity.ECG;
import com.trust.mediclarecordcollection.entity.Pacs;
import com.trust.mediclarecordcollection.entity.XMlBean;
import com.trust.mediclarecordcollection.service.ECGCollectionService;
import com.trust.mediclarecordcollection.service.PacsCollectionService;
import com.trust.mediclarecordcollection.utils.CreateXML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MediclarecordcollectionApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PacsCollectionService pacsCollectionService;
    @Autowired
    private CreateXML createXML;
    @Autowired
    private ECGCollectionService ecgCollectionService;

    @Value("${uploadFilePath}")
    private String dir;


    @Test
    void test1() {
        List<Pacs> pacss = pacsCollectionService.queryPacsInfo("0001012390");

        for(int i = 0;i<pacss.size();i++){
            Pacs pacs = pacss.get(i);
            XMlBean xMlBean = new XMlBean();
            xMlBean.setPatientcode(pacs.getHospitalID());
            xMlBean.setAdmission("1");
            xMlBean.setSystemcode("0102");
            xMlBean.setIndex(String.valueOf(i+1));
            xMlBean.setTotal(String.valueOf(pacss.size()));
            xMlBean.setDoctitle(pacs.getReportName());
            xMlBean.setDocdesc(pacs.getExamBodyPart());
            xMlBean.setDocdatetime(pacs.getReportTime());
            xMlBean.setDocuniquedesc(pacs.getRequisitionID());
            createXML.createXml(xMlBean,"D://pacs/"+i+"/pacs");

        }
    }

    @Test
    void test2() {
        List<ECG> ecgs = ecgCollectionService.queryECGInfo("ZY010001005391");
        Brxx brxx = ecgCollectionService.queryInfoByBrxh("ZY010001005391");
        for(int i = 0;i<ecgs.size();i++){
            ECG ecg = ecgs.get(i);
            XMlBean xMlBean = new XMlBean();
            xMlBean.setPatientcode(brxx.getBah());
            xMlBean.setAdmission(brxx.getZycs());
            xMlBean.setSystemcode("0102");
            xMlBean.setIndex(String.valueOf(i+1));
            xMlBean.setTotal(String.valueOf(ecgs.size()));
            xMlBean.setDoctitle(ecg.getTitle());
            xMlBean.setDocdesc(ecg.getTEST_ITEM_CODE_TEST());
            xMlBean.setDocdatetime(ecg.getREPORT_TIME());
            xMlBean.setDocuniquedesc(brxx.getBah()+brxx.getZycs()+ecg.getREPORT_TIME());
            createXML.createXml(xMlBean,"D://ecg/"+i+"/ecg");

        }
    }

    @Test
    public void test3(){
        System.out.println("111111"+dir);
    }

}
