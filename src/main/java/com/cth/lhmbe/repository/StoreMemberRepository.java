package com.cth.lhmbe.repository;

import com.cth.lhmbe.model.StoreMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMemberRepository extends JpaRepository<StoreMember,Long> {
	Optional<StoreMember> findByMember_MemberIdAndStore_StoreId(long memberId, long storeCompanyId);
}
