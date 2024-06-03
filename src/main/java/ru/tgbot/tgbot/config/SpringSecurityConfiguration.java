package ru.tgbot.tgbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.tgbot.tgbot.model.UserAuthority;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/Jokes/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/Jokes/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .requestMatchers(HttpMethod.GET, "/top").hasAnyAuthority(UserAuthority.user.getAuthority(),UserAuthority.manager.getAuthority() )
                                .requestMatchers(HttpMethod.GET, "/Jokes/**").hasAnyAuthority(UserAuthority.user.getAuthority(),UserAuthority.manager.getAuthority() )
                                .requestMatchers(HttpMethod.GET, "/Jokes/calls/**").hasAnyAuthority(UserAuthority.user.getAuthority(),UserAuthority.manager.getAuthority() )
                                .requestMatchers(HttpMethod.POST, "/Jokes/**").hasAuthority(UserAuthority.manager.getAuthority())
                                .anyRequest().hasAuthority(UserAuthority.admin.getAuthority()))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
