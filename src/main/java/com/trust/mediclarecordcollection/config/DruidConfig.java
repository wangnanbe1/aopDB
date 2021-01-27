package com.trust.mediclarecordcollection.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/14 14:23
 * @Description:
 */

@Configuration
public class DruidConfig {

    @Bean(name = "pacsDB")
    @ConfigurationProperties(prefix = "spring.datasource.pacs")
    public DataSource PacsDataSource() {
        return new DruidDataSource();
    }
    @Bean(name = "ecgDB")
    @ConfigurationProperties(prefix = "spring.datasource.ecg")
    public DataSource LisDataSource() {
        return  new DruidDataSource();
    }
    @Bean(name = "medicalDB")
    @ConfigurationProperties(prefix = "spring.datasource.medical")
    public DataSource medicalDataSource() {
        return  new DruidDataSource();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(PacsDataSource());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("pacsDB", PacsDataSource());
        dsMap.put("ecgDB", LisDataSource());
        dsMap.put("medicalDB", medicalDataSource());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}
