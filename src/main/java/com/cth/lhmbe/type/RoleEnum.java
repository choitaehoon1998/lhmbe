package com.cth.lhmbe.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleEnum implements BaseEnumCode<String>{
	ADMIN("A","최고관리자"),
	MANAGER( "M","매니저"),
	WORKER("W", "알바생");

	private final String value;
	private final String code;


	@Override
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}
}
