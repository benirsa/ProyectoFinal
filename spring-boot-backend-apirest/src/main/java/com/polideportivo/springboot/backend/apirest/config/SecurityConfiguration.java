package com.polideportivo.springboot.backend.apirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    AuthenticationManager authenticationManager(
            HttpSecurity http,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailsService) throws Exception {
 
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests(requests -> requests
        		.requestMatchers("/**")
        		.permitAll())
//                .requestMatchers("/pistas/lista/abonado",
//        				"/reservas/lista/abonado", "/reservas/crear", "/reservas/modificar/**", "/reservas/eliminar/**",
//        				"/tipo-hora/lista")
//                .hasAuthority("ABONADO")
//                .requestMatchers("/tipo-hora/lista",
//                		"/pistas/lista","/pistas/crear","/pistas/eliminar/**","/pistas/modificar/**",
//                		"/abonados/lista","/abonados/crear","/abonados/eliminar/**","/abonados/modificar/**",
//                		"/reservas/lista","/reservas/crear","/reservas/eliminar/**","/reservas/modificar/**",
//                		"/tipo-pista/lista","/tipo-pista/crear","/tipo-pista/eliminar/**","/tipo-pista/modificar/**",
//                		"/trabajadores/lista","/trabajadores/crear","/trabajadores/eliminar/**","/trabajadores/modificar/**")
//                .hasAuthority("ADMIN")
//                .requestMatchers("/tipo-hora/lista",
//                		"/pistas/lista","/pistas/crear","/pistas/eliminar/**","/pistas/modificar/**",
//                		"/abonados/crear","/abonados/eliminar/**","/abonados/modificar/**",
//                		"/reservas/lista","/reservas/crear","/reservas/eliminar/**","/reservas/modificar/**",
//                		"/tipo-pista/lista","/tipo-pista/crear","/tipo-pista/eliminar/**","/tipo-pista/modificar/**")
//		        .hasAuthority("TRABAJADOR"))
                .formLogin()
                .defaultSuccessUrl("/tipo-hora/lista");
        return http.build();
    }
}
