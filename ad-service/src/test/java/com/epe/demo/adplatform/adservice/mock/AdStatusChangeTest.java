package com.epe.demo.adplatform.adservice.mock;


import com.epe.demo.adplatform.adservice.AdServiceApplication;
import com.epe.demo.adplatform.domain.enums.ad.DadCnrStatus;
import com.epe.demo.adplatform.domain.rds.ad.*;
import com.epe.demo.adplatform.domain.rds.adv.AdvRepository;
import com.epe.demo.adplatform.domain.rds.adv.AdvVo;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupRepository;
import com.epe.demo.adplatform.domain.rds.agroup.AgroupVo;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({TestDocConfig.class})
public class AdStatusChangeTest{

    @Resource
    private AdvRepository advRepository;

    @Resource
    private AgroupRepository agroupRepository;

    @Resource
    private AdRepository adRepository;

    @Resource
    private AdBidRepository adBidRepository;

    @Resource
    private DadDetRepository dadDetRepository;

    @Resource
    private DadDetBidRepository dadDetBidRepository;

    private static RestTemplate restTemplate;
    private static String baseUrl;

    @BeforeAll
    public static void setup(){
        restTemplate = new RestTemplate();
        baseUrl = "http://13.125.202.127:5050";
    }

    @Test
    public void test() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(100);
        executor.initialize();

        List<String> events = Lists.newArrayList("ADV","AGROUP","AD", "AD_BID", "DAD", "DAD_BID");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int random = (int) (Math.random() * 6);
                    if (events.get(random).equals("ADV")) {
                        AdvVo adv = advRepository.findOneRandom();
                        int random2 = (int) (Math.random() * 3);
                        if (random2 == 0) {
                            adv.setAdvUseConfigYn(!adv.getAdvUseConfigYn());
                        } else if (random2 == 1) {
                            adv.setAdvBudgetPossYn(!adv.getAdvBudgetPossYn());
                        } else if (random2 == 2) {
                            adv.setAdvBalancePossYn(!adv.getAdvBalancePossYn());
                        }

                        URI uri = URI.create(baseUrl + "/api/advUpdate/" + adv.getAdvId());
                        restTemplate.put(uri, adv);
                    } else if (events.get(random).equals("AGROUP")) {
                        AgroupVo agroup = agroupRepository.findOneRandom();
                        System.out.println(agroup);
                        int random2 = (int) (Math.random() * 3);
                        if (random2 == 0) {
                            agroup.setAgroupUseConfigYn(!agroup.getAgroupUseConfigYn());
                        } else if (random2 == 1) {
                            agroup.setAgroupBudgetPossYn(!agroup.getAgroupBudgetPossYn());
                        } else if (random2 == 2) {
                            agroup.setAgroupActYn(!agroup.getAgroupActYn());
                        }

                        URI uri = URI.create(baseUrl + "/api/agroupUpdate/" + agroup.getAgroupId());
                        restTemplate.put(uri, agroup);
                    } else if (events.get(random).equals("AD")) {
                        AdVo ad = adRepository.findOneRandom();
                        System.out.println(ad);
                        int random2 = (int) (Math.random() * 2);
                        if (random2 == 0) {
                            ad.setAdUseConfigYn(!ad.getAdUseConfigYn());
                        } else if (random2 == 1) {
                            ad.setAdActYn(!ad.getAdActYn());
                        }
                        URI uri = URI.create(baseUrl + "/api/adUpdate/" + ad.getAdId());
                        restTemplate.put(uri, ad);
                    } else if (events.get(random).equals("AD_BID")) {
                        AdBidVo adBidVo = adBidRepository.findOneRandom();
                        System.out.println(adBidVo);
                        int cost = ((int) (Math.random() * 10) + 1) * 10;
                        int plusminusRandom = (int) (Math.random() * 2);
                        if (plusminusRandom == 0) {
                            adBidVo.setBidCost(adBidVo.getBidCost() + cost);
                        } else {
                            if (adBidVo.getBidCost() - cost < 90L) {
                                adBidVo.setBidCost(adBidVo.getBidCost() + cost);
                            } else {
                                adBidVo.setBidCost(adBidVo.getBidCost() - cost);
                            }
                        }
                        adBidVo.setBidTime(new Date());
                        URI uri = URI.create(baseUrl + "/api/adBidUpdate/" + adBidVo.getAdId());
                        restTemplate.put(uri, adBidVo);
                    } else if (events.get(random).equals("DAD")) {
                        DadDetVo dadDet = dadDetRepository.findOneRandom();
                        System.out.println(dadDet);
                        int random2 = (int) (Math.random() * 3);
                        if (random2 == 0) {
                            dadDet.setDadUseConfigYn(!dadDet.getDadUseConfigYn());
                        } else if (random2 == 1) {
                            dadDet.setDadActYn(!dadDet.getDadActYn());
                        } else if (random2 == 2) {
                            if (dadDet.getDadCnrStatus().equals(DadCnrStatus.APPROVAL))
                                dadDet.setDadCnrStatus(DadCnrStatus.REJECT);
                            else if (dadDet.getDadCnrStatus().equals(DadCnrStatus.REJECT))
                                dadDet.setDadCnrStatus(DadCnrStatus.CNR_REQ);
                            else if (dadDet.getDadCnrStatus().equals(DadCnrStatus.CNR_REQ))
                                dadDet.setDadCnrStatus(DadCnrStatus.APPROVAL);
                        }
                        URI uri = URI.create(baseUrl + "/api/dadUpdate/" + dadDet.getDadDetId());
                        restTemplate.put(uri, dadDet);
                    } else if (events.get(random).equals("DAD_BID")) {
                        DadDetBidVo dadDetBidVo = dadDetBidRepository.findOneRandom();
                        System.out.println(dadDetBidVo);
                        int cost = ((int) (Math.random() * 10) + 1) * 10;
                        int plusminusRandom = (int) (Math.random() * 2);
                        if (plusminusRandom == 0) {
                            dadDetBidVo.setBidCost(dadDetBidVo.getBidCost() + cost);
                        } else {
                            if (dadDetBidVo.getBidCost() - cost < 90L) {
                                dadDetBidVo.setBidCost(dadDetBidVo.getBidCost() + cost);
                            } else {
                                dadDetBidVo.setBidCost(dadDetBidVo.getBidCost() - cost);
                            }
                        }
                        dadDetBidVo.setBidTime(new Date());
                        URI uri = URI.create(baseUrl + "/api/dadBidUpdate/" + dadDetBidVo.getDadDetId());
                        restTemplate.put(uri, dadDetBidVo);
                    }
                }
            }

        };

        for(int i=0;i<100;i++) {
            executor.execute(r);
        }
        while(true){
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
