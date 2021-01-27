package com.trust.mediclarecordcollection.config;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/14 14:42
 * @Description:
 */
public class DataSourceContextHolder {

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "pacsDB";

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();


    /**
     * 设置数据源名
     * @param dbType
     * @Description
     */
    public static void setDB(String dbType) {
        CONTEXT_HOLDER.set(dbType);
    }
    /**
     * 获取数据源名
     */

    public static String getDB() {
        return (CONTEXT_HOLDER.get());
    }

    /**
     * 清除数据源名
      */

    public static void clearDB() {
        CONTEXT_HOLDER.remove();
    }
}
