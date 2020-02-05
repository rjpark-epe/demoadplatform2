package com.epe.demo.adplatform.domain.rds.agroup;

import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AgroupRepository extends MyBatisMainRepository<AgroupVo, Long> {

    public AgroupVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
