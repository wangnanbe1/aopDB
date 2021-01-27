package com.trust.mediclarecordcollection.entity;

import lombok.Data;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 16:17
 * @Description:
 *
 */

@Data
public class ECG {
    private String TEST_ITEM_CODE_TEST;
    private String title = "心电图";
    private String REPORT_TIME;
    private String REPORT_PATH;
}
