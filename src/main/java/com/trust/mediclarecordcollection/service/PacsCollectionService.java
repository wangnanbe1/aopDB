package com.trust.mediclarecordcollection.service;

import com.trust.mediclarecordcollection.annotation.DataSource;
import com.trust.mediclarecordcollection.entity.Pacs;
import com.trust.mediclarecordcollection.repository.PacsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/15 15:37
 * @Description:
 */

@Component
public class PacsCollectionService {

    @Autowired
    private PacsMapper pacsMapper;



    @DataSource("pacsDB")
    public List<Pacs> queryPacsInfo(String HospitalID){
        return pacsMapper.queryPacsInfo(HospitalID);
    }
}
