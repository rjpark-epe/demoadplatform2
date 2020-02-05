package com.epe.demo.adplatform.domain.cache.adv;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheAdvVo implements Serializable {

    private static final long serialVersionUID = -8641271607339118078L;
    private String advId;
    private boolean advRegStatusYn;
    private boolean advUseConfigYn;
    private boolean advBudgetPossYn;
    private boolean advBalancePossYn;

    /** 광고주 노출 전략 요일 */
    private String advImpStrgDow;
    /** 광고주 노출 전략 시작 날짜 */
    private String advImpStrgStartDate;
    /** 광고주 노출 전략 종료 날짜 */
    private String advImpStrgEndDate;

}
