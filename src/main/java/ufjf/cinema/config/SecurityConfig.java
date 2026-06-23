package ufjf.cinema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ufjf.cinema.security.JwtAuthFilter;
import ufjf.cinema.security.JwtService;
import ufjf.cinema.services.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/cinemas/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/sessoes-cinema/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/generos-filme/**").permitAll()

                .antMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/login").permitAll()

                .antMatchers("/api/v1/enderecos/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/tipo-ingressos/**").hasAnyRole("USER", "ADMIN")

                .antMatchers("/api/v1/assentos/**").hasRole("ADMIN")
                .antMatchers("/api/v1/tipo-assentos/**").hasRole("ADMIN")
                .antMatchers("/api/v1/tipo-audios/**").hasRole("ADMIN")
                .antMatchers("/api/v1/tipo-imagens/**").hasRole("ADMIN")
                .antMatchers("/api/v1/tipo-salas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/salas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/cinemas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/sessoes-cinema/**").hasRole("ADMIN")
                .antMatchers("/api/v1/generos-filme/**").hasRole("ADMIN")

                .antMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/clientes/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}