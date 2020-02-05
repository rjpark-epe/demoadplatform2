package com.epe.demo.adplatform.domain.enums.adv;

import com.epe.demo.adplatform.domain.enums.EnumDescription;

/** 계정 등록 상태 */ 
public enum AdvRegStatus implements EnumDescription{
	
	NORMAL("정상"),
	HOLD("중지"),
	;
	
	private AdvRegStatus(String desc){
		this.desc = desc;
	}
	
	private final String desc;

	@Override
	public String getDescription() {
		return desc;
	}

	
}
