package com.stockmarket.app.security;

import org.springframework.context.annotation.Configuration;

/**
 * >>>>>>>>>>>
 * SUPPLEMENTARY FOR QUIZ 13: Security Configuration
 * 
 * Your task:
 * Configure Spring Security with JWT authentication:
 * 1. Set up security configuration
 * 2. Configure authentication manager
 * 3. Define authorization rules
 * 4. Add JWT filter to the filter chain
 * 
 * HINTS:
 * 1. Create a configuration class with necessary annotations:
 *    @Configuration
 *    @EnableWebSecurity
 *    @EnableGlobalMethodSecurity(
 *        securedEnabled = true,
 *        jsr250Enabled = true,
 *        prePostEnabled = true
 *    )
 *    public class SecurityConfig extends WebSecurityConfigurerAdapter {
 *        // Implementation
 *    }
 * 
 * 2. Inject required dependencies:
 *    private final UserDetailsService userDetailsService;
 *    private final JwtAuthenticationFilter jwtAuthenticationFilter;
 *    private final JwtAuthenticationEntryPoint unauthorizedHandler;
 *    
 *    public SecurityConfig(
 *            UserDetailsService userDetailsService,
 *            JwtAuthenticationFilter jwtAuthenticationFilter,
 *            JwtAuthenticationEntryPoint unauthorizedHandler) {
 *        this.userDetailsService = userDetailsService;
 *        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
 *        this.unauthorizedHandler = unauthorizedHandler;
 *    }
 * 
 * 3. Configure authentication manager builder:
 *    @Override
 *    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) 
 *            throws Exception {
 *        authenticationManagerBuilder
 *            .userDetailsService(userDetailsService)
 *            .passwordEncoder(passwordEncoder());
 *    }
 *    
 *    @Bean
 *    public PasswordEncoder passwordEncoder() {
 *        return new BCryptPasswordEncoder();
 *    }
 *    
 *    @Bean
 *    @Override
 *    public AuthenticationManager authenticationManagerBean() throws Exception {
 *        return super.authenticationManagerBean();
 *    }
 * 
 * 4. Configure HTTP security:
 *    @Override
 *    protected void configure(HttpSecurity http) throws Exception {
 *        http
 *            .cors().and().csrf().disable()
 *            .exceptionHandling()
 *                .authenticationEntryPoint(unauthorizedHandler)
 *                .and()
 *            .sessionManagement()
 *                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
 *                .and()
 *            .authorizeRequests()
 *                .antMatchers("/api/auth/**").permitAll()
 *                .antMatchers("/api/public/**").permitAll()
 *                .antMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
 *                .anyRequest().authenticated();
 *                
 *        // Add JWT filter
 *        http.addFilterBefore(
 *            jwtAuthenticationFilter, 
 *            UsernamePasswordAuthenticationFilter.class
 *        );
 *    }
 * 
 * 5. Configure CORS:
 *    @Bean
 *    public CorsFilter corsFilter() {
 *        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 *        CorsConfiguration config = new CorsConfiguration();
 *        config.setAllowCredentials(true);
 *        config.addAllowedOrigin("*");
 *        config.addAllowedHeader("*");
 *        config.addAllowedMethod("*");
 *        source.registerCorsConfiguration("/**", config);
 *        return new CorsFilter(source);
 *    }
 * 
 * 6. Add necessary imports:
 *    import org.springframework.beans.factory.annotation.Autowired;
 *    import org.springframework.context.annotation.Bean;
 *    import org.springframework.security.authentication.AuthenticationManager;
 *    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
 *    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
 *    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 *    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 *    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 *    import org.springframework.security.config.http.SessionCreationPolicy;
 *    import org.springframework.security.core.userdetails.UserDetailsService;
 *    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 *    import org.springframework.security.crypto.password.PasswordEncoder;
 *    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 *    import org.springframework.web.cors.CorsConfiguration;
 *    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 *    import org.springframework.web.filter.CorsFilter;
 * 
 * >>>>>>>>>>>
 */
@Configuration
public class SecurityConfig {
    
    // TODO: Implement your solution here
    
}