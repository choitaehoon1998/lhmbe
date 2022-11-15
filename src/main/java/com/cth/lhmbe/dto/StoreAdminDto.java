package com.cth.lhmbe.dto;

import com.cth.lhmbe.model.StoreMember;
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
public class StoreAdminDto {
	private String adminName;
	private String adminEmail;
	private String storeName;
	private String storeNumber;
	private String storeLocation;
	public static StoreAdminDto fromEntity(StoreMember storeMember) {
		return StoreAdminDto.builder()
			.adminName(storeMember.getMember().getUserName())
			.adminEmail(storeMember.getMember().getEmail())
			.storeName(storeMember.getStore().getStoreName())
			.storeNumber(storeMember.getStore().getStoreNumber())
			.storeLocation(storeMember.getStore().getStoreLocation())
			.build();
	}
}
