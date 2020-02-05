package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DadDetBidRepository extends MyBatisMainRepository<DadDetBidVo, Long> {
    public DadDetBidVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
