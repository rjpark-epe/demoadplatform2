package com.epe.demo.adplatform.adservice.config;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public abstract class SqlSessionFactoryUtil{
    
	private final static Logger LOG = LoggerFactory.getLogger(SqlSessionFactoryUtil.class);
	private static DefaultResourceLoader LOADER = new DefaultResourceLoader();
    
	public static SqlSessionFactoryBean buildSessionFactory(DataSource source,boolean debug,String configPath,String ... sqlPath) throws IOException {
		SqlSessionFactoryBean factory =  new SqlSessionFactoryBean();
    	if(source == null) {
    		LOG.warn("dataSource 가 없어서 wrapper 로 대체합니다.");
    	}
    	Resource[] mapperLocations =  antToResources(AntResourceType.classpath, StringUtils.join(sqlPath, ","));
    	for(Resource each : mapperLocations){
    		LOG.debug("{} 가 {}를 로드합니다.",configPath,each);	
    	}
    	factory.setMapperLocations(mapperLocations);
    	factory.setDataSource(source);
    	factory.setConfigLocation(LOADER.getResource(configPath));
		return factory;
	}
	
	public static enum AntResourceType{
    	/** *붙이면 전부, 없다면 첫번째 꺼만 가져온다. 편하게 *로만 사용한다. */
    	classpath("classpath*:"),
    	file("file:"),
    	;
    	
    	private AntResourceType(String path){
    		this.path = path;
    	}
    	
    	private final String path;

		public String getPath() {
			return path;
		}
    	
    }
    
	public static Resource[] antToResources(AntResourceType antResourceType,String antMatchs) throws IOException{
    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	Iterable<String> matches = Splitter.on(',').trimResults().omitEmptyStrings().split(antMatchs);
    	List<Resource> allResources = Lists.newArrayList();
    	
    	for(String match : matches){
    		Resource[] res = resolver.getResources(antResourceType.getPath()+match);
    		for(Resource each : res) allResources.add(each);
    	}
    	
    	return allResources.toArray(new Resource[allResources.size()]);
    }
  
}
