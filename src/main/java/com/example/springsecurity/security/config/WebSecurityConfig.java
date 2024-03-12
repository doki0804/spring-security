package com.example.springsecurity.security.config;

import com.example.springsecurity.security.CustomAuthorizationManager;
import com.example.springsecurity.security.CustomAuthenticationProvider;
import com.example.springsecurity.security.filter.CustomAuthenticationFilter;
import com.example.springsecurity.security.handler.AuthenticationEntryPointHandler;
import com.example.springsecurity.security.handler.CustomAuthFailureHandler;
import com.example.springsecurity.security.handler.CustomAuthSuccessHandler;
import com.example.springsecurity.security.handler.WebAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebSecurityConfig {

	private final WebAccessDeniedHandler webAccessDeniedHandler;

	private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

	private final CustomAuthorizationManager customAuthorizationManager;

	public WebSecurityConfig(WebAccessDeniedHandler webAccessDeniedHandler, AuthenticationEntryPointHandler authenticationEntryPointHandler, CustomAuthorizationManager customAuthorizationManager) {
		this.webAccessDeniedHandler = webAccessDeniedHandler;
		this.authenticationEntryPointHandler = authenticationEntryPointHandler;
		this.customAuthorizationManager = customAuthorizationManager;
	}

	/**
	 * 1. 정적 자원(Resource)에 대해서 인증된 사용자가 정적 자원의 접근에 대해 ‘인가’에 대한 설정을 담당하는 메서드
	 *
	 * @return WebSecurityCustomizer
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// 정적 자원에 대해서 Security를 적용하지 않음으로 설정
		return (web) -> web.ignoring().requestMatchers("/fonts/**","/css/**","/js/**","/images/**","/placeholder/**");

	}

	/**
	 * 2. HTTP에 대해서 ‘인증’과 ‘인가’를 담당하는 메서드이며 필터를 통해 인증 방식과 인증 절차에 대해서 등록하며 설정을 담당하는
	 * 메서드
	 *
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.debug("WebSecurityCofig securityFilterChain");
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/**").permitAll()
						.anyRequest().access(customAuthorizationManager)
				)
				// exception 처리
				.exceptionHandling(exception -> exception
						.accessDeniedHandler(webAccessDeniedHandler)
						.authenticationEntryPoint(authenticationEntryPointHandler)
				)
				.formLogin(login -> login
						.loginPage("/loginPage")
						.usernameParameter("userId")
						.passwordParameter("password")
						.successForwardUrl("/main") // 로그인 성공 후 이동할 URL 설정
						.failureForwardUrl("/loginPage") // 로그인 실패 URL 설정
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout") // Set logout URL
						.logoutSuccessUrl("/loginPage") // URL after successful logout
						.invalidateHttpSession(true) // Invalidate session after logout
						.deleteCookies("JSESSIONID") // Delete JSESSIONID cookie after logout
				)
				// 사용자 인증 필터 적용
				.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * 3. authenticate 의 인증 메서드를 제공하는 매니져로'Provider'의 인터페이스
	 * 과정:
	 * CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
	 *
	 * @return AuthenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(customAuthenticationProvider());
	}

	/**
	 * 4. '인증' 제공자로 사용자의 이름과 비밀번호 요구
	 * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
	 *
	 * @return CustomAuthenticationProvider
	 */
	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(passwordEncoder());
	}

	/**
	 * 5. 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화를 수행
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 6. 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증 과정 및 인증 후 처리에 대한 설정을 구성하는
	 * 메서드
	 *
	 * @return CustomAuthenticationFilter
	 */
	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() {
		log.debug("WebSecurityCofig customAuthenticationFilter");
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
		customAuthenticationFilter.setFilterProcessesUrl("/login"); // 접근 URL
		customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler()); // '인증' 성공 시
		customAuthenticationFilter.setAuthenticationFailureHandler(customLoginFailureHandler()); // '인증' 실패 시
		customAuthenticationFilter.afterPropertiesSet();
		return customAuthenticationFilter;
	}

	/**
	 * 7. Spring Security 기반의 사용자의 정보가 맞을 경우 수행이 되며 결과값을 리턴해주는 Handler
	 *
	 * @return CustomLoginSuccessHandler
	 */
	@Bean
	public CustomAuthSuccessHandler customLoginSuccessHandler() {
		return new CustomAuthSuccessHandler();
	}

	/**
	 * 8. Spring Security 기반의 사용자의 정보가 맞지 않을 경우 수행이 되며 결과값을 리턴해주는 Handler
	 *
	 * @return CustomAuthFailureHandler
	 */
	@Bean
	public CustomAuthFailureHandler customLoginFailureHandler() {
		return new CustomAuthFailureHandler();
	}
}