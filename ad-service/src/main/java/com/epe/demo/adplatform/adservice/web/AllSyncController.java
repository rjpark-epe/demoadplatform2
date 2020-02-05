package com.epe.demo.adplatform.adservice.web;

import com.epe.demo.adplatform.adservice.web.service.AllSyncService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AllSyncController {

    @Resource private AllSyncService allSyncService;

    @RequestMapping(value = "/api/allSync", method=RequestMethod.POST)
    public String allSync(){
        allSyncService.allSync();
        return "All Sync Complete";
    }
    @RequestMapping(value = "/api/advSync", method=RequestMethod.POST)
    public String advSync(){
        allSyncService.step01AdvSync();
        return "Adv Sync Complete";
    }
    @RequestMapping(value = "/api/agroupSync", method=RequestMethod.POST)
    public String agroupSync(){
        allSyncService.step02AgroupSync();
        return "Agroup Sync Complete";
    }
    @RequestMapping(value = "/api/itemSync", method=RequestMethod.POST)
    public String itemSync(){
        allSyncService.step03ItemSync();
        return "Item Sync Complete";
    }
    @RequestMapping(value = "/api/kwdSync", method=RequestMethod.POST)
    public String kwdSync(){
        allSyncService.step04KwdSync();
        return "Kwd Sync Complete";
    }
    @RequestMapping(value = "/api/adSync", method=RequestMethod.POST)
    public String adSync(){
        allSyncService.step05AdSync();
        return "Ad Sync Complete";
    }
    @RequestMapping(value = "/api/aadSync", method=RequestMethod.POST)
    public String aadSync(){
        allSyncService.step06AadSync();
        return "Aad Sync Complete";
    }
    @RequestMapping(value = "/api/dadSync", method=RequestMethod.POST)
    public String dadSync(){
        allSyncService.step07DadSync();
        return "Dad Sync Complete";
    }
    @RequestMapping(value = "/api/flushAll/{key}", method=RequestMethod.DELETE)
    public String flushAll(@PathVariable(value="key") String key){
        if(key.equals("DELETE ALL")){
            allSyncService.flushCache();
        }
        return "OK";
    }
}
