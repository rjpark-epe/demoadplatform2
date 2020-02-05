package com.epe.demo.adplatform.domain.rds.item;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Data
public class ItemVo implements Serializable, Persistable<Long> {

    /** 상품 ID */
    private Long itemId;
    /** 광고주 ID */
    private String advId;
    /** 카테고리 ID */
    private String categoryId;
    /** 상품 번호 */
    private String itemNo;
    /** 상품 명 */
    private String itemName;
    /** 인터파크 업체 번호 */
    private String ipkComNo;
    /** 공급 계약 일련번호 */
    private Long supContractSeq;
    /** 이미지 URL */
    private String imgUrl;
    /** 제조사 명 */
    private String mnftName;
    /** 상품 모델 명 */
    private String itemModelName;
    /** 브랜드 명 */
    private String brandName;
    /** 상품 홍보문구 */
    private String itemPtxt;
    /** 배송 금액 */
    private Long deliveryCost;
    /** 상품 URL */
    private String itemUrl;
    /** 모바일 상품 URL */
    private String mobileItemUrl;
    /** 상품 코멘트 수 */
    private Long itemComtCnt;
    /** 성인 여부 */
    private Boolean adultYn;
    /** 상품 원본 금액 */
    private Long itemOrgCost;
    /** 상품 PC 금액 */
    private Long itemPcCost;
    /** 상품 모바일 금액 */
    private Long itemMobileCost;
    /** 모바일 키워드 명 */
    private String mobileKwdName;
    /** 상품 키워드 명 */
    private String itemKwdName;
    /** IPP TRANS 여부 */
    private Boolean ippTransYn;
    /** 상품 수정 시간 */
    private Date itemUpdateTime;
    /** 상품 활성 여부 */
    private Boolean itemActYn;
    /** 수정 시간 */
    private Date updateTime;
    /** 상품 명 수정 시간. */
    private Date itemNameUpdateTime;

    /** 상품 검색 - 상품번호, 상품명 */
    private String srchTxt;
    /** 광고 등록 상품 포함 여부 */
    private Boolean includeYn;
    /** 광고 등록 상품 포함 결과 */
    private Long adId;
    private Boolean adActYn;

    /** 상품동기화 잡 배치에서 사용 */
    private String adAdvId;

    /** 키워드 추출에 사용되는 5가지 중 4가지가 수정 되었는지 여부 */
    private Boolean kwdChangeYn;

    private boolean isNew = false;


    @Override
    public Long getId() {
        return itemId;
    }
}
