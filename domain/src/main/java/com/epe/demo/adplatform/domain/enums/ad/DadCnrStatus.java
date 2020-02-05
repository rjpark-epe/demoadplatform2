package com.epe.demo.adplatform.domain.enums.ad;

import com.epe.demo.adplatform.domain.enums.EnumDescription;

public enum DadCnrStatus implements EnumDescription {
	
	CNR_REQ("검수 요청"),
	APPROVAL("승인"),
	REJECT("반려"),
	;
	
	private DadCnrStatus(String desc){
		this.desc = desc;
	}
	
	private final String desc;

	@Override
	public String getDescription() {
		return desc;
	}

	
}
