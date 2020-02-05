package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheKwdVo implements Serializable {

    private static final long serialVersionUID = 191187706304419608L;
    private Long kwdId;
    private String kwdName;
    private boolean sellPossKwdYn;

}
