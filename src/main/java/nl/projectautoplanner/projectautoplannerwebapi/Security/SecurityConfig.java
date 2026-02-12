package nl.projectautoplanner.projectautoplannerwebapi.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.core.convert.converter.Converter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(hp -> hp.disable())
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST, "/gebruikers").permitAll()
                        .requestMatchers("/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/gebruikers/{gebruikersnaam}").hasAnyAuthority("ADMIN", "MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/gebruikers/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/gebruikers/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/gebruikers/*/rol").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/gebruikers/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/projecten/{projectId}").hasAnyAuthority("MONTEUR", "EIGENAAR")
                        .requestMatchers(HttpMethod.GET, "/projecten/eigenaar_id/**").hasAnyAuthority("MONTEUR", "EIGENAAR")
                        .requestMatchers(HttpMethod.POST, "/projecten/**").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/projecten/**").hasAnyAuthority("MONTEUR", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/onderdelen/**").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.DELETE, "/onderdelen/**").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.PUT, "/onderdelen/**").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/onderdelen/**").hasAnyAuthority("MONTEUR", "EIGENAAR")

                        .requestMatchers(HttpMethod.POST, "/logboeken/**").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/logboeken/**").hasAnyAuthority("MONTEUR", "EIGENAAR")

                        .requestMatchers(HttpMethod.GET, "/facturen/{projectId}").hasAnyAuthority("EIGENAAR", "MONTEUR")
                        .requestMatchers(HttpMethod.POST, "/facturen/**").hasAnyAuthority("MONTEUR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/facturen/**").hasAnyAuthority("MONTEUR", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/documentatie/upload").hasAuthority("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/documentatie/download/**").hasAnyAuthority("MONTEUR", "EIGENAAR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/documentatie/project/**").hasAnyAuthority("MONTEUR", "EIGENAAR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/documentatie/**").hasAnyAuthority("ADMIN", "MONTEUR")

                        .requestMatchers(HttpMethod.GET, "/**").authenticated()
                        .anyRequest().denyAll())
        .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new Converter<Jwt, Collection<GrantedAuthority>>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(authority));
                }
                return grantedAuthorities;
            }
            private List<String> getAuthorities(Jwt jwt) {
                Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                if (resourceAccess != null) {
                    if (resourceAccess.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAccess.get(clientId);
                        if (client != null && client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    }
                }
                return new ArrayList<>();
            }
        });
        return converter;
    }
}