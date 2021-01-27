package com.trust.mediclarecordcollection.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class XMlBean {
    private String hospitalcode = "12320200466286020Y";
    private String systemcode ;
    private String patientcode;
    private String admission;
    private String index;
    private String total;
    private String doctitle;
    private String docdesc;
    private String docdatetime;
    private String docfileformat = "application/pdf";
    private String docfileverifydata;
    private String docfileverifytype;
    private String verifydatas;
    private String docuniquedesc;

}
