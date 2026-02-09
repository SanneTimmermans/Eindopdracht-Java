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
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/gebruikers").permitAll()

                        .requestMatchers(HttpMethod.GET, "/gebruikers/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/gebruikers/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/gebruikers/*/rol").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/gebruikers/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/projecten/{projectId}").hasAnyRole("MONTEUR", "EIGENAAR")
                        .requestMatchers(HttpMethod.POST, "/projecten/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/projecten/**").hasAnyRole("MONTEUR", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/onderdelen/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.DELETE, "/onderdelen/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.PUT, "/onderdelen/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/onderdelen/**").hasAnyRole("MONTEUR", "EIGENAAR")

                        .requestMatchers(HttpMethod.POST, "/logboeken/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/logboeken/**").hasAnyRole("MONTEUR", "EIGENAAR")

                        .requestMatchers(HttpMethod.GET, "/facturen/{projectId}").hasAnyRole("EIGENAAR", "MONTEUR")
                        .requestMatchers(HttpMethod.POST, "/facturen/**").hasAnyRole("MONTEUR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/facturen/**").hasAnyRole("MONTEUR", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/documentatie/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.DELETE, "/documentatie/**").hasRole("MONTEUR")
                        .requestMatchers(HttpMethod.GET, "/documentatie/**").hasAnyRole("MONTEUR", "EIGENAAR")

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
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority));
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