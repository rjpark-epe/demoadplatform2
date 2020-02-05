package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.AdvService;
import com.epe.demo.adplatform.adservice.web.service.SrchAdService;
import com.epe.demo.adplatform.domain.cache.adv.CacheAdvVo;
import com.epe.demo.adplatform.domain.rds.ad.DadDetVo;
import com.epe.demo.adplatform.domain.rds.adv.AdvRepository;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class AdvController {

    @Resource
    private AdvService advService;

    private static final Gson GSON = buildDefaultGson();
    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }
    @Resource private RedisTemplate redisTemplate;

    @PutMapping(value = "/api/advUpdate/{advId}")
    public String advUpdate(@PathVariable(value="advId") String advId, @RequestBody @Valid AdvVo advVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(advService.advUpdate(advId,advVo));
        stopWatch.stop();
        System.out.println("ADV UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }

    @GetMapping(value = "/api/advInfo/{advId}")
    public String advInfo(@PathVariable(value="advId") String advId){
        CacheAdvVo cacheAdvVo = (CacheAdvVo)redisTemplate.opsForHash().get("ADV",advId);
        return GSON.toJson(cacheAdvVo);
    }

}
