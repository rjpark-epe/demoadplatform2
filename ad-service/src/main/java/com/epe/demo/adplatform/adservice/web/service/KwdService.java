package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.CacheAdvVo;
import com.epe.demo.adplatform.domain.cache.adv.CacheKwdVo;
import com.epe.demo.adplatform.domain.enums.adv.AdvRegStatus;
import com.epe.demo.adplatform.domain.rds.adv.AdvRepository;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.rds.kwd.KwdRepository;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import com.epe.demo.adplatform.domain.util.CompareUtil;
import com.google.common.collect.Sets;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class KwdService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private KwdRepository kwdRepository;

    public static final Set<String> KWD_DIRTY =  Sets.newHashSet("sellPossKwdYn");

    public boolean kwdUpdate(Long kwdId, KwdVo kwdVo){
        try {
            KwdVo exists = kwdRepository.findOne(kwdId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(kwdVo, exists, KWD_DIRTY, null);
                kwdRepository.save(kwdVo);
                if (!eq) {
                    CacheKwdVo cacheKwdVo = (CacheKwdVo) redisTemplate.opsForHash().get("KWD", kwdVo.getKwdName());
                    cacheKwdVo.setSellPossKwdYn(kwdVo.getSellPossKwdYn());
                    redisTemplate.opsForHash().put("KWD", kwdVo.getKwdName(), cacheKwdVo);
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}
