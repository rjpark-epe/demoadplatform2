package com.epe.demo.adplatform.domain.repository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.data.domain.Persistable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 공용 DAO
 * @author sin
 */
public abstract class MyBatisMainRepository<T extends Persistable<ID>,ID extends Serializable> extends PersistableBaseRepository<T,ID> {

    @Resource private SqlSessionFactory sqlSessionFactory;

    /** metadataHolder를 사용하도록 오버라이드 해준다. */
	@SuppressWarnings("rawtypes")
	@Override
	public <S extends T> S save(S vo) {
		try {
			super.save(vo);
		} catch (Exception e) {
			throw e;
		}
		return vo;
	}
	
	/** metadataHolder를 사용하도록 오버라이드 해준다. */
	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> vos) {
		super.saveAll(vos);
		return vos;
	}
    
    @PostConstruct
    public void postConstruct() {
    	setSqlSessionFactory(sqlSessionFactory);
    }
    
}
