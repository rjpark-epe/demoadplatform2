package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.AdReturnService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdReturnController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AdReturnService adReturnService;


    private static final Gson GSON = buildDefaultGson();

    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }


    @GetMapping(value = "/api/adReturn/{kwdName}", produces = "text/plain;charset=UTF-8")
    public String adReturn(@PathVariable("kwdName") String kwdName){
        return GSON.toJson(adReturnService.srpAds(kwdName));
    }

}
