package com.trust.mediclarecordcollection.repository;

import com.trust.mediclarecordcollection.entity.Brxx;
import com.trust.mediclarecordcollection.entity.UploadTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/19 16:35
 * @Description:
 */
@Component
public interface MedicalMapper {


    @Select("select bah,zycs from t_v_brxx where brxh = #{brxh}")
    Brxx queryInfoByBrxh(@Param("brxh") String brxh);

    @Select("select * from upload_task where pacs_flag = 0 ")
    List<UploadTask> queryPacsTask();

    @Select("select * from upload_task where ecg_flag = 0 ")
    List<UploadTask> queryECGTask();

    @Select("select * from upload_task where lis_flag = 0 ")
    List<UploadTask> queryLisTask();

    @Update("update upload_task set ${taskNme} = #{flag} where brxh= #{brxh}")
    void updateFlagByBrxh(@Param("taskNme") String taskNme,@Param("flag") String flag ,@Param("brxh") String brxh);

//    @SelectProvider(type = MedicalProvider.class, method = "updateFlagByBrxh")
//    public void updateFlagByBrxh(String taskName, @Param("flag") String flag, @Param("brxh") String brxh);
//
//    class MedicalProvider {
//        public String updateFlagByBrxh(String taskName, String flag, String brxh) {
//            String sql = "update upload_task set";
//            if (taskName == "ecg_flag") {
//                sql += " set ecg_flag = #{flag}";
//            }
//            if (taskName == "lis_flag") {
//                sql += " set lis_flag = #{flag}";
//            }
//            if (taskName == "pacs_flag") {
//                sql += " set pacs_flag = #{flag}";
//            }
//            sql += " where brxh = #{brxh}";
//            return sql;
//        }
//    }
}

