package com.trust.mediclarecordcollection.repository;

import com.trust.mediclarecordcollection.entity.Pacs;
import com.trust.mediclarecordcollection.entity.Test;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/14 14:52
 * @Description:
 */

@Component
public interface PacsMapper {



    @Select("select * from test")

    List<Test> query();


    @Select("SELECT HospitalID,ReportName,ExamBodyPart,ReportTime,RequisitionID,FilePath from UploadInfo where HospitalID = #{HospitalID}")
    List<Pacs> queryPacsInfo(@Param("HospitalID") String  HospitalID);




}
