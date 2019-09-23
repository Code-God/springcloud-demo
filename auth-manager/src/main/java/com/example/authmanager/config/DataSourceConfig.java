package com.example.authmanager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

//    @Bean(name = "masterDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource masterDataSource(DataSourceProperties properties) {
//        LOGGER.info("init master data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "slaveDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slave")
//    public DataSource slaveDataSource(DataSourceProperties properties) {
//        LOGGER.info("init slave data source：{}", properties);
//        return DataSourceBuilder.create().build();
//    }

//    @Bean
//    @Primary
//    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
//        Map<String, DataSource> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceEnum.MASTER.getName(), masterDataSource);
//        targetDataSources.put(DataSourceEnum.SLAVE.getName(), slaveDataSource);
//
//        return new DynamicDataSource(masterDataSource, targetDataSources);
//    }
}
