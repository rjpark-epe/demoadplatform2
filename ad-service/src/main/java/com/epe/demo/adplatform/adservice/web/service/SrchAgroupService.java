package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.CacheSrchAgroupVo;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupRepository;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupVo;
import com.epe.demo.adplatform.domain.util.CompareUtil;
import com.google.common.collect.Sets;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class SrchAgroupService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AgroupRepository agroupRepository;

    public static final Set<String> AGROUP_DIRTY =  Sets.newHashSet("agroupBudgetPossYn","agroupUseConfigYn","agroupImpStrgDow","agroupImpStrgStartDate","agroupImpStrgEndDate","agroupActYn");

    public boolean agroupUpdate(Long agroupId, AgroupVo agroupVo){
        try {
            AgroupVo exists = agroupRepository.findOne(agroupId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(agroupVo, exists, AGROUP_DIRTY, null);
                agroupRepository.save(agroupVo);
                if (!eq) {
                    CacheSrchAgroupVo cacheSrchAgroupVo = (CacheSrchAgroupVo) redisTemplate.opsForHash().get("AGROUP", agroupId);
                    cacheSrchAgroupVo.setAgroupBudgetPossyn(agroupVo.getAgroupBudgetPossYn());
                    cacheSrchAgroupVo.setAgroupUseConfigYn(agroupVo.getAgroupUseConfigYn());
                    cacheSrchAgroupVo.setAgroupImpStrgDow(agroupVo.getAgroupImpStrgDow());
                    cacheSrchAgroupVo.setAgroupImpStrgStartDate(agroupVo.getAgroupImpStrgStartDate());
                    cacheSrchAgroupVo.setAgroupImpStrgEndDate(agroupVo.getAgroupImpStrgEndDate());
                    cacheSrchAgroupVo.setAgroupActYn(agroupVo.getAgroupActYn());
                    redisTemplate.opsForHash().put("AGROUP", agroupId, cacheSrchAgroupVo);
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}
