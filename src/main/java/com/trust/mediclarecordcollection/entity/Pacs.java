package com.trust.mediclarecordcollection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 11:23
 * @Description:
 * PatientId,ReportName,ExamBodyPart,ReportTime,RequisitionID,FilePath
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pacs {
    private String HospitalID;
    private String ReportName;
    private String ExamBodyPart;
    private String ReportTime;
    private String RequisitionID;
    private String FilePath;

}
