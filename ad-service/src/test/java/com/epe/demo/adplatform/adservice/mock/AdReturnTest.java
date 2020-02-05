package com.epe.demo.adplatform.adservice.mock;

import com.epe.demo.adplatform.domain.rds.kwd.KwdRepository;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({TestDocConfig.class})
public class AdReturnTest {

    @Resource
    private KwdRepository kwdRepository;

    private static RestTemplate restTemplate;
    private static String baseUrl;

    private static List<String> kwdList;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @BeforeAll
    public static void setup(){
        restTemplate = new RestTemplate();
        baseUrl = "http://13.125.202.127:5050";
    }

    @Test
    public void test() {
        initKwdList();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(100);
        executor.initialize();

        Runnable r = new Runnable() {

            @Override
            public void run() {
                while(true){
                    try {
                        int random = (int)(Math.random()*kwdList.size());
                        URI uri = null;
                        uri = URI.create(baseUrl+"/api/adReturn/"+ URLEncoder.encode(kwdList.get(random),"UTF-8"));
                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start();
                        ResponseEntity<String> res = restTemplate.getForEntity(uri,String.class);
                        stopWatch.stop();
                        LOG.info("광고 리스트 : {}",res.getBody());
                        LOG.info("URL : {} / 소요시간 : {}",uri.toString(),stopWatch.getTotalTimeMillis());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
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

    private void initKwdList() {
        kwdList = Lists.newArrayList();
        for(int i=1;i<=10;i++) {
            List<KwdVo> kwdVos = (List<KwdVo>) kwdRepository.findListByIndexPrior(i);
            for (KwdVo kwd : kwdVos) {
                kwdList.add(kwd.getKwdName());
            }
        }
    }

}
