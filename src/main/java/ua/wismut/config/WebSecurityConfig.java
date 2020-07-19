package ua.wismut.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.wismut.security.JwtConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**",
                        "/api/v1/register/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/developers/**",
                        "/api/v1/accounts/**",
                        "/api/v1/skills/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .antMatchers("/api/v1/developers/**", "/api/v1/accounts/**").hasAnyRole("MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("MODERATOR", "ADMIN")
                .antMatchers("/api/v1/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(context.getBean(JwtConfigurer.class));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
