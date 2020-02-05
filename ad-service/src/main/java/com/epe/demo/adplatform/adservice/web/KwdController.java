package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.KwdService;
import com.epe.demo.adplatform.domain.cache.adv.CacheKwdVo;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class KwdController {

    private static final Gson GSON = buildDefaultGson();

    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private KwdService kwdService;

    @RequestMapping(value = "/api/kwdInfo/{kwdName}", method= RequestMethod.GET)
    public String kwdInfo(@PathVariable(value="kwdName") String kwdName){

        CacheKwdVo cacheKwdVo = (CacheKwdVo) redisTemplate.opsForHash().get("KWD",kwdName);
        return GSON.toJson(cacheKwdVo);
    }

    @PutMapping(value = "/api/kwdUpdate/{kwdId}")
    public String kwdUpdate(@PathVariable(value="kwdId") Long kwdId, @RequestBody @Valid KwdVo kwdVo){
        return GSON.toJson(kwdService.kwdUpdate(kwdId,kwdVo));
    }

}
