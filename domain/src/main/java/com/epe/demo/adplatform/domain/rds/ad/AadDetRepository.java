package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import com.epe.demo.adplatform.domain.repository.PageAware;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AadDetRepository extends MyBatisMainRepository<AadDetVo, Long> {

    public AadDetVo findOneWithAdAndKwd(Long adDetId){
        return findOneObject("findOneWithAdAndKwd",adDetId);
    }

    public List<AadDetVo> findAllWithAdAndKwd(){
        return findList("findAllWithAdAndKwd", null);
    }

    public Page<AadDetVo> findPageWithAdAndKwd(PageAware page){
        return findPage("findPageWithAdAndKwd",page);
    }

    public List<AadDetVo> findListByAdId(Long adId) { return findList("findListByAdId",adId); }

    public List<AadDetVo> findListWithKwdByAdId(Long adId) { return findList("findListWithKwdByAdId",adId); }
}
