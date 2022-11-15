package com.cth.lhmbe.dto;

import com.cth.lhmbe.model.Store;
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
public class StoreDto {
	private long storeId;
	private String storeName;
	private String storeNumber;
	private String storeLocation;
	public static StoreDto fromEntity(Store store) {
		return StoreDto.builder()
			.storeId(store.getStoreId())
			.storeName(store.getStoreName())
			.storeNumber(store.getStoreNumber())
			.storeLocation(store.getStoreLocation())
			.build();
	}
}
