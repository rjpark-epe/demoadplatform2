package com.epe.demo.adplatform.domain.rds.adv;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvRepository extends MyBatisMainRepository<AdvVo, String> {
    public void findAllWithConfig(ResultHandler<AdvVo> resultHandler) {
        findWithHandler("findAllWithConfig",null,resultHandler);
    }

    public AdvVo findOneRandom() {
        return findOneObject("findOneRandom",null);
    }
}
