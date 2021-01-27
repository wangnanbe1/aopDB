package com.trust.mediclarecordcollection.repository;

import com.trust.mediclarecordcollection.entity.ECG;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 16:20
 * @Description:
 * select TEST_ITEM_CODE_TEST,'心电图' title,REPORT_TIME,REPORT_PATH from VIEW_ECGREPORTPT v where INHOSPITAL_ID = 'ZY010001005391'
 */


@Component
public interface ECGMapper {

    @Select("select TEST_ITEM_CODE_TEST,REPORT_TIME,REPORT_PATH from GWECG.VIEW_ECGREPORTPT v where INHOSPITAL_ID = #{brxh}")
    List<ECG> queryECGInfo(@Param("brxh") String brxh);

}
