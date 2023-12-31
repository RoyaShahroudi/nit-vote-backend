package com.example.finalproject.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, UserDetailsService userDetailsService) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);

        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type", "x-auth-token", "*", "**"));
        configuration.setExposedHeaders(List.of("x-auth-token", "*", "**"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().and().headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .requestMatchers("/v1/student/register").permitAll()
                .requestMatchers("/v1/student/login").permitAll()
                .requestMatchers("/v1/student/*").hasRole("STUDENT")
                .requestMatchers("/v1/admin/submit").hasRole("ADMIN")
                .requestMatchers("/v1/admin/login").permitAll()
                .requestMatchers("/v1/admin/*").hasRole("ADMIN")
                .requestMatchers("/v1/election/new").hasRole("ADMIN")
                .requestMatchers("/v1/election/add-candidate-to-election").hasRole("ADMIN")
                .requestMatchers("/v1/election/all").hasRole("ADMIN")
                .requestMatchers("/v1/election/by-student-id").hasRole("STUDENT")
                .requestMatchers("/v1/election/result/*").hasRole("ADMIN")
                .requestMatchers("/v1/election/student/result/*").hasRole("STUDENT")
                .requestMatchers("/v1/election/get-candidates-for-students/*").hasRole("STUDENT")
                .requestMatchers("/v1/election/get-elections-history-for-student").hasRole("STUDENT")
                .requestMatchers("/v1/election/in-progress").hasRole("ADMIN")
                .requestMatchers("/v1/election/*").hasRole("ADMIN")
                .requestMatchers("/v1/election/student/*").hasRole("STUDENT")
                .requestMatchers("/v1/candidate/submit").hasRole("ADMIN")
                .requestMatchers("/v1/candidate/by-election-id/*").hasRole("ADMIN")
                .requestMatchers("/v1/candidate/get-all").hasRole("ADMIN")
                .requestMatchers("/v1/vote/submit").hasRole("STUDENT")
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .dispatcherTypeMatchers( DispatcherType.ERROR ).permitAll()
                .and()
                .httpBasic()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
