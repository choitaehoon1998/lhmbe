package com.cth.lhmbe.config;

import com.cth.lhmbe.config.jwt.JwtAuthenticationFilter;
import com.cth.lhmbe.config.jwt.JwtAuthorizationFilter;
import com.cth.lhmbe.repository.MemberRepository;
import com.cth.lhmbe.repository.StoreMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final MemberRepository memberRepository;
	private final StoreMemberRepository storeMemberRepository;
	private final CorsConfig corsConfig;

	public SecurityConfig(MemberRepository memberRepository, StoreMemberRepository storeMemberRepository, CorsConfig corsConfig) {
		this.memberRepository = memberRepository;
		this.storeMemberRepository = storeMemberRepository;
		this.corsConfig = corsConfig;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf()
			.disable()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()

			.formLogin()
			.disable()

			.httpBasic()
			.disable()

			.apply(new MyCustomDsl())
			.and()
			.authorizeRequests(authorize -> authorize
				.antMatchers("/api/v1/user/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/manager/**")
				.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/api/v1/admin/**")
				.access("hasRole('ROLE_ADMIN')")
				.anyRequest()
				.permitAll())
			.build();
	}

	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
				.addFilter(corsConfig.corsFilter())
				.addFilter(new JwtAuthenticationFilter(authenticationManager))
				.addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository , storeMemberRepository));
		}
	}

}
