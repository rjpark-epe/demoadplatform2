package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import com.epe.demo.adplatform.domain.repository.PageAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DadDetRepository extends MyBatisMainRepository<DadDetVo, Long> {

    public DadDetVo findOneWithAdAndKwd(Long adDetId){
        return findOneObject("findOneWithAdAndKwd", adDetId);
    }

    public List<DadDetVo> findAllWithAdAndKwd(){
        return findList("findAllWithAdAndKwd", null);
    }

    public Page<DadDetVo> findPageWithAdAndKwd(PageAware page){
        return findPage("findPageWithAdAndKwd",page);
    }

    public List<DadDetVo> findListByAdId(Long adId) { return findList("findListByAdId",adId); }

    public DadDetVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
