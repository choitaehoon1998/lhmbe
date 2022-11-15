package com.cth.lhmbe.service;

import static com.cth.lhmbe.type.ErrorCode.ALREADY_REGISTERED_EMAIL;

import com.cth.lhmbe.dto.AdminDto;
import com.cth.lhmbe.exceptions.MemberException;
import com.cth.lhmbe.model.Member;
import com.cth.lhmbe.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	public MemberService(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
		this.passwordEncoder = passwordEncoder;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public AdminDto createAdmin(String userName, String email, String password) {

		validateDuplicate(email);

		return AdminDto.fromEntity(
				memberRepository.save(
					Member.builder()
					.userName(userName)
					.email(email)
					.password(passwordEncoder.encode(password))
					.isDelete(false)
					.build())
				);
	}

	private void validateDuplicate(String email) {
		memberRepository.findByEmail(email)
			.ifPresent(member -> {throw new MemberException(ALREADY_REGISTERED_EMAIL);});
	}
}
