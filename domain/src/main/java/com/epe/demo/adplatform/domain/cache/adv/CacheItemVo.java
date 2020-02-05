package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheItemVo implements Serializable {

    private static final long serialVersionUID = -4122925661397218045L;
    private Long itemId;
    private String itemNo;
    private String itemName;
    private boolean itemActYn;
}
