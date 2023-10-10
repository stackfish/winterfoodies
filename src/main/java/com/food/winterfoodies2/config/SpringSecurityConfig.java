package com.food.winterfoodies2.config;

import com.food.winterfoodies2.security.JwtAuthenticationEntryPoint;
import com.food.winterfoodies2.security.JwtAuthenticationFilter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 50
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();// 55
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://172.105.204.235", "http://localhost:8080"));// 56
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));// 57
        configuration.setAllowedHeaders(Arrays.asList("*"));// 58
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();// 59
        source.registerCorsConfiguration("/**", configuration);// 60
        return source;// 61
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement((session) -> {// 68 69 70 71
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).cors((corsCustomizer) -> {
            corsCustomizer.configurationSource(this.corsConfigurationSource());
        }).csrf((csrf) -> {
            csrf.disable();
        }).authorizeHttpRequests((authorize) -> {
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.requestMatchers(new String[]{"/**"})).permitAll();// 73
        }).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());// 75 76 77
        http.exceptionHandling((exception) -> {// 79
            exception.authenticationEntryPoint(this.authenticationEntryPoint);// 80
        });
        http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);// 82
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    public SpringSecurityConfig(final UserDetailsService userDetailsService, final JwtAuthenticationEntryPoint authenticationEntryPoint, final JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }
}
