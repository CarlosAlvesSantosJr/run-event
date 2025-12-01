package com.example.runevent.config;

import com.example.runevent.repository.AtletaRepository;
import com.example.runevent.security.JwtAuthFilter;
import com.example.runevent.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AtletaRepository atletaRepository;

    public SecurityConfig(AtletaRepository atletaRepository) {
        this.atletaRepository = atletaRepository;
    }

    @Bean
    public JwtAuthFilter jwtAuthenticationFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        return new JwtAuthFilter(userDetailsService, jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/api/public/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/atletas").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/atletas**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/atletas**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/atletas**").hasRole("ADMIN")

                        // coloque aqui os seus outros controllers também
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> atletaRepository.findByLogin_Email(username)
                .map(atleta -> {

                    List<GrantedAuthority> authorities = atleta.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getDescricao()))
                            .collect(Collectors.toList());

                    String email = atleta.getLogin().getEmail();
                    String senha = atleta.getLogin().getSenha();

                    return new User(
                            email,
                            senha,
                            authorities
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Atleta não encontrado: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
