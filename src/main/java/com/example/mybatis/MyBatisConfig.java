package com.example.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        System.out.println("datasource configuration");
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // mappers 위치 설정
        Resource[] arrResource = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mappers/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(arrResource);
        return sqlSessionFactoryBean.getObject();
    }

}
