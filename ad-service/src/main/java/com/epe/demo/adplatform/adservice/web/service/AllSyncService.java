package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.*;
import com.epe.demo.adplatform.domain.enums.ad.DadCnrStatus;
import com.epe.demo.adplatform.domain.enums.adv.AdvRegStatus;
import com.epe.demo.adplatform.domain.rds.ad.*;
import com.epe.demo.adplatform.domain.rds.adv.AdvRepository;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupRepository;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupVo;
import com.epe.demo.adplatform.domain.rds.item.ItemRepository;
import com.epe.demo.adplatform.domain.rds.item.ItemVo;
import com.epe.demo.adplatform.domain.rds.kwd.KwdRepository;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AllSyncService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Resource private AdvRepository advRepository;
    @Resource private AgroupRepository agroupRepository;
    @Resource private AdRepository adRepository;
    @Resource private AadDetRepository aadDetRepository;
    @Resource private DadDetRepository dadDetRepository;
    @Resource private ItemRepository itemRepository;
    @Resource private KwdRepository kwdRepository;

    @Resource private RedisTemplate redisTemplate;

    public void allSync(){
        LOG.info("step01AdvSync 시작");
        step01AdvSync();
        LOG.info("step01AdvSync 종료");
        LOG.info("step02AgroupSync 시작");
        step02AgroupSync();
        LOG.info("step02AgroupSync 종료");
        LOG.info("step03ItemSync 종료");
        step03ItemSync();
        LOG.info("step03ItemSync 종료");
        LOG.info("step04KwdSync 시작");
        step04KwdSync();
        LOG.info("step04KwdSync 종료");
        LOG.info("step05AdSync 시작");
        step05AdSync();
        LOG.info("step05AdSync 종료");
        LOG.info("step06AadSync 시작");
        step06AadSync();
        LOG.info("step06AadSync 종료");
        LOG.info("step07DadSync 시작");
        step07DadSync();
        LOG.info("step07DadSync 종료");
    }

    public void step01AdvSync(){
        advRepository.findAllWithConfig(new ResultHandler<AdvVo>() {
            @Override
            public void handleResult(ResultContext<? extends AdvVo> resultContext) {
                AdvVo advVo = resultContext.getResultObject();
                CacheAdvVo cacheAdvVo = new CacheAdvVo();
                cacheAdvVo.setAdvId(advVo.getAdvId());
                cacheAdvVo.setAdvBalancePossYn(advVo.getAdvBalancePossYn());
                cacheAdvVo.setAdvBudgetPossYn(advVo.getAdvBudgetPossYn());
                cacheAdvVo.setAdvRegStatusYn(advVo.getAdvRegStatus().equals(AdvRegStatus.NORMAL));
                cacheAdvVo.setAdvUseConfigYn(advVo.getAdvUseConfigYn());
                cacheAdvVo.setAdvImpStrgDow(advVo.getAdvImpStrgDow());
                cacheAdvVo.setAdvImpStrgStartDate(advVo.getAdvImpStrgStartDate());
                cacheAdvVo.setAdvImpStrgEndDate(advVo.getAdvImpStrgEndDate());
                redisTemplate.opsForHash().put("ADV",cacheAdvVo.getAdvId(),cacheAdvVo);
            }
        });

    }

    public void step02AgroupSync(){
        agroupRepository.findWithHandler("findAll", null, new ResultHandler<AgroupVo>() {
            @Override
            public void handleResult(ResultContext<? extends AgroupVo> resultContext) {
                AgroupVo agroupVo = resultContext.getResultObject();
                CacheSrchAgroupVo cacheSrchAgroupVo = new CacheSrchAgroupVo();
                cacheSrchAgroupVo.setAdvId(agroupVo.getAdvId());
                cacheSrchAgroupVo.setAgroupId(agroupVo.getAgroupId());
                cacheSrchAgroupVo.setAgroupBudgetPossyn(agroupVo.getAgroupBudgetPossYn());
                cacheSrchAgroupVo.setAgroupUseConfigYn(agroupVo.getAgroupUseConfigYn());
                cacheSrchAgroupVo.setAgroupImpStrgDow(agroupVo.getAgroupImpStrgDow());
                cacheSrchAgroupVo.setAgroupImpStrgStartDate(agroupVo.getAgroupImpStrgStartDate());
                cacheSrchAgroupVo.setAgroupImpStrgEndDate(agroupVo.getAgroupImpStrgEndDate());
                cacheSrchAgroupVo.setAgroupActYn(agroupVo.getAgroupActYn());
                redisTemplate.opsForHash().put("AGROUP",cacheSrchAgroupVo.getAgroupId(),cacheSrchAgroupVo);
            }
        });
    }

    public void step03ItemSync(){
        itemRepository.findWithHandler("findListByRegistAd", null, new ResultHandler<ItemVo>() {
            @Override
            public void handleResult(ResultContext<? extends ItemVo> resultContext) {
                ItemVo itemVo = resultContext.getResultObject();
                CacheItemVo cacheItemVo = new CacheItemVo();
                cacheItemVo.setItemId(itemVo.getItemId());
                cacheItemVo.setItemNo(itemVo.getItemNo());
                cacheItemVo.setItemName(itemVo.getItemName());
                cacheItemVo.setItemActYn(itemVo.getItemActYn());
                redisTemplate.opsForHash().put("ITEM",cacheItemVo.getItemId(),cacheItemVo);
            }
        });
    }

    public void step04KwdSync(){
        kwdRepository.findWithHandler("findAll", null, new ResultHandler<KwdVo>() {
            @Override
            public void handleResult(ResultContext<? extends KwdVo> resultContext) {
                KwdVo kwd = resultContext.getResultObject();
                CacheKwdVo cacheKwdVo = new CacheKwdVo();
                cacheKwdVo.setKwdId(kwd.getKwdId());
                cacheKwdVo.setKwdName(kwd.getKwdName());
                cacheKwdVo.setSellPossKwdYn(kwd.getSellPossKwdYn());
                redisTemplate.opsForHash().put("KWD",cacheKwdVo.getKwdName(),cacheKwdVo);
            }
        });
    }

    public void step05AdSync(){
        adRepository.findWithHandler("findAll", null, new ResultHandler<AdVo>() {
            @Override
            public void handleResult(ResultContext<? extends AdVo> resultContext) {
                AdVo adVo = resultContext.getResultObject();
                CacheAdVo cacheAdVo = new CacheAdVo();
                cacheAdVo.setAdvId(adVo.getAdvId());
                cacheAdVo.setAgroupId(adVo.getAgroupId());
                cacheAdVo.setAdId(adVo.getAdId());
                cacheAdVo.setAdUseConfigYn(adVo.getAdUseConfigYn());
                cacheAdVo.setAdActYn(adVo.getAdActYn());
                redisTemplate.opsForHash().put("AD", cacheAdVo.getAdId(), cacheAdVo);
            }
        });
    }

    public void step06AadSync(){
        aadDetRepository.findWithHandler("findAllWithAdAndKwd", null, new ResultHandler<AadDetVo>() {
            @Override
            public void handleResult(ResultContext<? extends AadDetVo> resultContext) {
                AadDetVo aadDetVo = resultContext.getResultObject();
                CacheAdDetVo cacheAdDetVo = new CacheAdDetVo();
                cacheAdDetVo.setAdId(aadDetVo.getAd().getAdId());
                cacheAdDetVo.setAgroupId(aadDetVo.getAd().getAgroupId());
                cacheAdDetVo.setAdvId(aadDetVo.getAd().getAdvId());
                cacheAdDetVo.setItemId(aadDetVo.getAd().getItemId());
                cacheAdDetVo.setKwdId(aadDetVo.getKwd().getKwdId());
                cacheAdDetVo.setKwdName(aadDetVo.getKwd().getKwdName());
                cacheAdDetVo.setAdDetId(aadDetVo.getAadDetId());
                cacheAdDetVo.setAdDetCnrStatusYn(true);
                cacheAdDetVo.setAdDetActYn(true);
                cacheAdDetVo.setAdDetUseConfigYn(true);
                cacheAdDetVo.setBidCost(aadDetVo.getAdBid().getBidCost());
                cacheAdDetVo.setBidTime(new DateTime(aadDetVo.getAdBid().getBidTime()).getMillis());

                redisTemplate.opsForHash().put("SRCH||"+aadDetVo.getKwd().getKwdName(), cacheAdDetVo.getAdDetId(), cacheAdDetVo);
            }
        });
    }

    public void step07DadSync(){
        dadDetRepository.findWithHandler("findAllWithAdAndKwd", null, new ResultHandler<DadDetVo>() {
            @Override
            public void handleResult(ResultContext<? extends DadDetVo> resultContext) {
                DadDetVo dadDetVo = resultContext.getResultObject();
                CacheAdDetVo cacheAdDetVo = new CacheAdDetVo();
                cacheAdDetVo.setAdId(dadDetVo.getAd().getAdId());
                cacheAdDetVo.setAgroupId(dadDetVo.getAd().getAgroupId());
                cacheAdDetVo.setAdvId(dadDetVo.getAd().getAdvId());
                cacheAdDetVo.setItemId(dadDetVo.getAd().getItemId());
                cacheAdDetVo.setKwdId(dadDetVo.getKwd().getKwdId());
                cacheAdDetVo.setKwdName(dadDetVo.getKwd().getKwdName());
                cacheAdDetVo.setAdDetId(dadDetVo.getDadDetId());
                cacheAdDetVo.setAdDetCnrStatusYn(dadDetVo.getDadCnrStatus().equals(DadCnrStatus.APPROVAL));
                cacheAdDetVo.setAdDetActYn(dadDetVo.getDadActYn());
                cacheAdDetVo.setAdDetUseConfigYn(dadDetVo.getDadUseConfigYn());
                cacheAdDetVo.setBidCost(dadDetVo.getDadDetBid().getBidCost());
                cacheAdDetVo.setBidTime(new DateTime(dadDetVo.getDadDetBid().getBidTime()).getMillis());

                redisTemplate.opsForHash().put("SRCH||"+dadDetVo.getKwd().getKwdName(), cacheAdDetVo.getAdDetId(), cacheAdDetVo);
            }
        });
    }

    public void flushCache(){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }
}
