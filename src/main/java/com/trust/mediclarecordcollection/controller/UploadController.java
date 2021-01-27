package com.trust.mediclarecordcollection.controller;

import com.trust.mediclarecordcollection.entity.Brxx;
import com.trust.mediclarecordcollection.entity.UploadTask;
import com.trust.mediclarecordcollection.service.MedicalService;
import com.trust.mediclarecordcollection.serviceImpl.ECGCollectionServiceImpl;
import com.trust.mediclarecordcollection.serviceImpl.PacsCollectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @Author: wc
 * @Date: 2021/1/19 19:46
 * @Description:
 */

@Configurable
@EnableScheduling
@Controller
public class UploadController {

    @Autowired
    private ECGCollectionServiceImpl ecgCollectionService;
    @Autowired
    private PacsCollectionServiceImpl pacsCollectionService;
    @Autowired
    private MedicalService medicalService;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void upload(){

        List<UploadTask> pacsTask = medicalService.findPacsTask();
        pacsTask.forEach(uploadTask -> {
            pacsCollectionService.pacsUpload(uploadTask);
        });
        List<UploadTask> ecgTask = medicalService.queryECGTask();
        ecgTask.forEach(uploadTask->{
            ecgCollectionService.ecgUpload(uploadTask);
        });


    }




}
