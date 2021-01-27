package com.trust.mediclarecordcollection.controller;

import com.trust.mediclarecordcollection.utils.MakeDirUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * @description:
 * @author: wc
 * @time: 2021/1/27 15:41
 */

@Configurable
@EnableScheduling
@Controller
public class DeleteDirController{

    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void deleDir(){
        MakeDirUtils.deleDir();
    }
}
