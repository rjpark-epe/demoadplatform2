package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheAdVo implements Serializable {
    private static final long serialVersionUID = -6413078779146546884L;
    private Long adId;
    private String advId;
    private Long agroupId;

    private Boolean adActYn;
    private Boolean adUseConfigYn;
}
