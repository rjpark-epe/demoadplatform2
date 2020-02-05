package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.CacheAdDetVo;
import com.epe.demo.adplatform.domain.cache.adv.CacheAdVo;
import com.epe.demo.adplatform.domain.enums.ad.DadCnrStatus;
import com.epe.demo.adplatform.domain.rds.ad.*;
import com.epe.demo.adplatform.domain.util.CompareUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SrchAdService {

    @Resource
    private AdRepository adRepository;
    @Resource
    private AdBidRepository adBidRepository;
    @Resource
    private AadDetRepository aadDetRepository;
    @Resource
    private DadDetRepository dadDetRepository;
    @Resource
    private DadDetBidRepository dadDetBidRepository;
    @Resource
    private RedisTemplate redisTemplate;

    public static final Set<String> DAD_DET_DIRTY =  Sets.newHashSet("dadCnrStatus","dadUseConfigYn","dadActYn");

    public boolean dadUpdate(Long dadDetId, DadDetVo dadDetVo){
        try {
            DadDetVo exists = dadDetRepository.findOne(dadDetId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(dadDetVo, exists, DAD_DET_DIRTY, null);
                dadDetRepository.save(dadDetVo);
                if (!eq) {
                    CacheAdDetVo cacheAdDetVo = (CacheAdDetVo) redisTemplate.opsForHash().get("SRCH||" + exists.getKwd().getKwdName(), dadDetId);
                    cacheAdDetVo.setAdDetUseConfigYn(dadDetVo.getDadUseConfigYn());
                    cacheAdDetVo.setAdDetActYn(dadDetVo.getDadActYn());
                    cacheAdDetVo.setAdDetCnrStatusYn(DadCnrStatus.APPROVAL.equals(dadDetVo.getDadCnrStatus()));
                    redisTemplate.opsForHash().put("SRCH||" + exists.getKwd().getKwdName(), dadDetId, cacheAdDetVo);
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static final Set<String> BID_COST_DIRTY =  Sets.newHashSet("bidCost","bidTime");

    public boolean dadBidUpdate(Long dadDetId, DadDetBidVo dadDetBidVo){
        try {
            DadDetBidVo exists = dadDetBidRepository.findOne(dadDetId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(dadDetBidVo, exists, BID_COST_DIRTY, null);
                dadDetBidRepository.save(dadDetBidVo);
                if (!eq) {
                    DadDetVo dadDetVo = dadDetRepository.findOne(dadDetId);
                    CacheAdDetVo cacheAdDetVo = (CacheAdDetVo) redisTemplate.opsForHash().get("SRCH||" + dadDetVo.getKwd().getKwdName(), dadDetId);
                    cacheAdDetVo.setBidCost(dadDetBidVo.getBidCost());
                    cacheAdDetVo.setBidTime(new DateTime(dadDetBidVo.getBidTime()).getMillis());
                    redisTemplate.opsForHash().put("SRCH||" + dadDetVo.getKwd().getKwdName(), dadDetId, cacheAdDetVo);
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean adBidUpdate(Long adId, AdBidVo adBidVo){
        try {
            AdBidVo exists = adBidRepository.findOne(adId);
            if (exists != null) {
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(adBidVo, exists, BID_COST_DIRTY, null);
                adBidRepository.save(adBidVo);
                if (!eq) {
                    List<AadDetVo> aadDetVoList = aadDetRepository.findListWithKwdByAdId(adId);
                    for(AadDetVo aadDetVo : aadDetVoList){
                        CacheAdDetVo cacheAdDetVo = (CacheAdDetVo) redisTemplate.opsForHash().get("SRCH||" + aadDetVo.getKwd().getKwdName(), aadDetVo.getAadDetId());
                        cacheAdDetVo.setBidCost(adBidVo.getBidCost());
                        cacheAdDetVo.setBidTime(new DateTime(adBidVo.getBidTime()).getMillis());
                        redisTemplate.opsForHash().put("SRCH||" + aadDetVo.getKwd().getKwdName(), aadDetVo.getAadDetId(), cacheAdDetVo);
                    }
                }
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static final Set<String> AD_DIRTY =  Sets.newHashSet("adUseConfigYn","adActYn");

    public boolean adUpdate(Long adId, AdVo adVo) {
        try {
            AdVo exists = adRepository.findOne(adId);
            if(exists != null){
                boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(adVo, exists, AD_DIRTY, null);
                adRepository.save(adVo);
                if (!eq) {
                    CacheAdVo cacheAdVo = (CacheAdVo) redisTemplate.opsForHash().get("AD", adId);
                    cacheAdVo.setAdUseConfigYn(adVo.getAdUseConfigYn());
                    cacheAdVo.setAdActYn(adVo.getAdActYn());
                    redisTemplate.opsForHash().put("AD", adId, cacheAdVo);
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
