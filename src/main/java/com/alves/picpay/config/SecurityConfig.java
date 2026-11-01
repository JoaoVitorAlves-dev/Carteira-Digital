package com.alves.picpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenFilter tokenFilter;

    public SecurityConfig(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(cs -> cs.disable()
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST,"/picpay/auth/login", "/picpay/auth/register", "/picpay/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/picpay/auth/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/picpay/auth/delete").permitAll()

                        .requestMatchers(HttpMethod.POST, "/picpay/endereco/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/picpay/endereco/list").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/picpay/endereco/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/picpay/endereco/delete").authenticated()

                        .requestMatchers(HttpMethod.POST, "/picpay/carteira/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/picpay/carteira/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/picpay/carteira/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/picpay/carteira/delete").authenticated()

                        .requestMatchers(HttpMethod.POST, "/picpay/chavepix/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/picpay/chavepix/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/picpay/chavepix/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/picpay/chavepix/delete").authenticated()

                        .requestMatchers(HttpMethod.POST, "/picpay/estorno/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/picpay/estorno/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/picpay/estorno/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/picpay/estorno/delete").authenticated()

                        .requestMatchers(HttpMethod.POST, "/picpay/transacao/create").authenticated()
                        .requestMatchers(HttpMethod.GET, "/picpay/transacao/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/picpay/transacao/update").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/picpay/transacao/delete").authenticated()

                        .anyRequest().authenticated())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
