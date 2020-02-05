package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import com.epe.demo.adplatform.domain.rds.common.MetadataAware;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdVo implements Serializable, Persistable<Long>, MetadataAware<Long> {

    /** 광고 ID */
    private Long adId;
    /** 광고그룹 ID */
    private Long agroupId;
    /** 광고주 ID */
    private String advId;
    /** 상품 ID */
    private Long itemId;
    /** SRP 품질 지수 */
    private Double srpQs;
    /** 비SRP 품질 지수 */
    private Double nsrpQs;
    /** 광고 사용 설정 여부 */
    private Boolean adUseConfigYn;
    /** 광고 활성 여부 */
    private Boolean adActYn;
    /** 등록시간 */
    private Date regTime;

    private Metadata metadata;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return adId;
    }
}
