package com.epe.demo.adplatform.adservice.web;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Map;

@RestController
public class MonitorController {

    private static final Gson GSON = buildDefaultGson();

    public static Gson buildDefaultGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyyMMddHHmmss"); //초까지 나오게 (밀리초 생략)
        return gb.create();
    }

    @GetMapping(value = "/api/monitor", produces = "text/plain;charset=UTF-8")
    public String monitor(){

        /*MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();*/
        /*ManagementFactory.getMemoryPoolMXBeans();*/

        Map<String,Long> map = Maps.newHashMap();
        map.put("free",Runtime.getRuntime().freeMemory());
        map.put("total",Runtime.getRuntime().totalMemory());

        return GSON.toJson(map);
    }

}
