package com.epe.demo.adplatform.adservice.web.service;

import com.epe.demo.adplatform.domain.cache.adv.*;
import com.epe.demo.adplatform.domain.util.CompareUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdReturnService {

    @Resource
    RedisTemplate redisTemplate;

    public static final Comparator<CacheAdDetVo> SRP_SORT = new Comparator<CacheAdDetVo>(){
        @Override
        public int compare(CacheAdDetVo a, CacheAdDetVo b) {
            int compare = CompareUtil.compareTo(a.getBidCost(), b.getBidCost(),false);
            if(compare==0) compare =  CompareUtil.compareTo(a.getBidTime(), b.getBidTime(),true);
            return compare;
        }
    };

    public List<CacheAdDetVo> srpAds(String kwdName){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CacheKwdVo cacheKwdVo = (CacheKwdVo)redisTemplate.opsForHash().get("KWD",kwdName);
        List<CacheAdDetVo> list;
        if(!cacheKwdVo.isSellPossKwdYn()){
            list = Lists.newArrayList();
        }else{
            list = getCacheVo(kwdName,0,5);
        }
        stopWatch.stop();
        /*System.out.println("걸린시간 : " + stopWatch.getTotalTimeMillis());*/
        return list;
    }

    public List<CacheAdDetVo> getCacheVo(String kwdName, int startIdx, int endIdx){
        List<CacheAdDetVo> list = toList(redisTemplate.opsForHash().entries("SRCH||"+kwdName).values());
        Collections.sort(list, SRP_SORT);
        list = filter(list);
        if(list.size() > startIdx+endIdx)
            return list.subList(startIdx, endIdx);
        else
            return list.subList(startIdx, list.size()-startIdx);
    }

    public List<CacheAdDetVo> filter(List<CacheAdDetVo> list){
        List<String> advIds = Lists.newArrayList();
        List<Long> agroupIds = Lists.newArrayList();
        List<Long> adIds = Lists.newArrayList();
        for(CacheAdDetVo cacheAdDetVo : list){
            advIds.add(cacheAdDetVo.getAdvId());
            agroupIds.add(cacheAdDetVo.getAgroupId());
            adIds.add(cacheAdDetVo.getAdId());
        }

        Map<String,CacheAdvVo> advMaps = getAdvMap(advIds);
        Map<Long,CacheSrchAgroupVo> agroupMaps = getAgroupMap(agroupIds);
        Map<Long,CacheAdVo> adMaps = getAdMap(adIds);

        ListIterator<CacheAdDetVo> i = list.listIterator(list.size());
        CacheAdDetVo before = null;
        while(i.hasPrevious()){
            CacheAdDetVo vo = i.previous();
            CacheAdvVo adv = advMaps.get(vo.getAdvId());
            CacheSrchAgroupVo agroup = agroupMaps.get(vo.getAgroupId());
            CacheAdVo ad = adMaps.get(vo.getAdId());
            if(
                    !adv.isAdvBalancePossYn() || !adv.isAdvBudgetPossYn() || !adv.isAdvRegStatusYn() || !adv.isAdvUseConfigYn() || !impStrgCheck(adv.getAdvImpStrgDow(),adv.getAdvImpStrgStartDate(),adv.getAdvImpStrgEndDate())
                    || !agroup.isAgroupActYn() || !agroup.isAgroupBudgetPossyn() || !agroup.isAgroupUseConfigYn() || !impStrgCheck(agroup.getAgroupImpStrgDow(),agroup.getAgroupImpStrgStartDate(),agroup.getAgroupImpStrgEndDate())
                    || !ad.getAdUseConfigYn() || !ad.getAdActYn()
                    || !vo.getAdDetActYn() || !vo.getAdDetCnrStatusYn() || !vo.getAdDetUseConfigYn()

            ){
                i.remove();
            }else{
                long cpc = 0L;
                //cpc 로직 추가
                before = vo;
                if(before == null) cpc = 90L;
                else cpc =  before.getBidCost()+10L;

                if(cpc > vo.getBidCost()) cpc = vo.getBidCost();
                vo.setCpc(cpc);
                vo.setEcpm(calEcpm(vo));
                before = vo;
            }
        }

        return list;
    }

    public Map<String,CacheAdvVo> getAdvMap(List<String> advIds){
        List<CacheAdvVo>  advs = redisTemplate.opsForHash().multiGet("ADV",advIds);
        Map<String,CacheAdvVo> advMaps = Maps.newHashMap();
        for(CacheAdvVo adv : advs){
            advMaps.put(adv.getAdvId(),adv);
        }
        return advMaps;
    }

    public Map<Long,CacheSrchAgroupVo> getAgroupMap(List<Long> agroupIds){
        List<CacheSrchAgroupVo> agroups = redisTemplate.opsForHash().multiGet("AGROUP",agroupIds);
        Map<Long,CacheSrchAgroupVo> agroupMaps = Maps.newHashMap();
        for(CacheSrchAgroupVo agroup : agroups){
            agroupMaps.put(agroup.getAgroupId(),agroup);
        }
        return agroupMaps;
    }

    private Map<Long, CacheAdVo> getAdMap(List<Long> adIds) {
        List<CacheAdVo> ads = redisTemplate.opsForHash().multiGet("AD",adIds);
        Map<Long,CacheAdVo> adMaps = Maps.newHashMap();
        for(CacheAdVo ad : ads){
            adMaps.put(ad.getAdId(),ad);
        }
        return adMaps;
    }

    public static <T> List<T> toList(Iterable<T> collection){
        if(collection instanceof List) return (List)collection;
        return Lists.newArrayList(collection);
    }
    public static final DateTimeFormatter YMD = DateTimeFormat.forPattern("yyyyMMdd");
    private boolean impStrgCheck(String impStrgDow, String impStartDate, String impEndDate) {
        DateTime now = new DateTime();
        if(impStartDate != null) {
            DateTime impStart = DateTime.parse(impStartDate,YMD);
            impStart = impStart.minusHours(impStart.getHourOfDay()).minusMinutes(impStart.getMinuteOfHour()).minusSeconds(impStart.getSecondOfMinute());
            if(now.isBefore(impStart))
                return false;
        }
        if(impEndDate != null) {
            DateTime impEnd = DateTime.parse(impEndDate,YMD);
            impEnd = impEnd.minusHours(impEnd.getHourOfDay()).minusMinutes(impEnd.getMinuteOfHour()).minusSeconds(impEnd.getSecondOfMinute()).plusDays(1);
            if(now.isAfter(impEnd))
                return false;
        }
        if(impStrgDow != null) {
            int dowIdx = now.getDayOfWeek() == 7 ? 0 : now.getDayOfWeek();
            char[] strgDowArr = impStrgDow.toCharArray();
            if(strgDowArr[dowIdx] == '0')
                return false;
        }
        return true;
    }

    private Double calEcpm(CacheAdDetVo cacheAdDetVo) {
        return round(cacheAdDetVo.getBidCost() * 1, 2);
    }

    private double round(double value, int i) {
        double buff = Math.pow(10,i);
        long result = Math.round(value * buff);
        return result / buff;
    }
}
