package org.ecomm.ecommproduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig extends CorsConfiguration {

    @Bean
    public CorsFilter filter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("origin");
        config.addAllowedHeader("x-sso-token");
        config.addAllowedHeader("x-requested-with");
        config.addAllowedHeader("content-type");
        config.addAllowedHeader("accept");
        config.addAllowedHeader("authorization");
        config.addAllowedHeader("cookie");
        config.addAllowedHeader("x-correlation-id");
        config.addAllowedHeader("Access-Control-Allow-Headers");
        config.addAllowedHeader("Access-Control-Allow-Origin");
        config.addAllowedHeader("Access-Control-Allow-Credentials");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
