package com.wjl.o2o.config.dao;

import com.wjl.o2o.util.DESUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.imooc.o2o.dao")
public class DataSourceConfigration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource()throws Exception{
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(jdbcDriver);
        comboPooledDataSource.setJdbcUrl(jdbcUrl);
        comboPooledDataSource.setUser(DESUtils.getDecryptString(jdbcUsername));
        comboPooledDataSource.setPassword(DESUtils.getDecryptString(jdbcPassword));
        comboPooledDataSource.setMaxPoolSize(30);
        comboPooledDataSource.setMinPoolSize(10);
        comboPooledDataSource.setCheckoutTimeout(10000);
        comboPooledDataSource.setAcquireRetryAttempts(2);
        return  comboPooledDataSource;
    }
}
