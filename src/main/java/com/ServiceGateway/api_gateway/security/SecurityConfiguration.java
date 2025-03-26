package com.ServiceGateway.api_gateway.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated() // Todas las solicitudes deben estar autenticadas
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .authenticationSuccessHandler(authenticationSuccessHandler()) // Configura el manejador de éxito
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable); // Deshabilita CSRF

        return http.build();
    }

    @Bean
    public ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RedirectServerAuthenticationSuccessHandler("/home"); // Redirige a "/home" después de la autenticación
    }
}


/*
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Value("${security.enable-csrf:true}")
    private boolean csrfEnabled;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults());

        if (!csrfEnabled) {
            httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        }

        return httpSecurity.build();
    }
}

*/
