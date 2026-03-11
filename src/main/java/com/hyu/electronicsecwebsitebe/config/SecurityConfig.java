package com.hyu.electronicsecwebsitebe.config;

import com.hyu.electronicsecwebsitebe.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    private static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder (10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors (cors -> {
                })
                .csrf (AbstractHttpConfigurer::disable)
                .sessionManagement (session ->
                        session.sessionCreationPolicy (SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests (auth -> auth
                        // PUBLIC
                        .requestMatchers ("/api/auth/**").permitAll ()
                        .requestMatchers (HttpMethod.GET, "/api/product/**").permitAll ()
                        .requestMatchers (HttpMethod.GET, "/api/brand/**").permitAll ()
                        .requestMatchers (HttpMethod.GET, "/api/product-category/**").permitAll ()
                        .requestMatchers (HttpMethod.GET, "/api/promotion/**").permitAll ()
                        .requestMatchers (HttpMethod.GET, "/api/review/product/**").permitAll ()
                        .requestMatchers ("/api/bill/shipping-fee/**").permitAll ()

                        .requestMatchers (HttpMethod.POST, "/api/payment/sepay/webhook", "/api/payment/sepay-webhook")
                        .permitAll ()
                        .requestMatchers (HttpMethod.POST, "/api/payment/sepay/session")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.GET, "/api/payment/sepay/status/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.POST, "/api/bill/create")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.GET, "/api/bill/customer/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/bill/update-status/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        // CUSTOMER+
                        .requestMatchers (HttpMethod.GET, "/api/shopping-cart/customer/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.POST, "/api/shopping-cart/save")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/shopping-cart/update/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/shopping-cart/delete/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/customer/{id}")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/customer/update/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/address/detail/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.POST, "/api/address/save")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/address/update/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/address/delete/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.POST, "/api/review/save")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/review/update/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/review/delete/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.GET, "/api/review/customer/**")
                        .hasAnyAuthority (ROLE_CUSTOMER, ROLE_EMPLOYEE, ROLE_ADMIN)

                        // EMPLOYEE+
                        .requestMatchers (HttpMethod.POST, "/api/product/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/product/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/product/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/customer/all")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.GET, "/api/customer/mail/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.POST, "/api/customer/save")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/shopping-cart/all")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.GET, "/api/shopping-cart/{id}")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.POST, "/api/brand/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/brand/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/brand/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/review/all")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.POST, "/api/promotion/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.PUT, "/api/promotion/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/promotion/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.POST, "/api/product-category/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/product-category/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers ("/api/imports/**")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        .requestMatchers (HttpMethod.GET, "/api/address/all")
                        .hasAnyAuthority (ROLE_EMPLOYEE, ROLE_ADMIN)

                        // ADMIN ONLY
                        .requestMatchers ("/api/employees/**").hasAuthority (ROLE_ADMIN)
                        .requestMatchers ("/api/role/**").hasAuthority (ROLE_ADMIN)
                        .requestMatchers (HttpMethod.DELETE, "/api/customer/delete/**")
                        .hasAuthority (ROLE_ADMIN)

                        // PHOTO
                        .requestMatchers (HttpMethod.GET, "/photos/**").permitAll ()
                        .anyRequest ().authenticated ()
                )
                .addFilterBefore (jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build ();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration ();
        config.setAllowedOrigins (List.of (
                "https://ec-website-fe-312564370609.asia-southeast1.run.app",
                "https://ubraintech.store",
                "http://localhost:8080",
                "http://localhost:5173"
                
        ));
        config.setAllowedMethods (List.of ("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders (List.of ("*"));
        config.setAllowCredentials (true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ();
        source.registerCorsConfiguration ("/api/**", config);
        return source;
    }
}
