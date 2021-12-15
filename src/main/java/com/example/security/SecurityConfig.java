package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.impl.MemberDetailImpl;
import com.example.jwt.JwtRequestFilter;

@Configuration // 환경설정 파일
@EnableWebSecurity // security를 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // DB연동을 위해 만든 sevice
    @Autowired
    private MemberDetailImpl memberDetailImpl;

    // @Autowired
    // private UDetailService uService;

    // 환경설정 파일에서 객체 만들기(회원가입시 암호화 방법)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 기존에 정의되어 있던 메소드를 지우고 새로 작성
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 권한 설정
        http.authorizeRequests().antMatchers("/admin", "/admin/*").hasAuthority("ADMIN")
                .antMatchers("/seller", "/seller/*").hasAnyAuthority("ADMIN", "SELLER").antMatchers("/user", "/user/*")
                .hasAnyAuthority("ADMIN", "USER").anyRequest().permitAll().and();

        // 로그인 페이지
        // http.formLogin()
        // .loginPage("/member/login")
        // .loginProcessingUrl("/member/login")
        // .usernameParameter("userid")
        // .passwordParameter("userpw").permitAll()
        // .successHandler().and();

        // 로그아웃 페이지
        // http.logout().logoutUrl("/member/logout")
        // .logoutSuccessHandler()
        // .invalidateHttpSession(true)
        // .clearAuthentication(true).permitAll().and();

        // 접근불가 예외처리
        // http.exceptionHandling().accessDeniedPage("/page403").and();

        // 필터 추가하기(@controller 전에 수행됨)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // jwt의 restcontoller방식
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // h2-console 사용, restcontroller 사용
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

}