package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.rds.agroup.AgroupVo;
import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AdBidRepository extends MyBatisMainRepository<AdBidVo, Long> {
    public AdBidVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
