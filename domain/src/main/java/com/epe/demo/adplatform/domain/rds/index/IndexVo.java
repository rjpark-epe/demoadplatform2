package com.epe.demo.adplatform.domain.rds.index;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Data
public class IndexVo implements Serializable, Persistable<Long> {

    private Long venderId;
    private String advId;
    private Long adId;
    private Long kwdId;
    private Long bidCost;

    private boolean useYn;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return adId;
    }
}
