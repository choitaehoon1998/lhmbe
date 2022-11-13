package com.cth.lhmbe.repository;

import com.cth.lhmbe.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
	Optional<Member> findByEmail(String email);

}
