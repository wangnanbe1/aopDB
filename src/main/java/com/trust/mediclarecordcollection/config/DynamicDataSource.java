package com.trust.mediclarecordcollection.config;

import com.trust.mediclarecordcollection.annotation.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import java.util.Map;

/**
 * @Author: wangnanbei
 * @Date: 2021/1/14 14:27
 * @Description:
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug("datasource: {}" , DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();

    }
}
