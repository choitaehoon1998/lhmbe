package com.cth.lhmbe.config.auth;

import com.cth.lhmbe.Enum.RoleEnum;
import com.cth.lhmbe.exceptions.StoreMemberMisMatchException;
import com.cth.lhmbe.model.Member;
import com.cth.lhmbe.model.StoreMember;
import com.cth.lhmbe.repository.MemberRepository;
import com.cth.lhmbe.repository.StoreMemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final StoreMemberRepository storeMemberRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String[] usernameAndCompanyCd = StringUtils.split(
			username, String.valueOf(Character.LINE_SEPARATOR));

		Member member = memberRepository.findByEmail(usernameAndCompanyCd[0]).orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을수 없습니다."));

		StoreMember storeMember = storeMemberRepository.findByMember_MemberIdAndStore_StoreId(member.getMemberId(),
				Long.parseLong(usernameAndCompanyCd[1]))
			.orElseThrow(() -> new StoreMemberMisMatchException("선택하신 회사코드와 유저가 매치되지 않습니다."));

		return new PrincipalDetails(member.getUserName(), member.getEmail(), member.getPassword(), storeMember.getStore().getStoreId(),
			RoleEnum.ADMIN);
	}
}