package com.trust.mediclarecordcollection.service;

import com.trust.mediclarecordcollection.annotation.DataSource;
import com.trust.mediclarecordcollection.entity.Brxx;
import com.trust.mediclarecordcollection.entity.ECG;
import com.trust.mediclarecordcollection.repository.ECGMapper;
import com.trust.mediclarecordcollection.repository.MedicalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 16:21
 * @Description:
 * @ConfigurationProperties(prefix = "uploadFilePath")
 */

@Component
public class ECGCollectionService {

    @Autowired
    private ECGMapper ecgMapper;
    @Autowired
    private MedicalMapper medicalMapper;


    @DataSource("ecgDB")
    public List<ECG> queryECGInfo(String brxh){
      return   ecgMapper.queryECGInfo(brxh);
    }

    @DataSource("medicalDB")
    public Brxx queryInfoByBrxh(String brxh){
        return medicalMapper.queryInfoByBrxh(brxh);
    }



}
