package com.cth.lhmbe.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	ALREADY_REGISTERED_EMAIL("이미 등록된 이메일 입니다."),
	ALREADY_REGISTERED_STORE_NUMBER("이미 등록된 가게 전화번호 입니다."),
	ALREADY_REGISTERED_STORE_NAME_AND_STORE_LOCATION("이미 등록된 가게명과 가게 위치입니다."),
	MEMBER_NOT_FOUND("사용자가 없습니다."),
	STORE_NOT_FOUND("가게가 없습니다.")
	;

	private final String description;

}
