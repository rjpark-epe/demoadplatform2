package com.epe.demo.adplatform.domain.rds.kwd;

import com.epe.demo.adplatform.domain.rds.common.Metadata;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Data
public class KwdVo implements Serializable, Persistable<Long> {

    /** 키워드 ID */
    private Long kwdId;
    /** 키워드 명 */
    private String kwdName;
    /** 인덱스 우선순위 */
    private Long indexPrior;
    /** 판매 가능 키워드 여부 */
    private Boolean sellPossKwdYn;
    /** 판매 가능 키워드 여부 */
    private Boolean manualCnrKwdYn;

    private Metadata metadata;

    private boolean isNew = false;

    @Override
    public Long getId() {
        return kwdId;
    }
}
