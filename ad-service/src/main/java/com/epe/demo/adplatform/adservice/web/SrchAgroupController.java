package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.SrchAgroupService;
import com.epe.demo.adplatform.domain.cache.adv.CacheAdDetVo;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupVo;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class SrchAgroupController {

    private static final Gson GSON = buildDefaultGson();

    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }

    @Resource private RedisTemplate redisTemplate;

    @Resource
    private SrchAgroupService srchAgroupService;

    @PutMapping(value = "/api/agroupUpdate/{agroupId}")
    public String agroupUpdate(@PathVariable(value="agroupId") Long agroupId, @RequestBody @Valid AgroupVo agroupVo){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String res = GSON.toJson(srchAgroupService.agroupUpdate(agroupId,agroupVo));
        stopWatch.stop();
        System.out.println("Agroup UPDATE 걸린시간 : " + stopWatch.getTotalTimeMillis());
        return res;
    }

    @GetMapping(value = "/api/agroupInfo/{agroupId}", produces = "text/plain;charset=UTF-8")
    public String agroupInfo(@PathVariable(value="agroupId") Long agroupId){
        CacheAdDetVo cacheAdDetVo = (CacheAdDetVo) redisTemplate.opsForHash().get("AGROUP",agroupId);
        return GSON.toJson(cacheAdDetVo);
    }
}
