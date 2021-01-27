package com.trust.mediclarecordcollection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.trust.mediclarecordcollection.repository")
public class MediclarecordcollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediclarecordcollectionApplication.class, args);
    }

}
