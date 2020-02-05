package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.enums.ad.DadCnrStatus;
import com.epe.demo.adplatform.domain.rds.common.Metadata;
import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import com.epe.demo.adplatform.domain.repository.PageAware;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
public class DadDetVo implements Serializable, Persistable<Long>, PageAware {

    /** 직접광고 상세 ID */
    private Long dadDetId;
    /** 광고 ID */
    private Long adId;
    /** 키워드 ID */
    private Long kwdId;
    /** 직접광고 검수 상태 */
    private DadCnrStatus dadCnrStatus;
    /** 검수 요청 ID */
    private Long cnrReqId;
    /** 직접광고 사용 설정 여부 */
    private Boolean dadUseConfigYn;
    /** 직접광고 활성 여부 */
    private Boolean dadActYn;
    /** 등록 시간 */
    private Date regTime;

    private AdVo ad;
    private DadDetBidVo dadDetBid;
    private KwdVo kwd;

    private Metadata metadata;

    private Pageable pageable;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return dadDetId;
    }
}
