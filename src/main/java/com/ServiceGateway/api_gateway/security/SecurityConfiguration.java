
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
    // Intersectar todos los request y validar que el token sea correcto

    // Define un valor predeterminado (true) si no se especifica en application.properties
    @Value("${security.enable-csrf:true}")
    private boolean csrfEnabled;

    @Bean
    public SecurityWebFilterChain SecurityWebFilterChain(ServerHttpSecurity httpSecurity) throws Exception  {
        httpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults());

        if (!csrfEnabled) {
            httpSecurity.csrf(csrf -> csrf.disable());
        }


        return httpSecurity.build();


    }

}