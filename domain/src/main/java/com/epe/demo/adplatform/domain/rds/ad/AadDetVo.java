package com.epe.demo.adplatform.domain.rds.ad;

import com.epe.demo.adplatform.domain.rds.kwd.KwdVo;
import com.epe.demo.adplatform.domain.repository.PageAware;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AadDetVo implements Serializable, Persistable<Long>, PageAware {

    private Long aadDetId;
    private Long adId;
    private Long kwdId;
    /** 매치 타입 */
    private String matchType;
    /** 상위 연관 키워드 여부 */
    private Boolean highRelKwdYn;

    private AdVo ad;
    private AdBidVo adBid;
    private KwdVo kwd;

    private Pageable pageable;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return aadDetId;
    }
}
