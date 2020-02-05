package com.epe.demo.adplatform.domain.rds.agroup;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
public class AgroupVo implements Serializable, Persistable<Long> {

    /** 광고그룹 ID */
    private Long agroupId;
    /** 광고그룹 명 */
    private String agroupName;
    /** 광고주 ID */
    private String advId;
    /** 광고그룹 예산 제한 금액 */
    private Long agroupBudgetLimitCost;
    /** 광고그룹 예산 가능 여부 */
    private Boolean agroupBudgetPossYn;
    /** 광고그룹 사용 설정 여부 */
    private Boolean agroupUseConfigYn;
    /** 광고그룹 노출 전략 요일 */
    private String agroupImpStrgDow;
    /** 광고그룹 노출 전략 시작 날짜 */
    private String agroupImpStrgStartDate;
    /** 광고그룹 노출 전략 종료 날짜 */
    private String agroupImpStrgEndDate;
    /** 희망 광고그룹 설정 일시 */
    private Date hopeAgroupConfigDtime;
    /** 희망 광고그룹 설정 여부 */
    private Boolean hopeAgroupConfigYn;
    /** 희망 광고그룹 설정 처리 여부 */
    private Boolean hopeAgroupConfigProcYn;
    /** 광고그룹 활성 여부 */
    private Boolean agroupActYn;
    /** 등록시간 */
    private Date regTime;

    private Metadata metadata;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return agroupId;
    }
}
