package com.example.config.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zhangjie
 * @date 2019/8/12 16:50
 */
@Configuration
@MapperScan(basePackages = "com.example.dao.db1",sqlSessionTemplateRef = "oneSqlSessionTemplate")
public class DataSourceConfigDBOne {

    @Value("${spring.datasource.db1.url}")
    private String url;

    @Value("${spring.datasource.db1.username}")
    private String username;

    @Value("${spring.datasource.db1.password}")
    private String password;

    @Autowired(required = false)
    private DataSourceConfigProperties dataSourceConfigProperties;

    /**
     * 连接数据库db1数据源
     * @return
     */
    @Bean("oneDataSource")
    public DataSource oneDataSource(){
        return dataSourceConfigProperties.initProperties(url, username, password);
    }

    /**
     * SqlSessionFactory是MyBatis的关键对象,它是个单个数据库映射关系经过编译后的内存镜像
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean("oneSqlSessionFactory")
    public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/db1/*Mapper.xml"));
        SqlSessionFactory sqlSessionFactory = bean.getObject();
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        return sqlSessionFactory;
    }

    /**
     * 定义业务核心库的事务管理器
     * @param dataSource
     * @return
     */
    @Bean("oneTransactionManager")
    public DataSourceTransactionManager oneTransactionManager(@Qualifier("oneDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * SqlSessionTemplate是MyBatis-Spring的核心。这个类负责管理MyBatis的SqlSession,调用MyBatis的SQL方法，翻译异常
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean("oneSqlSessionTemplate")
    public SqlSessionTemplate oneSqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
