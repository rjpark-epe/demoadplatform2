package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheAdDetVo implements Serializable {

    private static final long serialVersionUID = -729610568959922126L;
    private Long adId;
    private String advId;
    private Long agroupId;
    private Long adDetId;
    private Long itemId;
    private Long kwdId;
    private String kwdName;
    private Long bidCost;
    private Long bidTime;

    private Boolean adDetCnrStatusYn;
    private Boolean adDetUseConfigYn;
    private Boolean adDetActYn;

    private Long cpc;
    private Double ecpm;
}
