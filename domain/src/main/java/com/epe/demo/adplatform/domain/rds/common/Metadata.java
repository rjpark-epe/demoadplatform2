package com.epe.demo.adplatform.domain.rds.common;

import lombok.Data;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.util.Date;


/** 
 * 해당 객체의 공통적인 부가적인 정보를 저장한다.
 * 기능1. 이력테이블에 저장  
 * 기능2. 버저닝 기능 (잘 안씀..)
 * 
 * regTime / regId 등은 생략한다. regTime 같은게 업무에  필요한경우 본 테이블에 넣자
 *  */
@SuppressWarnings("serial")
@Data
public class Metadata implements Serializable,Comparable<Metadata>{
    
	/** 업데이트 시간 :  이 필드는 버전 대용으로도 사용한다. */
	@Version
	private Date updateTime;
	/** 배치의 경우 신청자 ID가 와야한다. */
	private String updateId;
	/** 배치의 경우 신청자 IP가 와야한다. */
	private String updateIp;
	/** 이벤트가 일어난 인스턴스의 ID */
	private String updateInstance;
	/** 병렬처리시 안찍힐 수 있다?? */
	private String updateMenuId;
	
	/** updateTime을 갱신하기위한 필드. updateTime은 버저닝 체크에 필요함으로 그대로 놔둬야함 */
	private Date dirtyTime;
	/** 이력 테이블의 PK값 */
	private Long hisId;
	/** 이력 테이블의 메세지값 */
	private String hisMsg;
	/** 트랜잭션 ID */
	private Long tid;
	
	// ================== 이하 view용 ==========================
	
	/** 이 로그에 대한 설명 */
	private String hisLog;
	/** vo의 설명. 통합이력에 사용됨 */
	private String desc;

	/** 최근 수정이 위로 오게  */
	@Override
	public int compareTo(Metadata o) {
		return updateTime.compareTo(o.updateTime) * -1;
	}

}

