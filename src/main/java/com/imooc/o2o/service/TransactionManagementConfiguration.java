package com.imooc.o2o.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 首先使用注解EnableTransactionManagement开启事务支持
 * 在service方法上注解@Transactionnal便可
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {
   //注入DataSourceConfiguration里面的dataSource，通过createDataSource()获取
    @Autowired
    private DataSource dataSource;

    /**
     * 关于事务管理需要返回platformTransactionManager的实现
     * @return
     */
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
