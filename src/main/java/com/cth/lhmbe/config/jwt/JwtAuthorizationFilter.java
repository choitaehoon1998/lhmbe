package com.cth.lhmbe.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cth.lhmbe.config.auth.PrincipalDetails;
import com.cth.lhmbe.exceptions.StoreMemberMisMatchException;
import com.cth.lhmbe.model.Member;
import com.cth.lhmbe.model.StoreMember;
import com.cth.lhmbe.repository.MemberRepository;
import com.cth.lhmbe.repository.StoreMemberRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final MemberRepository memberRepository;

	private final StoreMemberRepository storeMemberRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository
								  ,StoreMemberRepository storeMemberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
		this.storeMemberRepository = storeMemberRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		String token = request.getHeader(JwtProperties.HEADER_STRING)
			.replace(JwtProperties.TOKEN_PREFIX, "");


		DecodedJWT jwt  = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
		String email = jwt.getClaim("email").asString();
		int companyId = jwt.getClaim("companyId").asInt();

		if (email != null) {

			Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("해당 하는 유저를 찾을수 없습니다."));

			StoreMember storeMember = storeMemberRepository.findByMember_MemberIdAndStore_StoreId(member.getMemberId(),
				companyId)
				.orElseThrow(() -> new StoreMemberMisMatchException("유저와 가게가 매치되지 않습니다."));

			PrincipalDetails principalDetails = new PrincipalDetails(member.getUserName(),
				member.getEmail(), member.getPassword(), storeMember.getStore().getStoreId(), storeMember.getRoleType());

			Authentication authentication = new UsernamePasswordAuthenticationToken(
				principalDetails,
				null,
				principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

}