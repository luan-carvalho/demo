package br.com.unnamed.demo.domain.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.unnamed.demo.domain.authentication.web.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private UserDetailsConfig userDetailsConfig;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/**").authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .successHandler(new LoginSuccessHandler())
                                                .permitAll())
                                // .httpBasic(withDefaults())
                                // .logout(LogoutConfigurer::permitAll)
                                .rememberMe(rememberMe -> rememberMe
                                                .userDetailsService(userDetailsConfig)
                                                .key("Q1b7UzLYkZ++kJaZGapB/r/SVn4Xq3tvG3ECXElGr1w=")
                                                .tokenValiditySeconds(86400));

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}