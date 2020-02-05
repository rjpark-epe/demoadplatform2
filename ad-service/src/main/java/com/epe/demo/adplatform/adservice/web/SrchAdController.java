package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.SrchAdService;
import com.epe.demo.adplatform.domain.cache.adv.CacheAdDetVo;
import com.epe.demo.adplatform.domain.cache.adv.CacheAdVo;
import com.epe.demo.adplatform.domain.rds.ad.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class SrchAdController {

    @Resource
    private SrchAdService srchAdService;

    private static final Gson GSON = buildDefaultGson();
    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Resource private RedisTemplate redisTemplate;

    @GetMapping(value = "/api/adDetInfo/{kwdName}/{adDetId}", produces = "text/plain;charset=UTF-8")
    public String adDetInfo(@PathVariable(value="kwdName") String kwdName,@PathVariable(value="adDetId") Long adDetId){
        CacheAdDetVo cacheAdDetVo = (CacheAdDetVo) redisTemplate.opsForHash().get("SRCH||"+kwdName,adDetId);
        return GSON.toJson(cacheAdDetVo);
    }
    @GetMapping(value = "/api/adInfo/{adId}", produces = "text/plain;charset=UTF-8")
    public String adDetInfo(@PathVariable(value="adId") Long adId){
        CacheAdVo cacheAdVo = (CacheAdVo) redisTemplate.opsForHash().get("AD",adId);
        return GSON.toJson(cacheAdVo);
    }


    @PutMapping(value = "/api/dadUpdate/{dadDetId}")
    public String dadUpdate(@PathVariable(value="dadDetId") Long dadDetId, @RequestBody @Valid DadDetVo dadDetVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(srchAdService.dadUpdate(dadDetId, dadDetVo));;
        stopWatch.stop();
        System.out.println("DAD DET UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }
    @PutMapping(value = "/api/dadBidUpdate/{dadDetId}")
    public String dadBidUpdate(@PathVariable(value="dadDetId") Long dadDetId, @RequestBody @Valid DadDetBidVo dadDetBidVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(srchAdService.dadBidUpdate(dadDetId, dadDetBidVo));
        stopWatch.stop();
        System.out.println("DAD DET BID UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }
    @PutMapping(value = "/api/adUpdate/{adId}")
    public String adUpdate(@PathVariable(value="adId") Long adId, @RequestBody @Valid AdVo adVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(srchAdService.adUpdate(adId, adVo));
        stopWatch.stop();
        System.out.println("AD UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }
    @PutMapping(value = "/api/adBidUpdate/{adId}")
    public String adBidUpdate(@PathVariable(value="adId") Long adId, @RequestBody @Valid AdBidVo adBidVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(srchAdService.adBidUpdate(adId, adBidVo));
        stopWatch.stop();
        System.out.println("AD BID UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }


}
