package com.huaguoguo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.pool.DruidDataSource;

/**
 ************************************************************
 * @类名 : DataSourceConfig.java
 *
 * @DESCRIPTION :数据源相关配置
 * @AUTHOR : cgj
 * @DATE : 2017年10月28日
 ************************************************************
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "com.huaguoguo.mapper" }, sqlSessionFactoryRef = "sqlSessionFactoryOracle")
public class DataSourceConfigTms {

    @Bean(name = "datasourceOracle")
    @ConfigurationProperties(prefix = "spring.datasource.tms")
    public DataSource testDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactoryOracle")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("datasourceOracle") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
        // 开启驼峰命名转换
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        //自定义数据库配置的时候，需要将pageHelper的bean注入到Plugins中，如果采用系统默认的数据库配置，则只需要定义pageHelper的bean，会自动注入。
        return bean.getObject();
    }

    @Bean(name = "transactionManagerOracle")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("datasourceOracle") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateOracle")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactoryOracle") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "interceptorTms")
    public TransactionInterceptor transactionInterceptor(@Qualifier("transactionManagerOracle") PlatformTransactionManager transactionManager) {
        final String PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED,-Exception";
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        Properties transactionAttributes = new Properties();
        // TransactionDefinition.PROPAGATION_REQUIRED;
        transactionAttributes.setProperty("insert*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("update*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("delete*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("add*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("modify*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("login*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("biz*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("set*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("register*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("reset*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("pass*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("refuse*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("use*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("sub*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("create*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("send*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("get*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("find*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("load*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("search*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("query*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("data*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("user*", PROPAGATION_REQUIRED);
        transactionAttributes.setProperty("select*", PROPAGATION_REQUIRED + ",readOnly");
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }


    @Bean("advisorTms")
    @Primary
    public Advisor txAdviceAdvisor(@Qualifier("interceptorTms") TransactionInterceptor interceptor) {
        String expression = "execution(* com.ezgo.bm.tms.service..*.*(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
        return advisor;
    }


}
