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
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()// pode acessar sem autenticação
                        //permissões POST /auth/login
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        //permissões GET  /api/users
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        //permissões GET  /api/users/{id}
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("ADMIN")
                        //permissões DELETE  /api/users/{id}
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        //permissões GET /api/trafficLights
                        .requestMatchers(HttpMethod.GET, "/api/trafficLights").hasAnyRole("ADMIN", "USER")//somente se for ADMIN ou USER
                        //permissões PUT /trafficLights/reportFault/:{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/reportFault/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficLights/desactivateTrafficLights/{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/desactivateTrafficLights/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficLights/togglePedestrianMode/{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/togglePedestrianMode/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficLights/toggleEmergencyMode/{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/toggleEmergencyMode/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficLights/activateTrafficLights/:{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/activateTrafficLights/{id}").hasRole("ADMIN")
                        //permissões PUT /api/trafficLights/activateTrafficLights/:{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficLights/activateTrafficLights/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões GET /api/trafficLights/{id}
                        .requestMatchers(HttpMethod.GET, "/api/trafficLights/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões DELETE /api/trafficLights/{id}
                        .requestMatchers(HttpMethod.DELETE, "/api/trafficLights/{id}").hasRole("ADMIN")
                        //permissões POST /api/trafficLights
                        .requestMatchers(HttpMethod.POST, "/api/trafficLights").hasRole("ADMIN")//somente se tiver role ADMIN
                        //permissões POST /api/trafficSensors
                        .requestMatchers(HttpMethod.POST, "/api/trafficSensors").hasRole("ADMIN")
                        //permissões GET /api/trafficSensors/{id}
                        .requestMatchers(HttpMethod.GET, "/api/trafficSensors/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões DEL /api/trafficSensors/{id}
                        .requestMatchers(HttpMethod.DELETE, "/api/trafficSensors/{id}").hasRole("ADMIN")
                        //permissões GET /api/trafficSensors
                        .requestMatchers(HttpMethod.GET, "/api/trafficSensors").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /trafficSensors/activateTrafficSensors/{id}
                        .requestMatchers(HttpMethod.PUT, "/trafficSensors/activateTrafficSensors/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficSensors/reportFault/{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficSensors/reportFault/{id}").hasAnyRole("ADMIN", "USER")
                        //permissões PUT /api/trafficSensors/adjustTrafficLightsBasedOnDensity/{id}
                        .requestMatchers(HttpMethod.PUT, "/api/trafficSensors/adjustTrafficLightsBasedOnDensity/{id}").hasAnyRole("ADMIN", "USER")
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
