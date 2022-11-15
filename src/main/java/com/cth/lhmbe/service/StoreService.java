package com.cth.lhmbe.service;

import static com.cth.lhmbe.type.ErrorCode.ALREADY_REGISTERED_STORE_NAME_AND_STORE_LOCATION;
import static com.cth.lhmbe.type.ErrorCode.ALREADY_REGISTERED_STORE_NUMBER;

import com.cth.lhmbe.dto.StoreDto;
import com.cth.lhmbe.exceptions.StoreException;
import com.cth.lhmbe.model.Store;
import com.cth.lhmbe.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {
	private final StoreRepository storeRepository;

	public StoreService(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	@Transactional
	public StoreDto createStore(String storeName, String storeNumber, String storeLocation) {
		validateDuplicate(storeNumber);
		validateDuplicate(storeName, storeLocation);
		return StoreDto.fromEntity(
			storeRepository.save(
				Store.builder()
					.storeName(storeName)
					.storeNumber(storeNumber)
					.storeLocation(storeLocation)
					.isDelete(false)
					.build()
			)
		);
	}

	private void validateDuplicate(String storeNumber) {
		storeRepository.findByStoreNumber(storeNumber)
			.ifPresent(store -> {throw new StoreException(ALREADY_REGISTERED_STORE_NUMBER);});
	}
	private void validateDuplicate(String storeName,String  storeLocation){
		storeRepository.findByStoreNameAndStoreLocation(storeName, storeLocation)
			.ifPresent(store -> {throw new StoreException(ALREADY_REGISTERED_STORE_NAME_AND_STORE_LOCATION);});
	}
}
