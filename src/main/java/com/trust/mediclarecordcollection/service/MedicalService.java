package com.trust.mediclarecordcollection.service;

import com.trust.mediclarecordcollection.annotation.DataSource;
import com.trust.mediclarecordcollection.entity.UploadTask;
import com.trust.mediclarecordcollection.repository.MedicalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 19:48
 * @Description:
 */

@Component
public class MedicalService {
    @Autowired
    private MedicalMapper medicalMapperl;

    @DataSource("medicalDB")
    public List<UploadTask> findPacsTask(){
        return medicalMapperl.queryPacsTask();
    }

    @DataSource("medicalDB")
    public List<UploadTask> queryECGTask(){
        return medicalMapperl.queryECGTask();
    }


    @DataSource("medicalDB")
    public List<UploadTask> queryLisTask(){
        return medicalMapperl.queryLisTask();
    }

    @DataSource("medicalDB")
    public void updateFlagByBrxh(String taskName,String flag ,String brxh){
        medicalMapperl.updateFlagByBrxh(taskName,flag,brxh);
    }
}
