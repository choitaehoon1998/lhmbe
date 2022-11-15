package com.cth.lhmbe.repository;

import com.cth.lhmbe.model.Store;
import com.cth.lhmbe.model.StoreMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {

	Optional<Store> findByStoreNumber(String storeNumber);

	Optional<Store> findByStoreNameAndStoreLocation(String storeName, String storeLocation);
}
