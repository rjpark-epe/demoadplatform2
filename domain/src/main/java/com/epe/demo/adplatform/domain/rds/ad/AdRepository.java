package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AdRepository extends MyBatisMainRepository<AdVo, Long> {
    public AdVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
