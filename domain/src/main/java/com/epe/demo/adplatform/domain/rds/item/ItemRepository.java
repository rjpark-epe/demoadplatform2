package com.epe.demo.adplatform.domain.rds.item;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository extends MyBatisMainRepository<ItemVo, Long> {
    public List<ItemVo> findListByRegistAd() {
        return findList("findListByRegistAd",null);
    }
}
