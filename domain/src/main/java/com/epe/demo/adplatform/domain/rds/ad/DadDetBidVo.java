package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.util.Date;

@Data
public class DadDetBidVo implements Persistable<Long> {

    private Long dadDetId;
    private Long bidCost;
    private Date bidTime;

    private Metadata metadata;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return dadDetId;
    }
}
