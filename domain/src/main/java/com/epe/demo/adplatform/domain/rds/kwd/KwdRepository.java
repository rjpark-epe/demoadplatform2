package com.epe.demo.adplatform.domain.rds.kwd;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KwdRepository extends MyBatisMainRepository<KwdVo, Long> {

    public List<KwdVo> findListByIndexPrior(int indexPrior){
        return findList("findListByIndexPrior",indexPrior);
    }
}
