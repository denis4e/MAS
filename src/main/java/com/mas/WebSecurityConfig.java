package com.mas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select login, password, enabled from user where login=? and enabled=1 ")
                .authoritiesByUsernameQuery("select u.login, r.role_name from user_roles ur " +
                        " inner join user u on u.id_user=ur.user_id " +
                        " inner join roles r on r.id=ur.role_id where u.login=? ")
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/home")
                .permitAll()
                .and()
                .formLogin().loginPage("/signIn")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/signIn")
                .failureUrl("/signIn")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .permitAll()
                .and()
                .authorizeRequests()
//                .antMatchers("/user/**").authenticated()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    private Filter ssoFilter() {

        CompositeFilter filter = new CompositeFilter();
        List filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/connect/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);

        facebookFilter.setRestTemplate(facebookTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
                facebook().getClientId());
        tokenServices.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(tokenServices);

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/connect/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);

        OAuth2ClientAuthenticationProcessingFilter linkedInFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/connect/linkedIn");
        OAuth2RestTemplate linkedInTemplate = new OAuth2RestTemplate(linkedIn(), oauth2ClientContext);
        linkedInFilter.setRestTemplate(linkedInTemplate);
        tokenServices = new UserInfoTokenServices(linkedInResource().getUserInfoUri(), linkedIn().getClientId());
        tokenServices.setRestTemplate(linkedInTemplate);
        linkedInFilter.setTokenServices(tokenServices);

        OAuth2ClientAuthenticationProcessingFilter twitterFilter = new OAuth2ClientAuthenticationProcessingFilter(
                "/connect/twitter");
        OAuth2RestTemplate twitterTemplate = new OAuth2RestTemplate(twitter(), oauth2ClientContext);
        twitterFilter.setRestTemplate(twitterTemplate);
        tokenServices = new UserInfoTokenServices(twitterResource().getUserInfoUri(), twitter().getClientId());
        tokenServices.setRestTemplate(twitterTemplate);
        twitterFilter.setTokenServices(tokenServices);

        filters.add(facebookFilter);
        filters.add(googleFilter);
        filters.add(linkedInFilter);
        filters.add(twitterFilter);

        filter.setFilters(filters);

        return filter;
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("facebook.client")
    public AuthorizationCodeResourceDetails facebook() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("facebook.resource")
    public ResourceServerProperties facebookResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("linkedIn.client")
    public AuthorizationCodeResourceDetails linkedIn() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("linkedIn.resource")
    public ResourceServerProperties linkedInResource() {
        return new ResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("twitter.client")
    public AuthorizationCodeResourceDetails twitter() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("twitter.resource")
    public ResourceServerProperties twitterResource() {
        return new ResourceServerProperties();
    }
}
