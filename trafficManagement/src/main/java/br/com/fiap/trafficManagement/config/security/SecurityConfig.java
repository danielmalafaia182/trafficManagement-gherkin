package br.com.fiap.trafficManagement.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private VerifyToken verifyToken;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity) throws Exception {

        DefaultSecurityFilterChain build = httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        //permissões POST /auth/register
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()//qualquer role pode acessar
                        //permissões POST /auth/login
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //permissões GET /api/trafficLights
                        .requestMatchers(HttpMethod.GET, "/api/trafficLights").hasAnyRole("ADMIN", "USER")//somente se for ADMIN ou USER
                        //permissões GET /api/trafficLights/{id}
                        .requestMatchers(HttpMethod.GET, "/api/trafficLights/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões GET /api/trafficLights
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights").hasRole("ADMIN")
                        //permissões GET /api/trafficLights
                        .requestMatchers(HttpMethod.DELETE, "/api/trafficLights/{id}").hasRole("ADMIN")
                        //permissões POST /api/trafficLights
                        .requestMatchers(HttpMethod.POST, "/api/trafficLights").hasRole("ADMIN")//somente se tiver role ADMIN
                        .anyRequest()//qualquer requisição somente se estiver autenticado
                        .authenticated()
                )
                .addFilterBefore(
                        verifyToken,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
        return build;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
