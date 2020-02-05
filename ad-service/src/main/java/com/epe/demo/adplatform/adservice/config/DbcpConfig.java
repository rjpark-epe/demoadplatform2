package com.epe.demo.adplatform.adservice.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "com.epe.demo.adplatform.domain")
public class DbcpConfig {
	
	private static final String CONFIG_NAME     =  "mybatis-batch-config.xml";
	private static final String SQL_MAIN     = "com/epe/demo/adplatform/domain/**/*.main.xml";
	

	@Bean(name="datasource")
	@Qualifier("datasource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.redis")
	public DataSource datasource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
		String configPath = MybatisUtil.toClasspath(this.getClass()) + "/" + CONFIG_NAME;
		final SqlSessionFactoryBean sqlsessionFactoryBean = SqlSessionFactoryUtil.buildSessionFactory(datasource(),false,configPath,SQL_MAIN);
		
//		CglUtil.makeProxy(sqlsessionFactoryBean, new MethodInterceptor (){
//	        @Override
//	        public Object intercept(Object proxy,final Method method,final Object[] args, MethodProxy proxyMethod) throws Throwable {
//				try {
//	                return method.invoke(sqlsessionFactoryBean, args);
//	            } catch (InvocationTargetException e) {
//	            	Exception exception = (Exception)e.getCause();  //실제 예외
//	            	throw exception;
//	            }
//	        }
//	    });		
		
		return sqlsessionFactoryBean;
	}
	
	@Bean(name="sqlSession")
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(datasource());
		return transactionManager;
	}
	
}
