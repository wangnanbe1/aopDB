package com.trust.mediclarecordcollection.entity;

import lombok.Data;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/20 15:25
 * @Description:
 */

@Data
public class UploadTask {

    private String brxh;
    private String bah;
    private String zycs;
    private String ecg_flag;
    private String lis_flag;
    private String pacs_flag;

}
