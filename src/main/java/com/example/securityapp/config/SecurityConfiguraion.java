package com.example.securityapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import java.util.*;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;


@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguraion extends WebSecurityConfigurerAdapter {

    //AccountService
    @Autowired
    private UserDetailsService userDetailsService;


    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //중요
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
         //WebSecurity : Security filter chain을 적용할 필요가 전혀 없는 요청인 경우
        //정적 컨텐츠의 액세스는 인증을 걸지 않는다.
        web.ignoring().antMatchers("/webjars/**", "/static/**", "/favicon.ico");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("call SecurityConfig configure");

        http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .loginProcessingUrl("/login")                  //UsernamePasswordAuthenticationFilter 수행한다. 디폴트(/login)
            .defaultSuccessUrl("/loginSuccess")
            .failureUrl("/loginFailure")
            .usernameParameter("email")                    //디폴트 : username
            .permitAll()
            .and()
            //.accessDeniedHandler(accessDeniedHandler())
            //.and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .deleteCookies("SESSION", "JSESSIONID")
            .logoutUrl("/logout")
            .logoutSuccessUrl("/logoutSuccess")
            .invalidateHttpSession(true)
            .permitAll()
            .and()
            .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class); //URL별 접근 제어

                /* =======================================================================

                http.exceptionHandling()
                    .authenticationEntryPoint(new AuthenticationEntryPoint() {
                        //인증에 실패한 경우 예외처리
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response,
                                             AuthenticationException authException) throws IOException, ServletException {
                            log.error("AuthenticationException : ", authException);
                            response.sendRedirect("/login");
                        }
                    }).accessDeniedHandler(new AccessDeniedHandler() {
                        //인가 실패한 경우 예외처리
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response,
                                           AccessDeniedException accessDeniedException) throws IOException, ServletException {
                            log.error("AccessDeniedException : ", accessDeniedException);
                            response.sendRedirect("/denied");
                        }
                 });

                ======================================================================= */

    }

    /*----------------------------------------------------------------------------
             URL 인가 방식
    ----------------------------------------------------------------------------*/

    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        return filterSecurityInterceptor;
    }


    private AccessDecisionManager affirmativeBased() {
        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        return affirmativeBased;
    }



    private java.util.List<AccessDecisionVoter<? extends Object>> getAccessDecisionVoters() {
        return Arrays.asList(new RoleVoter());
    }


    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        //return new UrlFilterInvocationSecurityMetadataSource1(urlResourcesMapFactoryBean().getObject());
        return new UrlFilterInvocationSecurityMetadataSource();
    }


    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /*----------------------------------------------------------------------------
             Role Hierarchy
    ----------------------------------------------------------------------------*/

    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        String allHierarchy = roleHierarchyService.findAllHierarchy();
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(allHierarchy);
        return roleHierarchy;
    }


    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter(){
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }


}
