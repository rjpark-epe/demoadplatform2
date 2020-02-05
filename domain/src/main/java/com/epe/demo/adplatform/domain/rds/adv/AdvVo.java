package com.epe.demo.adplatform.domain.rds.adv;

import com.epe.demo.adplatform.domain.enums.adv.AdvRegStatus;
import com.epe.demo.adplatform.domain.rds.common.Metadata;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Data
public class AdvVo implements Serializable, Persistable<String> {

    /** 광고주 ID */
    private String advId;
    /** 광고주 업체 ID */
    private Long advComId;
    /** 인터파크 업체 번호 */
    private String ipkComNo;
    /** 공급 계약 일련번호 */
    private Long supContractSeq;
    /** 업체 명 */
    private String comName;
    /** 업체 상태 */
    private String comStatus;
    /** 업체 구분 */
    private String comDiv;
    /** 광고주 사용 설정 여부 */
    private Boolean advUseConfigYn;
    /** 광고주 등록 상태 */
    private AdvRegStatus advRegStatus;
    /** 광고주 예산 제한 금액 */
    private Long advBudgetLimitCost;
    /** 광고주 예산 가능 여부 */
    private Boolean advBudgetPossYn;
    /** 광고주 잔액 가능 여부 */
    private Boolean advBalancePossYn;
    /** 광고주 할인 비율 */
    private Long advDcRate;
    /** 광고주 할인 시작 날짜 */
    private String advDcStartDate;
    /** 광고주 할인 종료 날짜 */
    private String advDcEndDate;
    /** 광고주 할인 코멘트 */
    private String advDcComt;
    /** 광고주 노출 전략 요일 */
    private String advImpStrgDow;
    /** 광고주 노출 전략 시작 날짜 */
    private String advImpStrgStartDate;
    /** 광고주 노출 전략 종료 날짜 */
    private String advImpStrgEndDate;
    /** 대행사 추천 요청 ID */
    private Long agencyRecReqId;
    /** 활성 광고주 여부 */
    private Boolean actAdvYn;

    private String searchType;

    private AdvConfigVo advConfig;

    private Metadata metadata;

    /** 최근 쉐도우 로그인 목록에서 사용 */
    private boolean isActive = false;
    private boolean isTack = false;

    private boolean isNew = false;

    @Override
    public String getId() {
        return advId;
    }
}
