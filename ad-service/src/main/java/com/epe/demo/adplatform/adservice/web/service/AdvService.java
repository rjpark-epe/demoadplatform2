package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.CacheAdvVo;
import com.epe.demo.adplatform.domain.cache.adv.CacheSrchAgroupVo;
import com.epe.demo.adplatform.domain.enums.adv.AdvRegStatus;
import com.epe.demo.adplatform.domain.rds.adv.AdvRepository;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.util.CompareUtil;
import com.google.common.collect.Sets;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class AdvService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AdvRepository advRepository;

    public static final Set<String> ADV_DIRTY =  Sets.newHashSet("advRegStatus","advUseConfigYn","advBudgetPossYn","advBalancePossYn","advImpStrgDow","advImpStrgStartDate","advImpStrgEndDate");

    public boolean advUpdate(String advId, AdvVo advVo){
        try {
            AdvVo exists = advRepository.findOne(advId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(advVo, exists, ADV_DIRTY, null);
                advRepository.save(advVo);
                if (!eq) {
                    CacheAdvVo cacheAdvVo = (CacheAdvVo) redisTemplate.opsForHash().get("ADV", advId);
                    cacheAdvVo.setAdvRegStatusYn(advVo.getAdvRegStatus().equals(AdvRegStatus.NORMAL));
                    cacheAdvVo.setAdvUseConfigYn(advVo.getAdvUseConfigYn());
                    cacheAdvVo.setAdvBudgetPossYn(advVo.getAdvBudgetPossYn());
                    cacheAdvVo.setAdvBalancePossYn(advVo.getAdvBalancePossYn());
                    cacheAdvVo.setAdvImpStrgDow(advVo.getAdvImpStrgDow());
                    cacheAdvVo.setAdvImpStrgStartDate(advVo.getAdvImpStrgStartDate());
                    cacheAdvVo.setAdvImpStrgEndDate(advVo.getAdvImpStrgEndDate());
                    redisTemplate.opsForHash().put("ADV", advId, cacheAdvVo);
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}
