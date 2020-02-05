package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheSrchAgroupVo implements Serializable {


    private static final long serialVersionUID = -5706142266023971083L;
    private Long agroupId;
    private String advId;
    private boolean agroupUseConfigYn;
    private boolean agroupBudgetPossyn;
    private String agroupImpStrgDow;
    private String agroupImpStrgStartDate;
    private String agroupImpStrgEndDate;
    private boolean agroupActYn;

}
