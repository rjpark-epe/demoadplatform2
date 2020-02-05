package com.epe.demo.adplatform.domain.rds.adv;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdvConfigVo implements Serializable, Persistable<String> {

    /** 광고주 ID */
    private String advId;
    /** 가입 시간 */
    private Date joinTime;
    /** 약관 동의 여부 */
    private Boolean clauseAgreeYn;
    /** 약관 동의 시간 */
    private Date clauseAgreeTime;
    /** 광고주 담당자 명 */
    private String advPericName;
    /** 광고주 담당자 이메일 */
    private String advPericEmail;
    /** 광고주 담당자 HP */
    private String advPericHp;
    /** 잔액 상태 알림 여부 */
    private Boolean balanceStatusAlertYn;
    /** 프로모션 알림 여부 */
    private Boolean promAlertYn;
    /** 심야 SMS 알림 여부 */
    private Boolean nightSmsAlertYn;
    /** 수동 검수 광고주 여부 */
    private Boolean manualCnrAdvYn;
    /** 희망 광고주 설정 일시 */
    private Date hopeAdvConfigDtime;
    /** 희망 광고주 설정 여부 */
    private Boolean hopeAdvConfigYn;
    /** 희망 광고주 설정 처리 여부 */
    private Boolean hopeAdvConfigProcYn;
    /** 광고그룹 제한 수 */
    private Long agroupLimitCnt;

    private Boolean itemSyncYn;

    /** 첫 노출일 */
    private String firstImpDate;
    /** 최근 노출 일 */
    private String recentImpDate;

    private String comName;

    // 등록 시 필요한 값들
    /** 대행사 컨택 동의 여부 */
    private Boolean contactPossYn;
    /** 약관 동의 여부 */
    private Boolean agreementYn;
    /** 담당자 ID */
    private String pericId;
    /** 대행사 초대 예정 여부*/
    private Boolean agencyInviteYn;
    private Boolean smsAuthFlag;
    private String smsAuthText;

    private Metadata metadata;

    private boolean isNew = false;


    @Override
    public String getId() {
        return advId;
    }
}
