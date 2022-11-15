package com.cth.lhmbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SignUpAdmin {
	@Getter
	@Setter
	@AllArgsConstructor
	public static class Request {
		private String userName;
		private String email;
		private String password;
		private String storeName;
		private String storeNumber;
		private String storeLocation;
	}
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private String adminName;
		private String adminEmail;
		private String storeName;
		private String storeNumber;
		private String storeLocation;
		public static Response from(StoreAdminDto storeMemberDto) {
			return Response.builder()
				.adminName(storeMemberDto.getAdminName())
				.adminEmail(storeMemberDto.getAdminEmail())
				.storeName(storeMemberDto.getStoreName())
				.storeNumber(storeMemberDto.getStoreNumber())
				.storeLocation(storeMemberDto.getStoreLocation())
				.build();
		}
	}
}
