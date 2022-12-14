package com.san.martin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService usuarioServiceImpl;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.usuarioServiceImpl).passwordEncoder(passwordEncoder());
  }

  @Bean("authenticationManager")
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    // TODO Auto-generated method stub
    return super.authenticationManager();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest().authenticated()// requiere autenticación
        .and()
        .csrf().disable() // desabilitamos la proteccion csrf
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // desabilitar el manejo de sesiones en la autenticación por el lado de spring
    // security
  }// proteger el formulario atraves de un Token para evitar ataques se utilizara
   // token para controlar.

}
