package com.cth.lhmbe.dto;

import com.cth.lhmbe.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
	private Long memberId;
	private String userName;
	private String email;
	public static AdminDto fromEntity(Member member) {
		return AdminDto.builder()
			.memberId(member.getMemberId())
			.userName(member.getUserName())
			.email(member.getEmail())
			.build();
	}
}
