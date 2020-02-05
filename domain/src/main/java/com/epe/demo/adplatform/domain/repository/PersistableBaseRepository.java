package com.epe.demo.adplatform.domain.repository;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import com.epe.demo.adplatform.domain.rds.common.MetadataAware;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.UncategorizedSQLException;

import java.io.Serializable;
import java.util.*;

@Data
public abstract class PersistableBaseRepository <T extends Persistable<ID>,ID extends Serializable> implements CrudRepository<T, ID> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private String daoName = this.getClass().getSimpleName();
    /**  this.sqlSession = new SqlSessionTemplate(sqlSessionFactory); */
    protected SqlSession sqlSession;

    /** 기본 이름을 조합해준다. 필요하면 오버라이드 */
    protected String getSqlId(String sqlMap) {
        return getDaoName()+"."+sqlMap;
    }

    /** 대부분 어노테이션을 사용하겠지만 XML에서도 DI가능하게 하나 빼줌 */
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
    }

    /** 실행타입 변경해서 만들때 */
    public void setSqlSessionFactoryManual(SqlSessionFactory sqlSessionFactory, String daoName, ExecutorType executorType) {
        this.daoName = daoName;
        this.sqlSession = new SqlSessionTemplate(sqlSessionFactory,executorType);
    }

    // ==============================  간단 메서드 ============================ //

    /** 이름 변경해주는 기본메소드. T 말고 다른거도 insert 할 수 있다. */
    protected int insert(String sqlId,Object vo){
        return sqlSession.insert(getSqlId(sqlId), vo);
    }
    /** 이름 변경해주는 기본메소드 */
    protected int update(String sqlId,Object vo){
        try {
            return sqlSession.update(getSqlId(sqlId), vo);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    /** 이름 변경해주는 기본메소드.  (파라메터가 Object이다.) */
    protected int delete(String sqlId,Object vo){
        return sqlSession. delete(getSqlId(sqlId), vo);
    }


    /** 스프리밍 전체 다운로드 */
    public void findAll(T t, ResultHandler<?> handler) {
        sqlSession.select(getSqlId("findAll"),t,handler);
    }

    /** 스프리밍 전체 다운로드 */
    public void findWithHandler(String sqlId, Object t,ResultHandler<?> handler) {
        sqlSession.select(getSqlId(sqlId),t,handler);
    }

    /** 특수용도. TASK 등 업도드한거 다운로드용 */
    public void taskDownloadAll(T t,ResultHandler<?> handler) {
        sqlSession.select(getSqlId("taskDownloadAll"),t,handler);
    }

    protected <S extends T> boolean insert(S vo){
        int insertCount =  insert("insert", vo);
        return insertCount == 1;
    }

    protected <S extends T> boolean update(S vo){
        try {
            return update("update", vo) == 1;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /** 검색조건 있는 애들 래퍼 */
    protected List<T> find(String sqlId, Object vo){
        try {
            return sqlSession.selectList(getSqlId(sqlId), vo);
        } catch (UncategorizedSQLException e) {
            if(e.getMessage().startsWith("Error setting null for parameter")){
                log.error("find null value : {}",vo);
            }
            throw e;
        }
    }

    /** T 말고 완전 별개의 타입을 매핑할때 (like map) */
    protected <OTHER> List<OTHER> findList(String sqlId,Object vo){
        return sqlSession.selectList(getSqlId(sqlId), vo);
    }

    @SuppressWarnings("unchecked")
    protected <RT> RT findOneObject(String sqlId,Object vo){
        return (RT) sqlSession.selectOne(getSqlId(sqlId), vo);
    }

    /** 검색조건 + 페이징(토탈카운트 필요한애들) */
    protected Page<T> findPage(String sqlId, PageAware vo){
        List<T> datas = find(sqlId+"_pageList", vo);
        PageAware aware = (PageAware) vo;
        long totalCount =  findOneObject(sqlId+"_pageCount", vo);
        PageImpl<T> page = new PageImpl<T>(datas,aware.getPageable(),totalCount);
        return page;
    }

    /** 검색조건 + 페이징(토탈카운트 필요한애들) - map */
    protected <OTHER> Page<OTHER> findPageList(String sqlId,PageAware vo){
        List<OTHER> datas = findList(sqlId+"_pageList", vo);
        PageAware aware = (PageAware) vo;
        long totalCount =  findOneObject(sqlId+"_pageCount", vo);
        PageImpl<OTHER> page = new PageImpl<OTHER>(datas,aware.getPageable(),totalCount);
        return page;
    }

    /**
     * 간단메소드
     * 경고!!! findOne에서 키가 복키일 경우 안된다.  param.getId() -> param 일케 바꿔야함.  쓰는데가 많아서 일단 놔둔다.
     * @see
     *  */
    public T findOne(T param) {
        T vo = sqlSession.selectOne(getSqlId("findOne"),param.getId());
        return vo;
    }

    /** findOne대신 이걸 대용으로 쓰자 */
    public T findByPk(T param) {
        return sqlSession.selectOne(getSqlId("findOne"),param);
    }

    /** get() 시에 null 이 가능하다.NoSuchElementException 에러 처리 혹은 orElse 로 가져오도록 한다. **/
    public Optional<T> findById(ID id) {
        T vo = sqlSession.selectOne(getSqlId("findOne"),id);
        Optional<T> optional = Optional.ofNullable(vo);
        return optional;
    }

    /** PK로 서버측 자료를 대량으로 가져온다. 여기서 데이터를 조금씩 수정 후 update 해주자 */
    public List<T> findAll(Collection<T> inputs) {
        List<T> servers = Lists.newArrayList();
        for(T input : inputs){
            T server = findOne(input);
            servers.add(server);
        }
        return servers;
    }

    // ==============================  compare 관련 ============================ //

    /** PK로 서버측 자료를 대량으로 가져온다. findAll과 비슷하지만   */
    /*public List<CompareVo<T>> findCompare(Collection<? extends T> inputs) {
        List<CompareVo<T>> compares = Lists.newArrayList();
        for(T input : inputs){
            T server = findOne(input);
            compares.add(CompareVo.of(server, input));
        }
        return compares;
    }*/

    // ==============================  CrudRepository 구현 ============================ //

    /** 사용안함 */
    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }
    @Override
    public void deleteById(ID id) {
        sqlSession.delete(getSqlId("delete"), id);
    }
    @Override
    public void delete(T vo) {
        sqlSession.delete(getSqlId("delete"), vo.getId());
    }
    @Override
    public void deleteAll(Iterable<? extends T> vos) {
        for(T vo : vos) delete(vo);
    }
    /** PK 값이 2개인 경우 이걸 활용하자 */
    public void deleteByPk(Iterable<? extends T> vos) {
        for(T vo : vos) deleteByPk(vo);
    }
    public void deleteByPk(T vo) {
        sqlSession.delete(getSqlId("delete"), vo);
    }
    /** 사용에 주의할것 */
    @Override
    public void deleteAll() {
        sqlSession.delete(getSqlId("deleteAll"),null);
    }
    @Override
    public boolean existsById(ID id) {
        T vo = findOne(id);
        return vo != null;
    }

    @Override
    public Iterable<T> findAll() {
        return  sqlSession.selectList(getSqlId("findAll"),null);
    }

    /** null이 있어도 그대로 담아서 리턴한다. */
    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> results = Lists.newArrayList();
        for(ID id : ids){
            T vo = findOne(id);
            results.add(vo);
        }
        return results;
    }
    /** null은 빼고 담아서 리턴한다. */
    public Iterable<T> findAllWithOutNull(Iterable<ID> ids) {
        List<T> results = Lists.newArrayList();
        for(ID id : ids){
            T vo = findOne(id);
            if(vo != null)
                results.add(vo);
        }
        return results;
    }
    /** sqlId는 findListByIdsUseMultiKey 로 구현해준다. */
    public Iterable<T> findListByVosUseMultiKey(Iterable<T> vos){
        List<T> results = Lists.newArrayList();
        List<ID> ids = Lists.newArrayList();
        for(T vo : vos){
            ids.add(vo.getId());
        }
        if(ids.size() > 0){
            results.addAll((List<T>) findListByIdsUseMultiKey(ids));
        }
        return results;
    }
    /** Pk로 가지고 온다. in절 사용으로  1000개씩 잘라서 가져온다. */
    public Iterable<T> findListByIdsUseMultiKey(List<ID> ids) {
        int inCauseSize = 1000;
        if(ids.size() > inCauseSize){
            List<T> results = Lists.newArrayList();
            int idsSize = ids.size();
            int loopCnt = idsSize % inCauseSize == 0 ? idsSize / inCauseSize : idsSize / inCauseSize + 1;
            for(int i=0;i<loopCnt;i++){
                if(i != (loopCnt - 1)){
                    results.addAll((List<T>)findListByIdsUseMultiKey(ids.subList(i*inCauseSize, (i+1)*inCauseSize)));
                }else{
                    results.addAll((List<T>)findListByIdsUseMultiKey(ids.subList(i*inCauseSize, idsSize)));
                }
            }
            return results;
        }else{
            /** 리스트를 넘길 경우 mybatis에서 바인딩을 못한다. mybatis 쪽 이슈인듯 */
            Map<String,List<ID>> map = Maps.newHashMap();
            map.put("ids", ids);
            return sqlSession.selectList(getSqlId("findListByIdsUseMultiKey"),map);
        }
    }

    public T findOne(ID id) {
        T vo = sqlSession.selectOne(getSqlId("findOne"),id);
        return vo;
    }

    @Override
    public <S extends T> S save(S vo) {
        if(vo instanceof MetadataAware) metadataaware(vo);
        if(vo.isNew()) insert(vo);
        else update(vo);
        return vo;
    }

    /**
     * 마이바티스 때문에 조절해준다. 배치는 이쪽을 사용할것
     *  */
    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> vos) {
        List<S> inserts = Lists.newArrayList();
        List<S> updates = Lists.newArrayList();
        for(S vo : vos){
            if(vo instanceof MetadataAware) metadataaware(vo);
            if(vo.isNew()) inserts.add(vo);
            else updates.add(vo);
        }
        for(S vo : inserts) insert(vo);
        for(S vo : updates) update(vo);
        return vos;
    }

    private <S extends T> void metadataaware(S vo) {
        Metadata metadata = new Metadata();
        metadata.setUpdateTime(new Date());
        metadata.setUpdateId("system");
        metadata.setUpdateIp("127.0.0.1");
        metadata.setUpdateInstance("AD_SERVICE");
        ((MetadataAware)vo).setMetadata(metadata);
    }


    /** 각종 부가정보(자식테이블 등등)를 호함해서 로드해준다. 오버라이드 할것. */
    public T load(ID id) {
        T vo = findOne(id);
        return vo;
    }

    // ========================== MybatisVo  사용 ======================================

    /** 마이바티스용 심플 입력기 */
    /*public void dirtySave(MybatisVo<T> vo){
        saveAll(vo.getInsertList());
        saveAll(vo.getUpdateList());
        deleteAll(vo.getDeleteList());
    }*/

    /** 마이바티스용 심플 입력기. 소스참고용이다. 응용이 필요하면 풀어서 사용하자. */
    /*public MybatisVo<T> dirtySave(Collection<? extends T> inputs,CompareTool tool){
        List<CompareVo<T>> compares = findCompare(inputs);
        tool.shallowCopy(compares);
        MybatisVo<T> mybatisVo =  MybatisVo.of(compares);
        dirtySave(mybatisVo); //여기서는 I/U 만 된다. D는 안됨
        return mybatisVo;
    }*/

    /**
     * 전체를 별도로 모두 로드 후 더티처리할때 사용하기
     * (없는애들은 삭제해주는 로직이 필요할때의 경우 전체를 먼저 로드해야 한다.)
     * exists에는 있으나 inputs에는 없는건 알아서 삭제로 처리해준다. (DB에서 삭제 or 플래그처리 등등)
     *
     * 주의!!!!  원래 서버 - 인풋 일케 넣었는데 여기서 거꾸로다. ㅠㅠ 나중에 수정하자
     *  */
    /*public MybatisVo<T> dirtyCheckAndSave(Collection<T> servers,Collection<T> inputs,CompareTool tool){
        List<CompareVo<T>> compares = Lists.newArrayList();
        Map<ID,T> existsMap = CollectionUtil.persistableCollectionToMap(servers);
        for(T input : inputs){
            T server = existsMap.remove(input.getId());
            compares.add(CompareVo.of(server, input));
        }
        tool.shallowCopy(compares);
        MybatisVo<T> mybatisVo =  MybatisVo.of(compares);
        mybatisVo.setDeleteList(Lists.newArrayList(existsMap.values()));
        dirtySave(mybatisVo);
        return mybatisVo;
    }*/

    /**
     * dirtyCheckAndSave 와 동일 기능을 하지만 DB 액션은 안취한다.
     */
    /*public MybatisVo<T> dirtyCheck(Collection<T> servers,Collection<T> inputs,CompareTool tool){
        List<CompareVo<T>> compares = Lists.newArrayList();
        Map<ID,T> existsMap = CollectionUtil.persistableCollectionToMap(servers);
        for(T input : inputs){
            T server = existsMap.remove(input.getId());
            compares.add(CompareVo.of(server, input));
        }
        tool.shallowCopy(compares);
        MybatisVo<T> mybatisVo =  MybatisVo.of(compares);
        mybatisVo.setDeleteList(Lists.newArrayList(existsMap.values()));
        return mybatisVo;
    }*/

}
