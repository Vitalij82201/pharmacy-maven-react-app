package org.hstn.pharmacy.security.filter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter filter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Публічні ендпоінти
                        .requestMatchers("/api/public/**", "/api/auth/**").permitAll()

                        // Адмінські ендпоінти
                        .requestMatchers("/api/admin/**", "/api/users/all").hasRole("ADMIN")

                        // Ендпоінти для Allergy_Hay_Fever - тільки ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/allergy_hay_fever").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/laxative").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/love_potency").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/pain_relief").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/allergy_hay_fever").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/laxative").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/love_potency").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/pain_relief").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/allergy_hay_fever/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/laxative").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/love_potency").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/pain_relief").hasRole("ADMIN")

                        // Дозволити всім переглядати препарати (GET)
                        .requestMatchers(HttpMethod.GET, "/api/allergy_hay_fever").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/allergy_hay_fever/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/laxative").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/laxative/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/love_potency").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/love_potency/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/pain_relief").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/pain_relief/**").permitAll()// Інші правила
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Додайте Cors конфігурацію, якщо потрібно
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}