package com.epe.demo.adplatform.domain.rds.index;

import com.epe.demo.adplatform.domain.repository.MyBatisMainRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IndexRepository extends MyBatisMainRepository<IndexVo, Long> {
}
