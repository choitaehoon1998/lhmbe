package com.cth.lhmbe.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String email;
	private String password;
	private Long companyId;
}
