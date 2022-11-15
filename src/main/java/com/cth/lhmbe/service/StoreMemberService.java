package com.cth.lhmbe.service;

import static com.cth.lhmbe.type.ErrorCode.MEMBER_NOT_FOUND;
import static com.cth.lhmbe.type.ErrorCode.STORE_NOT_FOUND;

import com.cth.lhmbe.type.RoleEnum;
import com.cth.lhmbe.dto.SignUpAdmin;
import com.cth.lhmbe.dto.StoreAdminDto;
import com.cth.lhmbe.exceptions.MemberException;
import com.cth.lhmbe.exceptions.StoreException;
import com.cth.lhmbe.model.Member;
import com.cth.lhmbe.model.Store;
import com.cth.lhmbe.model.StoreMember;
import com.cth.lhmbe.repository.MemberRepository;
import com.cth.lhmbe.repository.StoreMemberRepository;
import com.cth.lhmbe.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreMemberService {

	private final MemberService memberService;
	private final StoreService storeService;
	private final StoreMemberRepository storeMemberRepository;
	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;

	public StoreMemberService(MemberService memberService, StoreService storeService, StoreMemberRepository storeMemberRepository,
		StoreRepository storeRepository, MemberRepository memberRepository) {
		this.memberService = memberService;
		this.storeService = storeService;
		this.storeMemberRepository = storeMemberRepository;
		this.storeRepository = storeRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public StoreAdminDto createStoreAdmin(SignUpAdmin.Request request) {
		return StoreAdminDto.fromEntity(
			storeMemberRepository.save(
				StoreMember.builder()
					.member(getAdmin(
						memberService
							.createAdmin(
								request.getUserName(),
								request.getEmail(),
								request.getPassword()
							).getMemberId()))
					.store(getStore(
						storeService
							.createStore(
								request.getStoreName(),
								request.getStoreNumber(),
								request.getStoreLocation()
							)
							.getStoreId()))
					.roleType(RoleEnum.ADMIN)
					.build()
			)
		);
	}

	private Member getAdmin(long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
	}

	private Store getStore(long storeId) {
		return storeRepository.findById(storeId)
			.orElseThrow(() -> new StoreException(STORE_NOT_FOUND));
	}

}
