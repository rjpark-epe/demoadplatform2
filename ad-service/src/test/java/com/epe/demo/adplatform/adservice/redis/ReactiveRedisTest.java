package com.epe.demo.adplatform.adservice.redis;

import com.epe.demo.adplatform.domain.rds.index.IndexVo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@SpringBootTest
public class ReactiveRedisTest {

    @Resource
    private ReactiveRedisTemplate reactiveRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    private Logger log = LoggerFactory.getLogger(ReactiveRedisTest.class);

    @Test
    public void test() {

        /*IndexVo index1 = new IndexVo();
        IndexVo index2 = new IndexVo();
        IndexVo index3 = new IndexVo();
        IndexVo index4 = new IndexVo();
        IndexVo index5 = new IndexVo();
        IndexVo index6 = new IndexVo();
        IndexVo index7 = new IndexVo();
        IndexVo index8 = new IndexVo();
        IndexVo index9 = new IndexVo();
        IndexVo index10 = new IndexVo();
        IndexVo index11 = new IndexVo();
        IndexVo index12 = new IndexVo();

        index1.setVenderId(1L);
        index1.setAdvId("adv1");
        index1.setKwdId(2L);
        index1.setAdId(101L);
        index1.setBidCost(1010L);
        index1.setUseYn(true);

        index2.setVenderId(1L);
        index2.setAdvId("adv1");
        index2.setKwdId(2L);
        index2.setAdId(102L);
        index2.setBidCost(1020L);
        index2.setUseYn(true);

        index3.setVenderId(1L);
        index3.setAdvId("adv1");
        index3.setKwdId(2L);
        index3.setAdId(103L);
        index3.setBidCost(1030L);
        index3.setUseYn(true);

        index4.setVenderId(1L);
        index4.setAdvId("adv1");
        index4.setKwdId(2L);
        index4.setAdId(104L);
        index4.setBidCost(1040L);
        index4.setUseYn(true);

        index5.setVenderId(1L);
        index5.setAdvId("adv1");
        index5.setKwdId(2L);
        index5.setAdId(105L);
        index5.setBidCost(1050L);
        index5.setUseYn(true);

        index6.setVenderId(1L);
        index6.setAdvId("adv1");
        index6.setKwdId(2L);
        index6.setAdId(106L);
        index6.setBidCost(1060L);
        index6.setUseYn(true);

        index7.setVenderId(1L);
        index7.setAdvId("adv1");
        index7.setKwdId(2L);
        index7.setAdId(107L);
        index7.setBidCost(1070L);
        index7.setUseYn(true);

        index8.setVenderId(1L);
        index8.setAdvId("adv1");
        index8.setKwdId(2L);
        index8.setAdId(108L);
        index8.setBidCost(1080L);
        index8.setUseYn(true);

        index9.setVenderId(1L);
        index9.setAdvId("adv1");
        index9.setKwdId(2L);
        index9.setAdId(109L);
        index9.setBidCost(1090L);
        index9.setUseYn(true);

        index10.setVenderId(1L);
        index10.setAdvId("adv1");
        index10.setKwdId(2L);
        index10.setAdId(110L);
        index10.setBidCost(1100L);
        index10.setUseYn(true);

        index11.setVenderId(1L);
        index11.setAdvId("adv1");
        index11.setKwdId(2L);
        index11.setAdId(111L);
        index11.setBidCost(1110L);
        index11.setUseYn(true);

        index12.setVenderId(1L);
        index12.setAdvId("adv1");
        index12.setKwdId(2L);
        index12.setAdId(112L);
        index12.setBidCost(1120L);
        index12.setUseYn(true);

        String key = String.valueOf(index1.getVenderId()+"||"+ index1.getKwdId());

       *//* Map<String,IndexVo> indexMap = new HashMap<>();
        indexMap.put(String.valueOf(index1.getId()),index1);
        indexMap.put(String.valueOf(index2.getId()),index2);
        indexMap.put(String.valueOf(index3.getId()),index3);
        indexMap.put(String.valueOf(index4.getId()),index4);
        indexMap.put(String.valueOf(index5.getId()),index5);
        indexMap.put(String.valueOf(index6.getId()),index6);
        indexMap.put(String.valueOf(index7.getId()),index7);
        indexMap.put(String.valueOf(index8.getId()),index8);
        indexMap.put(String.valueOf(index9.getId()),index9);
        indexMap.put(String.valueOf(index10.getId()),index10);
        indexMap.put(String.valueOf(index11.getId()),index11);
        indexMap.put(String.valueOf(index12.getId()),index12);

        redisTemplate.opsForHash().putAll(key,indexMap);*//*
        redisTemplate.opsForHash().put(key,index1.getId(),index1);
        redisTemplate.opsForHash().put(key,index2.getId(),index2);
        redisTemplate.opsForHash().put(key,index3.getId(),index3);
        redisTemplate.opsForHash().put(key,index4.getId(),index4);
        redisTemplate.opsForHash().put(key,index5.getId(),index5);
        redisTemplate.opsForHash().put(key,index6.getId(),index6);
        redisTemplate.opsForHash().put(key,index7.getId(),index7);
        redisTemplate.opsForHash().put(key,index8.getId(),index8);
        redisTemplate.opsForHash().put(key,index9.getId(),index9);
        redisTemplate.opsForHash().put(key,index10.getId(),index10);
        redisTemplate.opsForHash().put(key,index11.getId(),index11);
        redisTemplate.opsForHash().put(key,index12.getId(),index12);

        log.info("{}",redisTemplate.opsForHash().values(key));

        IndexVo index = (IndexVo) redisTemplate.opsForHash().get(key, index3.getId());

        log.info("{}",index);

        index.setBidCost(30000L);
        redisTemplate.opsForHash().put(key, index3.getId(),index);

        log.info("{}",redisTemplate.opsForHash().values(key));*/

        String key = String.valueOf(1L+"||"+2L);

        IndexVo index = (IndexVo) redisTemplate.opsForHash().get(key, 103L);

        log.info("{}",index);
    }


}
